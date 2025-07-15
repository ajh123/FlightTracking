package me.ajh123.flight_tracking;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import me.ajh123.flight_tracking.data.TrackingState;
import me.ajh123.flight_tracking.payloads.TrackingServerS2C;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public final class FlightTracking {
    public static final String MOD_ID = "flight_tracking";
    public static @Nullable TrackingState state = null;

    public static final KeyMapping PILOT_MENU = new KeyMapping(
            "key.flight_tracking.pilot_menu", // The translation key of the name shown in the Controls screen
            InputConstants.Type.KEYSYM, // This key mapping is for Keyboards by default
            InputConstants.KEY_DELETE, // The default keycode
            "category.flight_tracking" // The category translation key used to categorize in the Controls screen
    );

    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void init() {
        KeyMappingRegistry.register(PILOT_MENU);

        // Write common init code here.
        NetworkManager.registerReceiver(
                NetworkManager.Side.S2C,
                TrackingServerS2C.TYPE,
                TrackingServerS2C.STREAM_CODEC,
                (payload, context) -> {
                    state = new TrackingState();
                    state.setTrackingURL(payload.URL());
                }
        );

        PlayerEvent.PLAYER_JOIN.register((player) -> {
            String trackingServerUrl = "https://example.com/tracking";
            TrackingServerS2C payload = new TrackingServerS2C(trackingServerUrl);
            NetworkManager.sendToPlayer(player, payload);
        });

        ClientPlayerEvent.CLIENT_PLAYER_QUIT.register((player -> {
            state = null; // Clear the tracking state when the player quits
        }));

        ClientTickEvent.CLIENT_POST.register(minecraft -> {
            while (PILOT_MENU.consumeClick()) {
                if (minecraft.screen == null) {
                    if (state == null) {
                        PilotScreen.onNoTrackingState(minecraft);
                    } else {
                        minecraft.setScreen(new PilotScreen(state));
                    }
                }
            }
            if (minecraft.player != null && minecraft.player.getVehicle() != null) {
                Entity vehicle = minecraft.player.getVehicle();
                ResourceLocation aircraftId = vehicle.getType().arch$registryName();
                System.out.println("Vehicle ID: " + aircraftId);
            }
        });
    }
}
