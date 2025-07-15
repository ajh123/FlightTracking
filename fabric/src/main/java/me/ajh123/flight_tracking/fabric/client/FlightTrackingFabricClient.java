package me.ajh123.flight_tracking.fabric.client;

import me.ajh123.flight_tracking.FlightTrackingClient;
import net.fabricmc.api.ClientModInitializer;

public final class FlightTrackingFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FlightTrackingClient.init();
    }
}
