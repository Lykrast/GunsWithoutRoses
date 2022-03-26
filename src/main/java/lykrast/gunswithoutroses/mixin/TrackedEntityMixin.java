package lykrast.gunswithoutroses.mixin;

import lykrast.gunswithoutroses.entity.BulletEntity;
import lykrast.gunswithoutroses.network.NetworkUtils;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SEntityPacket;
import net.minecraft.world.TrackedEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Consumer;

@Mixin(TrackedEntity.class)
public class TrackedEntityMixin {

    @Final @Shadow private Entity entity;

    @Redirect(method = "sendChanges", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V", ordinal = 2))
    public void onVelocity(Consumer<Object> instance, Object t) {
        if (!(entity instanceof BulletEntity)) {
            instance.accept(t);
            return;
        }
        instance.accept(NetworkUtils.sendProjectileVelocity(entity, false));
    }

    @Redirect(method = "sendPairingData(Ljava/util/function/Consumer;)V", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V", ordinal = 3))
    public void onPairingData(Consumer<Object> consumer, Object t) {
        if (!(entity instanceof BulletEntity)) {
            consumer.accept(t);
            return;
        }
        consumer.accept(NetworkUtils.sendProjectileVelocity(entity, false));
    }

    @Redirect(method = "sendChanges()V", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V", ordinal = 3))
    public void onMovePacket(Consumer<Object> consumer, Object t) {
        if (!(t instanceof SEntityPacket.MovePacket) || !(entity instanceof BulletEntity)) {
            consumer.accept(t);
            return;
        }
        consumer.accept(NetworkUtils.sendProjectileVelocity(entity, true));
    }
}
