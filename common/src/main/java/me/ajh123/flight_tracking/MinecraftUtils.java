package me.ajh123.flight_tracking;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinecraftUtils {
    public static final String MOD_ID = "flight_tracking";
    public static MinecraftPlatform platform;

    @SuppressWarnings("LoggerInitializedWithForeignClass") // Using a foreign class as Minecraft loggers should use the name of the mod
    public static final Logger LOGGER = LoggerFactory.getLogger(FlightTrackingClient.class);

    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
