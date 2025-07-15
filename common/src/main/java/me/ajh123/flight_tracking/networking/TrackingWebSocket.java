package me.ajh123.flight_tracking.networking;

import me.ajh123.flight_tracking.FlightTrackingClient;
import me.ajh123.flight_tracking.MinecraftUtils;
import me.ajh123.flight_tracking.screens.PilotScreen;
import net.minecraft.client.Minecraft;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class TrackingWebSocket extends WebSocketClient {
    public TrackingWebSocket(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {

    }

    @Override
    public void onMessage(String message) {

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        MinecraftUtils.LOGGER.info("WebSocket closed: Code: {}, Reason: {}, Remote: {}", code, reason, remote);
        boolean isError = code != 1000; // 1000 is normal closure
        if (isError) {
            PilotScreen.onWebSocketError(Minecraft.getInstance(), reason);
            if (FlightTrackingClient.state != null) {
                FlightTrackingClient.state.setInFlight(false);
            }
        }
    }

    @Override
    public void onError(Exception e) {
        MinecraftUtils.LOGGER.error("WebSocket error: ", e);
        PilotScreen.onWebSocketError(Minecraft.getInstance(), e.getMessage());
        if (FlightTrackingClient.state != null) {
            FlightTrackingClient.state.setInFlight(false);
        }
    }
}
