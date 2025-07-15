package me.ajh123.flight_tracking.neoforge;

import me.ajh123.flight_tracking.FlightTrackingClient;
import me.ajh123.flight_tracking.FlightTrackingServer;
import me.ajh123.flight_tracking.MinecraftUtils;
import net.neoforged.fml.common.Mod;

@Mod(MinecraftUtils.MOD_ID)
public final class FlightTrackingNeoForge {
    public FlightTrackingNeoForge() {
        MinecraftUtils.platform = new ForgePlatform();
        // Run our common setup.
        FlightTrackingServer.init();
        FlightTrackingClient.init();
    }
}
