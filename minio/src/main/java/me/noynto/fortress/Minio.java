package me.noynto.fortress;

import io.helidon.config.Config;
import io.minio.MinioClient;

import java.net.URL;

public interface Minio {

    record Configuration(
            MinioClient client
    ) {
        public static Configuration build(Properties properties) {
            return new Configuration(MinioClient.builder().endpoint(properties.url).credentials(properties.accessKey, properties.secretKey).build());
        }
    }

    record Properties(
            URL url,
            String accessKey,
            String secretKey
    ) {

        public static Properties from(Config config) {
            Config minioConfig = config.get("minio");
            return new Properties(
                    minioConfig.get("url").as(URL.class).get(),
                    minioConfig.get("user").as(String.class).get(),
                    minioConfig.get("password").as(String.class).get());
        }

    }

}
