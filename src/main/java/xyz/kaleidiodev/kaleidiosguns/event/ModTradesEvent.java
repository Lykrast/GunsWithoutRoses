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
        if (event.getType() == VillagerProfession.FLETCHER) {
            Int2ObjectMap<List<VillagerTrades.ITrade>> trades = event.getTrades();

            trades.get(4).add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 10), new ItemStack(ModItems.blessedPistol, 1), 10, 12, 0.08F));
        }
    }
}
