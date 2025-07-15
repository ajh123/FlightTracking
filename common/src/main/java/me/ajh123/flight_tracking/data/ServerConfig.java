package me.ajh123.flight_tracking.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ServerConfig {
    private final Properties properties = new Properties();
    private final Path configPath;

    public String trackingURL;

    public ServerConfig(Path configDir) {
        this.configPath = configDir.resolve("flight_tracking-server.properties");
    }
    public void load() {
        if (Files.exists(configPath)) {
            try (InputStream in = Files.newInputStream(configPath)) {
                properties.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        trackingURL = properties.getProperty("trackingURL", "wss://example.com/tracking");
    }

    public void save() {
        properties.setProperty("trackingURL", trackingURL);
        try {
            Files.createDirectories(configPath.getParent());
            try (OutputStream out = Files.newOutputStream(configPath)) {
                properties.store(out, "Flight Tracking Server Configuration");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
