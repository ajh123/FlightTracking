package me.ajh123.flight_tracking.networking;

import io.netty.buffer.ByteBuf;
import me.ajh123.flight_tracking.MinecraftUtils;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record TrackingServerS2C(String URL) implements CustomPacketPayload {
    public static final ResourceLocation ID = MinecraftUtils.location("tracking_server");
    public static final CustomPacketPayload.Type<TrackingServerS2C> TYPE = new CustomPacketPayload.Type<>(ID);

    // Each pair of elements defines the stream codec of the element to encode/decode and the getter for the element to encode
    // 'name' will be encoded and decoded as a string
    // 'age' will be encoded and decoded as an integer
    // The final parameter takes in the previous parameters in the order they are provided to construct the payload object
    public static final StreamCodec<ByteBuf, TrackingServerS2C> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            TrackingServerS2C::URL,
            TrackingServerS2C::new
    );

    @Override
    public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
