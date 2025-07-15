package me.ajh123.flight_tracking.neoforge;

import me.ajh123.flight_tracking.MinecraftPlatform;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public class ForgePlatform implements MinecraftPlatform {
    @Override
    public Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }
}
