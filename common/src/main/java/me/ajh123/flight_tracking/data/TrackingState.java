package me.ajh123.flight_tracking.data;

import me.ajh123.flight_tracking.networking.TrackingWebSocket;

import java.net.URI;

public class TrackingState {
    private String trackingURL;
    private boolean inFlight;
    private String callsign;
    private TrackingWebSocket webSocket;

    public String getTrackingURL() {
        return trackingURL;
    }

    public void setTrackingURL(String trackingURL) {
        this.trackingURL = trackingURL;
    }

    public boolean isInFlight() {
        return inFlight;
    }

    public void setInFlight(boolean inFlight) {
        this.inFlight = inFlight;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public void startWebSocket() {
        if (webSocket != null) {
            webSocket.close();
        }
        webSocket = new TrackingWebSocket(URI.create(trackingURL));
        webSocket.connect();
    }

    public void stopWebSocket() {
        if (webSocket != null) {
            webSocket.close();
            webSocket = null;
        }
    }
}
