package me.ajh123.flight_tracking.screens;

import me.ajh123.flight_tracking.data.TrackingState;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class PilotScreen extends Screen {
    public static final SystemToast.SystemToastId NO_TRACKING_STATE = new SystemToast.SystemToastId();
    public static final SystemToast.SystemToastId WEBSOCKET_ERROR = new SystemToast.SystemToastId();

    public static void onNoTrackingState(Minecraft minecraft) {
        SystemToast systemToast = SystemToast.multiline(
                minecraft,
                NO_TRACKING_STATE,
                Component.translatable("screen.flight_tracking.no_tracking_state.title").withStyle(ChatFormatting.RED),
                Component.translatable("screen.flight_tracking.no_tracking_state.body")
        );
        minecraft.getToasts().addToast(systemToast);
    }

    public static void onWebSocketError(Minecraft minecraft, String errorMessage) {
        SystemToast systemToast = SystemToast.multiline(
                minecraft,
                WEBSOCKET_ERROR,
                Component.translatable("screen.flight_tracking.websocket_error.title").withStyle(ChatFormatting.RED),
                Component.translatable("screen.flight_tracking.websocket_error.body", errorMessage)
        );
        minecraft.getToasts().addToast(systemToast);
    }

    private final TrackingState state;

    private Button startButton;
    private Button stopButton;
    private EditBox callsignInput;

    public PilotScreen(TrackingState state) {
        super(Component.translatable("screen.flight_tracking.pilot_menu.title"));
        this.state = state;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int titleY = 20;
        int spacing = 10;
        int buttonWidth = 70;
        int buttonHeight = 20;

        // Row 2: Start and Stop buttons side by side
        int buttonY = titleY + 30 + spacing;

        startButton = Button.builder(Component.translatable("screen.flight_tracking.start_flight"), (btn) -> {
            if (!state.isInFlight()) {
                state.setInFlight(true);
                state.startWebSocket();
            }
        }).bounds(centerX - buttonWidth - spacing / 2, buttonY, buttonWidth, buttonHeight).build();

        stopButton = Button.builder(Component.translatable("screen.flight_tracking.stop_flight"), (btn) -> {
            if (state.isInFlight()) {
                state.setInFlight(false);
                state.stopWebSocket();
            }
        }).bounds(centerX + spacing / 2, buttonY, buttonWidth, buttonHeight).build();

        // Row 3: Callsign input (under status)
        int inputWidth = 100;
        int statusY = startButton.getY() + startButton.getHeight() + 10;
        int inputY = statusY + this.font.lineHeight + spacing * 2;
        int inputX = centerX - inputWidth / 2;

        callsignInput = new EditBox(this.font, inputX, inputY, inputWidth, buttonHeight,
                Component.translatable("screen.flight_tracking.callsign_box"));
        callsignInput.setValue(state.getCallsign() != null ? state.getCallsign() : "");

        // Add widgets
        this.addRenderableWidget(startButton);
        this.addRenderableWidget(stopButton);
        this.addRenderableWidget(callsignInput);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);

        // Title
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);

        // Tracking URL
        if (state.getTrackingURL() != null) {
            Component urlText = Component.translatable("screen.flight_tracking.tracking_url", state.getTrackingURL());
            guiGraphics.drawCenteredString(this.font, urlText, this.width / 2, 40, 0xFFFFFF);
        } else {
            Component noUrlText = Component.translatable("screen.flight_tracking.no_tracking_url");
            guiGraphics.drawCenteredString(this.font, noUrlText, this.width / 2, 40, 0xFF5555);
        }

        // Status
        Component statusText = state.isInFlight()
                ? Component.translatable("screen.flight_tracking.status.in_flight")
                : Component.translatable("screen.flight_tracking.status.idle");

        int statusColor = state.isInFlight() ? 0x00FF00 : 0xFF5555;
        int statusY = startButton.getY() + startButton.getHeight() + 10;
        guiGraphics.drawCenteredString(this.font, statusText, this.width / 2, statusY, statusColor);

        // Callsign Label
        Component callsignLabel = Component.translatable("screen.flight_tracking.callsign_box");
        int labelY = callsignInput.getY() - this.font.lineHeight - 2;
        guiGraphics.drawCenteredString(this.font, callsignLabel, this.width / 2, labelY, 0xFFFFFF);

        if (callsignInput.getValue().isEmpty()) {
            startButton.active = false;
            stopButton.active = false;
        } else {
            startButton.active = !state.isInFlight();
            stopButton.active = state.isInFlight();
            state.setCallsign(callsignInput.getValue());
        }

        callsignInput.setEditable(!state.isInFlight());
    }
}