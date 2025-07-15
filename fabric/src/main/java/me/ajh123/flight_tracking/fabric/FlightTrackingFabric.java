package me.ajh123.flight_tracking.fabric;

import me.ajh123.flight_tracking.FlightTrackingServer;
import me.ajh123.flight_tracking.MinecraftUtils;
import net.fabricmc.api.ModInitializer;

public final class FlightTrackingFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        MinecraftUtils.platform = new FabricPlatform();

        // Run our common setup.
        FlightTrackingServer.init();
    }
}
