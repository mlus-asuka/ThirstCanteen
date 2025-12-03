package vip.fubuki.thirstcanteen.compat;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vip.fubuki.thirstcanteen.ThirstCanteen;
import vip.fubuki.thirstcanteen.common.item.Canteen;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

@Mod.EventBusSubscriber
public class CanteenCreativeTabHandler {
    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation("legendarysurvivaloverhaul","legendary_creatures"))){
            addCanteens(event);
        }

        if (event.getTabKey() == ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation("thirst","thirst"))) {
            addCanteens(event);
        }
    }

    private static void addCanteens(BuildCreativeModeTabContentsEvent event) {
        ThirstCanteenItem.ITEMS.getEntries().forEach(itemRegistryObject -> {
            ItemStack itemStack = itemRegistryObject.get().getDefaultInstance();
            if(itemStack.getItem() instanceof Canteen canteen){
                itemStack.getOrCreateTag().putInt("Contain", canteen.getMaxUsableTimes());
                if(ThirstCanteen.thirstLoaded)
                    itemStack.getOrCreateTag().putInt("Purity", canteen.getDefaultPurity());
            }
            event.accept(itemStack);
        });
    }
}
