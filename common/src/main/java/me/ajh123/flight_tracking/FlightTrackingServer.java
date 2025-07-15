package me.ajh123.flight_tracking;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.networking.NetworkManager;
import me.ajh123.flight_tracking.data.ServerConfig;
import me.ajh123.flight_tracking.payloads.TrackingServerS2C;

import java.nio.file.Path;

public final class FlightTrackingServer {
    static ServerConfig config;

    public static void init() {
        LifecycleEvent.SERVER_STARTING.register((server) -> {
            Path configDir = MinecraftUtils.platform.getConfigDir();
            config = new ServerConfig(configDir);
            config.load();
            config.save();
        });

        PlayerEvent.PLAYER_JOIN.register((player) -> {
            TrackingServerS2C payload = new TrackingServerS2C(config.trackingURL);
            NetworkManager.sendToPlayer(player, payload);
        });
    }
}
