package me.ajh123.flight_tracking;

import net.minecraft.resources.ResourceLocation;

public class MinecraftUtils {
    public static final String MOD_ID = "flight_tracking";
    public static MinecraftPlatform platform;

    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
