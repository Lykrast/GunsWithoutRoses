package lykrast.gunswithoutroses.registry;

import lykrast.gunswithoutroses.GunsWithoutRoses;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GWRAttributes {
	public static RegistryObject<Attribute> dmgBase, dmgTotal, fireDelay, spread, chanceUseAmmo, knockback;
	public static final DeferredRegister<Attribute> REG = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, GunsWithoutRoses.MODID);
	
	static {
		dmgBase = initAttribute("damage_base", 0, -1024, 1024, false);
		dmgTotal = initAttribute("damage_total", 1, 0, 1024, false);
		fireDelay = initAttribute("fire_delay", 1, 0, 1024, true);
		spread = initAttribute("spread", 1, 0, 1024, false);
		chanceUseAmmo = initAttribute("chance_ammo", 1, 0, 1, false);
		knockback = initAttribute("knockback", 0, 0, 1024, false);
	}
	
	private static RegistryObject<Attribute> initAttribute(String name, double base, double min, double max, boolean sync) {
		return REG.register("gwr." + name, () -> new RangedAttribute("attribute.name.gwr." + name, base, min, max).setSyncable(sync));
	}
	
	public static void playerAttributes(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, dmgBase.get());
		event.add(EntityType.PLAYER, dmgTotal.get());
		event.add(EntityType.PLAYER, fireDelay.get());
		event.add(EntityType.PLAYER, spread.get());
		event.add(EntityType.PLAYER, chanceUseAmmo.get());
		event.add(EntityType.PLAYER, knockback.get());
	}
}
