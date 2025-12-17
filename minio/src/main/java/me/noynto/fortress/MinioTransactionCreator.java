package me.noynto.fortress;

import io.helidon.common.media.type.MediaTypes;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.VersioningConfiguration;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonWriter;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record MinioTransactionCreator(
        Minio.Configuration configuration
) implements CreateTransaction {

    public static final String SOURCE_NAME = "MINIO";
    public static final String BUCKET_NAME = "transactions";

    @Override
    public Transaction execute(String description, Instant timestamp, BigDecimal amount) throws Failed {
        manageBucketExistence(this.configuration.client());
        String key = generateKey();
        File temporaryFile = createEmptyFile();

        writeTransactionOnTemporaryFile(description, timestamp, amount, temporaryFile, key);
        Source source = uploadFile(this.configuration.client(), temporaryFile, key);
        return new Transaction(source, description, timestamp, amount);
    }

    private Source uploadFile(MinioClient client, File temporaryFile, String key) throws Failed {
        try {
            ObjectWriteResponse response = client.uploadObject(UploadObjectArgs.builder()
                    .contentType(MediaTypes.APPLICATION_JSON_VALUE)
                    .bucket(BUCKET_NAME)
                    .object(key + ".json")
                    .filename(temporaryFile.getAbsolutePath())
                    .build());
            return new Source(response.object(), SOURCE_NAME);
        } catch (ErrorResponseException | InternalException | InsufficientDataException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new Failed(e);
        }
    }

    private void writeTransactionOnTemporaryFile(String description, Instant timestamp, BigDecimal amount, File temporaryFile, String key) throws Failed {
        try (
                OutputStream outputStream = new FileOutputStream(temporaryFile);
                JsonWriter jsonWriter = Json.createWriter(outputStream);
        ) {
            JsonObject object = buildJsonObject(key, description, formatTimestamp(timestamp), amount.toString());
            jsonWriter.writeObject(object);
        } catch (FileNotFoundException e) {
            throw new Failed("Le document sur lequel écrire est introuvable.", e);
        } catch (IOException e) {
            throw new Failed(e);
        }
    }

    private static JsonObject buildJsonObject(String key, String description, String timestamp, String amount) {
        return Json.createObjectBuilder().add("reference", key).add("description", description).add("timestamp", timestamp).add("amount", amount).build();
    }

    private static String generateKey() {
        return UUID.randomUUID().toString();
    }

    private void manageBucketExistence(MinioClient client) throws Failed {
        try {
            if (bucketDoesNotExist(client)) {
                createBucket(client);
                enableVersioningOnBucket(client);
            }
        } catch (ErrorResponseException | XmlParserException | NoSuchAlgorithmException | InternalException |
                 InvalidKeyException | InsufficientDataException | InvalidResponseException | IOException |
                 ServerException e) {
            throw new Failed(e);
        }
    }

    private static void enableVersioningOnBucket(MinioClient client) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        VersioningConfiguration versioningConfiguration = new VersioningConfiguration(VersioningConfiguration.Status.ENABLED, true);
        client.setBucketVersioning(SetBucketVersioningArgs.builder().bucket(BUCKET_NAME).config(versioningConfiguration).build());
    }

    private static void createBucket(MinioClient client) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        client.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
    }

    private boolean bucketDoesNotExist(MinioClient client) throws ErrorResponseException, InsufficientDataException, InternalException, InvalidKeyException, InvalidResponseException, IOException, NoSuchAlgorithmException, ServerException, XmlParserException {
        return !client.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build());
    }

    private File createEmptyFile() throws Failed {
        try {
            return Files.createTempFile(null, null).toFile();
        } catch (IOException e) {
            throw new Failed(e);
        }
    }

    private String formatTimestamp(Instant instant) {
        return DateTimeFormatter.ISO_INSTANT.format(instant);
    }
}
