package me.ajh123.flight_tracking.fabric;

import me.ajh123.flight_tracking.MinecraftPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class FabricPlatform implements MinecraftPlatform {
    @Override
    public Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
