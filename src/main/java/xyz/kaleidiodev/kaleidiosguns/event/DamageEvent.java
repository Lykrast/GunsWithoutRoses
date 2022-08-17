package xyz.kaleidiodev.kaleidiosguns.event;

import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.kaleidiodev.kaleidiosguns.KaleidiosGuns;
import xyz.kaleidiodev.kaleidiosguns.entity.BulletEntity;

@Mod.EventBusSubscriber(modid = KaleidiosGuns.MODID)
public class DamageEvent {
    @SubscribeEvent
    public void onDamageEvent(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        BulletEntity bullet = (BulletEntity)source.getEntity();

        if (bullet != null) {
            System.out.println("bullet event actually fired!");
            if (bullet.isClean) {
                System.out.println("bullet event actually cancelled!");
                event.setCanceled(true);
            }
        }
    }
}
