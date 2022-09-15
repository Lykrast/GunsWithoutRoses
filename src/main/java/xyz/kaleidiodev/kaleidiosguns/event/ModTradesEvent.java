package xyz.kaleidiodev.kaleidiosguns.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.kaleidiodev.kaleidiosguns.KaleidiosGuns;
import xyz.kaleidiodev.kaleidiosguns.registry.ModItems;

import java.util.List;

@Mod.EventBusSubscriber(modid = KaleidiosGuns.MODID)
public class ModTradesEvent {
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.WEAPONSMITH) {
            Int2ObjectMap<List<VillagerTrades.ITrade>> trades = event.getTrades();

            trades.get(5).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 15), new ItemStack(ModItems.blessedPistol, 1), 1, 50, 0.3F));
        }

        if (event.getType() == VillagerProfession.ARMORER) {
            Int2ObjectMap<List<VillagerTrades.ITrade>> trades = event.getTrades();

            trades.get(5).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 30), new ItemStack(ModItems.defenderRifle, 1), 1, 80, 0.3F));
        }

        if (event.getType() == VillagerProfession.CLERIC) {
            Int2ObjectMap<List<VillagerTrades.ITrade>> trades = event.getTrades();

            trades.get(5).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 45), new ItemStack(ModItems.xpBullet, 1), 1, 32, 0.3F));
        }
    }
}
