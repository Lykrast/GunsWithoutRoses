package lykrast.gunswithoutroses.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class HungerBulletItem extends BulletItem {

	public HungerBulletItem(Properties properties, int damage) {
		super(properties, damage);
	}
	
	@Override
	public void consume(ItemStack stack, PlayerEntity player) {
		if (player.getFoodStats().getFoodLevel() <= 0) player.attackEntityFrom(DamageSource.STARVE, 1);
		player.addExhaustion(2);
	}
}
