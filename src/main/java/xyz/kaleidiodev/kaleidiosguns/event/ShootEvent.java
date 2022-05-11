package xyz.kaleidiodev.kaleidiosguns.event;

import java.util.Random;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import xyz.kaleidiodev.kaleidiosguns.entity.BulletEntity;

public class ShootEvent {
    //Event to change add bullet inaccuracy if VivecraftForgeExtension is present
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof BulletEntity) {
            Random random = new Random();
            BulletEntity shot = (BulletEntity)event.getEntity();
            Vector3d direction = shot.getDeltaMovement();
            double velocity = direction.length();
            direction = direction.normalize().add(random.nextGaussian() * 0.0075 * shot.getInaccuracy(), random.nextGaussian() * 0.0075 * shot.getInaccuracy(), random.nextGaussian() * 0.0075 * shot.getInaccuracy()).scale(velocity);
            shot.setDeltaMovement(direction);
            float horizontalDistance = MathHelper.sqrt(direction.x * direction.x + direction.z * direction.z);
            shot.yRot = (float)(MathHelper.atan2(direction.x, direction.z) * (double)(180F / (float)Math.PI));
            shot.xRot = (float)(MathHelper.atan2(direction.y, horizontalDistance) * (double)(180F / (float)Math.PI));
            shot.yRotO = shot.yRot;
            shot.xRotO = shot.xRot;
        }
    }
}
