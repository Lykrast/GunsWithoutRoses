package lykrast.gunswithoutroses.registry;

import lykrast.gunswithoutroses.GunsWithoutRoses;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GWREffects {
	public static RegistryObject<MobEffect> dmgUp, dmgDown, fireUp, fireDown, accuracyUp, accuracyDown, ammoUp, knockbackUp;
	public static final DeferredRegister<MobEffect> REG = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, GunsWithoutRoses.MODID);
	
	static {
		dmgUp = REG.register("gun_damage_up", () -> new Effect(true, 0xFF9400).addAttributeModifier(GWRAttributes.dmgBase.get(), "9db8a059-1dbf-4764-8b4d-13fca16fc301", 2, AttributeModifier.Operation.ADDITION));
		dmgDown = REG.register("gun_damage_down", () -> new Effect(false, 0x4C3A34).addAttributeModifier(GWRAttributes.dmgBase.get(), "11666b87-d644-4a4a-8010-a892e474e7a1", -3, AttributeModifier.Operation.ADDITION));
		fireUp = REG.register("gun_fire_up", () -> new Effect(true, 0x32FFD9).addAttributeModifier(GWRAttributes.fireDelay.get(), "8be4778e-b426-4369-b3a8-ac2f09f6a0fd", -0.2, AttributeModifier.Operation.MULTIPLY_TOTAL));
		fireDown = REG.register("gun_fire_down", () -> new Effect(false, 0xBAD3DD).addAttributeModifier(GWRAttributes.fireDelay.get(), "7ebf0a25-3b89-49d7-9b80-b7505c4bc54f", 0.25, AttributeModifier.Operation.MULTIPLY_TOTAL));
		accuracyUp = REG.register("gun_accuracy_up", () -> new Effect(true, 0x7BBA57).addAttributeModifier(GWRAttributes.spread.get(), "3b430e49-bbbc-4974-afd7-d819ce60a88a", -0.3, AttributeModifier.Operation.MULTIPLY_TOTAL));
		accuracyDown = REG.register("gun_accuracy_down", () -> new Effect(false, 0xBFAAB1).addAttributeModifier(GWRAttributes.spread.get(), "225800de-1add-4c87-825a-7929bb55132e", 1, AttributeModifier.Operation.MULTIPLY_TOTAL));
		ammoUp = REG.register("gun_ammo_up", () -> new Effect(true, 0xB8C1B4).addAttributeModifier(GWRAttributes.chanceUseAmmo.get(), "06882803-21bd-4e96-8f73-b9d61dff625a", -0.25, AttributeModifier.Operation.MULTIPLY_TOTAL));
		knockbackUp = REG.register("gun_knockback_up", () -> new Effect(true, 0xD3DBAB).addAttributeModifier(GWRAttributes.knockback.get(), "937db97a-fc55-4797-b795-cfb2a12cfc74", 1, AttributeModifier.Operation.ADDITION));
	}
	
	public static class Effect extends MobEffect {
		//WHAT DO YOU MEAN THE CONSTRUCTOR IS PROTECTED???
		public Effect(boolean good, int color) {
			super(good ? MobEffectCategory.BENEFICIAL : MobEffectCategory.HARMFUL, color);
		}
		
	}
}
