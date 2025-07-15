package me.ajh123.flight_tracking.neoforge;

import me.ajh123.flight_tracking.FlightTracking;
import net.neoforged.fml.common.Mod;

@Mod(FlightTracking.MOD_ID)
public final class FlightTrackingNeoForge {
    public FlightTrackingNeoForge() {
        // Run our common setup.
        FlightTracking.init();
    }
}
