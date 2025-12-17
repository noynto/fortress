package me.noynto.fortress;


import io.helidon.config.Config;
import io.helidon.webserver.WebServer;

/**
 * <p><b>Knight</b> est une <b>interface en ligne de commande</b>, autrement appelé <b>CLI</b>.</p>
 */
public class Knight {
    public static void main(String[] args) {
        Config globalConfig = Config.create().get("fortress");
        Config storageConfig = globalConfig.get("storage");
        Minio.Properties minioProperties = Minio.Properties.from(storageConfig);
        Minio.Configuration minioConfiguration = Minio.Configuration.build(minioProperties);
        CreateTransaction createTransaction = new MinioTransactionCreator(minioConfiguration);
        Config serverConfig = globalConfig.get("server");
        Config knightConfig = serverConfig.get("knight");
        WebServer webServer = WebServer.builder()
                .port(knightConfig.get("port").as(Integer.class).get())
                .routing(builder -> builder.post(new CreateTransactionHandler(createTransaction)))
                .build();
        webServer.start();
    }
}