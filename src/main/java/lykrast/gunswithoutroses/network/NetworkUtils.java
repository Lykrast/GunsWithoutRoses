package lykrast.gunswithoutroses.network;

import io.netty.buffer.Unpooled;
import lykrast.gunswithoutroses.GunsWithoutRoses;
import net.minecraft.entity.Entity;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.network.NetworkDirection;
import org.apache.commons.lang3.tuple.Pair;

public class NetworkUtils {

    public static IPacket<?> getProjectileSpawnPacket(Entity entity) {
        PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
        buffer.writeInt(10);
        buffer.writeVarInt(Registry.ENTITY_TYPE.getId(entity.getType()));
        buffer.writeInt(entity.getId());
        buffer.writeUUID(entity.getUUID());
        buffer.writeDouble(entity.getX());
        buffer.writeDouble(entity.getY());
        buffer.writeDouble(entity.getZ());
        buffer.writeByte((byte) MathHelper.floor(entity.xRot * 256.0F / 360.0F));
        buffer.writeByte((byte) MathHelper.floor(entity.yRot * 256.0F / 360.0F));
        buffer.writeByte((byte) (entity.getYHeadRot() * 256.0F / 360.0F));
        Vector3d velocity = entity.getDeltaMovement();
        buffer.writeFloat((float) velocity.x);
        buffer.writeFloat((float) velocity.y);
        buffer.writeFloat((float) velocity.z);

        return NetworkDirection.PLAY_TO_CLIENT.buildPacket(Pair.of(buffer, 10), GunsWithoutRoses.rl("custom_channel")).getThis();
    }

    public static IPacket<?> sendProjectileVelocity(Entity entity, boolean movement) {
        PacketBuffer packet = new PacketBuffer(Unpooled.buffer());
        packet.writeInt(12);
        packet.writeInt(entity.getId());
        Vector3f vector3f = new Vector3f(entity.getDeltaMovement());
        packet.writeDouble(vector3f.x());
        packet.writeDouble(vector3f.y());
        packet.writeDouble(vector3f.z());
        packet.writeBoolean(movement);
        if (movement) {
            packet.writeFloat(entity.xRot);
            packet.writeFloat(entity.yRot);
            packet.writeBoolean(entity.isOnGround());
        }
        return NetworkDirection.PLAY_TO_CLIENT.buildPacket(Pair.of(packet, 12), GunsWithoutRoses.rl("custom_channel")).getThis();
    }
}
