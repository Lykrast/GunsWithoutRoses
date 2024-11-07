package lykrast.gunswithoutroses.item;

/**
 * @deprecated {@link lykrast.gunswithoutroses.item.GunItem#projectiles(int) Collapsted into main GunItem, call projectiles() on registering, will remove this in 1.21}
 */
@Deprecated
public class ShotgunItem extends GunItem {

	public ShotgunItem(Properties properties, int bonusDamage, double damageMultiplier, int fireDelay, double inaccuracy, int enchantability, int bulletCount) {
		super(properties, bonusDamage, damageMultiplier, fireDelay, inaccuracy, enchantability);
		projectiles(bulletCount);
	}

}
