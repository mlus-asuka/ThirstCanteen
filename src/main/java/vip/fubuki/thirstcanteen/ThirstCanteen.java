package vip.fubuki.thirstcanteen;

import com.mojang.logging.LogUtils;
import dev.ghen.thirst.foundation.common.event.RegisterThirstValueEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import vip.fubuki.thirstcanteen.common.item.Canteen;
import vip.fubuki.thirstcanteen.config.ThirstCanteenConfig;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

import java.util.List;

@Mod(ThirstCanteen.MODID)
public class ThirstCanteen
{
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "thirstcanteen";

    public ThirstCanteen()
    {
        ThirstCanteenConfig.setup();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        ThirstCanteenItem.ITEMS.register(modBus);
    }


    @SuppressWarnings("unused")
    @Mod.EventBusSubscriber
    public static class ListeningEvents
    {

        @SubscribeEvent(priority = EventPriority.HIGH)
        public static void onRenderItemTooltips(ItemTooltipEvent event) {
            ItemStack stack = event.getItemStack();
            if(stack.getItem() instanceof Canteen canteen){
                List<Component> tooltip = event.getToolTip();
                tooltip.add(new TranslatableComponent("tooltips.drinkable",canteen.getLeftUsableTimes(stack),canteen.usableTime));
                setPurity(event.getItemStack());
            }

            if(stack.is(ThirstCanteenItem.LEATHER_CANTEEN.get())){
                event.getToolTip().add(Component.nullToEmpty("Thanks SquARzY for drawing this."));
            }
        }

        public static void setPurity(ItemStack item) {
            if (!item.getOrCreateTag().contains("Purity")) {
                if(item.is(ThirstCanteenItem.MILITARY_BOTTLE_FULL.get()) || item.is(ThirstCanteenItem.LEATHER_CANTEEN_FULL.get()))
                    item.getOrCreateTag().putInt("Purity", 0);
                if(item.is(ThirstCanteenItem.DRAGON_BOTTLE_FULL.get()))
                    item.getOrCreateTag().putInt("Purity", 2);
            }
        }

        @SubscribeEvent
        public static void registerDrinks(RegisterThirstValueEvent event){
            event.addDrink(ThirstCanteenItem.MILITARY_BOTTLE_FULL.get(),6,8);
            event.addDrink(ThirstCanteenItem.DRAGON_BOTTLE_FULL.get(),6,8);
            event.addDrink(ThirstCanteenItem.LEATHER_CANTEEN_FULL.get(),6,8);
            event.addContainer(ThirstCanteenItem.MILITARY_BOTTLE_FULL.get());
            event.addContainer(ThirstCanteenItem.DRAGON_BOTTLE_FULL.get());
            event.addContainer(ThirstCanteenItem.LEATHER_CANTEEN_FULL.get());
        }
    }
}
