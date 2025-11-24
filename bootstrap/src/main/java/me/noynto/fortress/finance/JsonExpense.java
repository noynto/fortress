package me.noynto.fortress.finance;

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * <p>Implémentation qui fonctionne avec un fichier JSON.</p>
 */
public record JsonExpense(
        ObjectMapper mapper,
        Path root
) implements ExpenseProvider {

    @Override
    public Expense create(Expense.Label label, Expense.Amount amount) {
        Expense.Reference reference = new Expense.Reference(UUID.randomUUID().toString());
        Path path = createFileAndGetPath(reference);
        writeOnFile(label, amount, path, reference);
        return new Expense(reference, label, amount);
    }

    @Override
    public Stream<Expense> read() {
        Spliterator<File> fileSpliterator = getFileSpliterator();
        Stream<File> fileStream = StreamSupport.stream(fileSpliterator, false);
        return fileStream.map(file -> {
            try (InputStream is = Files.newInputStream(file.toPath());
                 JsonParser parser = mapper.createParser(is)) {
                JsonNode node = parser.readValueAsTree();
                return new Expense(new Expense.Reference(node.get("reference").asString()), new Expense.Label(node.get("label").asString()), new Expense.Amount(new BigDecimal(node.get("amount").asString())));
            } catch (IOException e) {
                throw new NotAvailable("Impossible de lire le fichier " + file.getName(), e);
            }
        });
    }

    private Spliterator<File> getFileSpliterator() {
        Iterator<File> fileIterator = new Iterator<>() {
            private final File[] files = root().toFile().listFiles(f -> f.isFile() && f.getName().endsWith(".json"));
            private int index = 0;

            @Override
            public boolean hasNext() {
                return files != null && index < files.length;
            }

            @Override
            public File next() {
                if (files != null) {
                    return files[index++];
                } else {
                    throw new NoSuchElementException();
                }
            }
        };

        Spliterator<File> fileSpliterator = Spliterators.spliteratorUnknownSize(fileIterator, Spliterator.ORDERED);
        return fileSpliterator;
    }

    private void writeOnFile(Expense.Label label, Expense.Amount amount, Path path, Expense.Reference reference) {
        try (OutputStream outputStream = Files.newOutputStream(path)) {
            JsonGenerator jsonGenerator = mapper.createGenerator(outputStream);
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringProperty("reference", reference.value());
            jsonGenerator.writeStringProperty("label", label.value());
            jsonGenerator.writeStringProperty("amount", amount.value().toPlainString());
            jsonGenerator.writeEndObject();
            jsonGenerator.flush();
        } catch (IOException e) {
            throw new NotAvailable("La création du fichier JSON contenant la dépense a échoué.", e);
        }
    }

    private Path createFileAndGetPath(Expense.Reference reference) {
        Path path;
        try {
            path = Files.createFile(Path.of(root.toString(), reference.value() + ".json"));
        } catch (IOException e) {
            throw new NotAvailable("La création du fichier JSON contenant la dépense a échoué.", e);
        }
        return path;
    }
}
