package vip.fubuki.thirstcanteen;

import dev.ghen.thirst.foundation.common.event.RegisterThirstValueEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import vip.fubuki.thirstcanteen.common.item.Canteen;
import vip.fubuki.thirstcanteen.config.ThirstCanteenConfig;
import vip.fubuki.thirstcanteen.mixin.MixinThirstHelper;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

import java.util.List;

@Mod(ThirstCanteen.MODID)
public class ThirstCanteen
{
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
                tooltip.add(Component.translatable("tooltips.drinkable",canteen.getLeftUsableTimes(stack),canteen.usableTime));
            }

            if(stack.is(ThirstCanteenItem.LEATHER_CANTEEN.get())){
                event.getToolTip().add(Component.nullToEmpty("Thanks SquARzY for drawing this."));
            }
        }

        /** Just make them be recognized as drink, don't mind the detail
         *  See #{@link MixinThirstHelper}
         */
        @SubscribeEvent
        public static void registerDrinks(RegisterThirstValueEvent event){
            ThirstCanteenItem.ITEMS.getEntries().forEach(itemRegistryObject ->{
                if(itemRegistryObject.get() instanceof Canteen canteen){
                    event.addDrink(canteen,6,8);
                    event.addContainer(canteen);
                }
            } );
        }
    }
}
