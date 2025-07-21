package lykrast.gunswithoutroses.item;

import java.util.List;

import javax.annotation.Nullable;

import lykrast.gunswithoutroses.entity.BulletEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityTeleportEvent;

public class ChorusBulletItem extends BulletItem {

	public ChorusBulletItem(Properties properties, int damage) {
		super(properties, damage);
	}

	private static final int ATTEMPTS = 16, RANGE = 16;

	@Override
	public void onLivingEntityHit(BulletEntity projectile, LivingEntity target, @Nullable Entity shooter, Level level, boolean headshot) {
		super.onLivingEntityHit(projectile, target, shooter, level, headshot);

		//Copied and adjusted from chorus fruit
		double ox = target.getX();
		double oy = target.getY();
		double oz = target.getZ();
		//using same random source as what I did on headshot
		RandomSource random = level.getRandom();

		for (int i = 0; i < ATTEMPTS; ++i) {
			double tx = ox + (random.nextDouble() - 0.5) * RANGE;
			double ty = Mth.clamp(oy + random.nextInt(RANGE) - RANGE / 2, level.getMinBuildHeight(), level.getMinBuildHeight() + ((ServerLevel) level).getLogicalHeight() - 1);
			double tz = oz + (random.nextDouble() - 0.5D) * RANGE;
			if (target.isPassenger()) target.stopRiding();

			level.gameEvent(GameEvent.TELEPORT, target.position(), GameEvent.Context.of(target));
			EntityTeleportEvent.ChorusFruit event = ForgeEventFactory.onChorusFruitTeleport(target, tx, ty, tz);
			if (event.isCanceled()) return;
			if (target.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true)) {
				level.playSound(null, ox, oy, oz, SoundEvents.CHORUS_FRUIT_TELEPORT, shooter != null ? shooter.getSoundSource() : projectile.getSoundSource(), 1, 1);
				target.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1, 1);
				break;
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		tooltip.add(Component.translatable("tooltip.gunswithoutroses.chorus_fruit_bullet").withStyle(ChatFormatting.GRAY));
	}

}
