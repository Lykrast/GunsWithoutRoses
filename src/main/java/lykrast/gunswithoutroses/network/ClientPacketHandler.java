package lykrast.gunswithoutroses.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;

public class ClientPacketHandler {

    @SubscribeEvent
    public void onClientPayload(NetworkEvent.ServerCustomPayloadEvent event) {
        if (event instanceof NetworkEvent.ServerCustomPayloadLoginEvent) {
            return;
        }
        PacketBuffer buffer = event.getPayload();
        NetworkEvent.Context ctx = event.getSource().get();
        INetHandler netHandler = ctx.getNetworkManager().getPacketListener();
        ctx.setPacketHandled(true);
        if (netHandler instanceof ClientPlayNetHandler) {
            ClientPlayNetHandler cnh = (ClientPlayNetHandler) netHandler;
            ctx.enqueueWork(() -> handlePacket(buffer, Minecraft.getInstance(), cnh));
        }
    }

    private void handlePacket(PacketBuffer buffer, Minecraft minecraft, ClientPlayNetHandler netHandler) {
        int packet = buffer.readInt();

        if (packet == 10) {
            entitySpawnPacket(buffer, minecraft);
        } else if (packet == 11) {
            entityVelocityPacket(buffer, minecraft);
        }
    }

    private void entitySpawnPacket(PacketBuffer packet, Minecraft mc) {
        if (mc.level == null) {
            return;
        }
        EntityType<?> type = Registry.ENTITY_TYPE.byId(packet.readVarInt());
        int entityID = packet.readInt();
        UUID uuid = packet.readUUID();
        double posX = packet.readDouble();
        double posY = packet.readDouble();
        double posZ = packet.readDouble();
        byte yaw = packet.readByte();
        byte pitch = packet.readByte();
        byte headYaw = packet.readByte();
        Vector3d velocity = new Vector3d(packet.readFloat(), packet.readFloat(), packet.readFloat());
        Entity entity = type.create(mc.level);
        if (entity == null) {
            return;
        }

        entity.setPacketCoordinates(posX, posY, posZ);
        entity.absMoveTo(posX, posY, posZ, (pitch * 360) / 256.0F, (yaw * 360) / 256.0F);
        entity.setYHeadRot((headYaw * 360) / 256.0F);
        entity.setYBodyRot((headYaw * 360) / 256.0F);
        entity.setId(entityID);
        entity.setUUID(uuid);
        mc.level.putNonPlayerEntity(entityID, entity);
        entity.lerpMotion(velocity.x, velocity.y, velocity.z);
    }

    private void entityVelocityPacket(PacketBuffer packet, Minecraft mc) {
        if (mc.level == null) {
            return;
        }
        int entityID = packet.readInt();
        Entity entity = mc.level.getEntity(entityID);
        if (entity != null) {
            Vector3f motion = new Vector3f(packet.readFloat(), packet.readFloat(), packet.readFloat());
            entity.lerpMotion(motion.x(), motion.y(), motion.z());
            if (packet.readBoolean()) {
                entity.xRot = packet.readFloat();
                entity.yRot = packet.readFloat();
                entity.setOnGround(packet.readBoolean());
            }
        }
    }
}
