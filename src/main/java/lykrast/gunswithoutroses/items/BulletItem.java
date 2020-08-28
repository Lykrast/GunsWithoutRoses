package lykrast.gunswithoutroses.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BulletItem extends Item {
	public BulletItem(Properties properties) {
		super(properties);
	}

	//TODO
	public AbstractArrowEntity createProjectile(World worldIn, ItemStack stack, LivingEntity shooter) {
		ArrowEntity arrowentity = new ArrowEntity(worldIn, shooter);
		return arrowentity;
	}

}
