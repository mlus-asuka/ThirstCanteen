package vip.fubuki.thirstcanteen;

import dev.ghen.thirst.content.purity.ContainerWithPurity;
import dev.ghen.thirst.content.purity.WaterPurity;
import dev.ghen.thirst.foundation.common.event.RegisterThirstValueEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vip.fubuki.thirstcanteen.common.item.Canteen;
import vip.fubuki.thirstcanteen.config.ThirstCanteenConfig;
import vip.fubuki.thirstcanteen.registry.RegistryRecipe;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

import java.util.List;

@Mod(ThirstCanteen.MODID)
public class ThirstCanteen
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "thirstcanteen";

    public ThirstCanteen()
    {
        ThirstCanteenConfig.setup();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        ThirstCanteenItem.ITEMS.register(modBus);
    }


    public static ResourceLocation location(String path) {
        return new ResourceLocation(MODID, path);
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
            WaterPurity.addContainer(new ContainerWithPurity(new ItemStack(ThirstCanteenItem.MILITARY_BOTTLE_FULL.get())));
            WaterPurity.addContainer(new ContainerWithPurity(new ItemStack(ThirstCanteenItem.DRAGON_BOTTLE_FULL.get())));
            WaterPurity.addContainer(new ContainerWithPurity(new ItemStack(ThirstCanteenItem.LEATHER_CANTEEN_FULL.get())));
//            event.addContainer(ThirstCanteenItem.MILITARY_BOTTLE_FULL.get());
//            event.addContainer(ThirstCanteenItem.DRAGON_BOTTLE_FULL.get());
//            event.addContainer(ThirstCanteenItem.LEATHER_CANTEEN_FULL.get());
        }
    }

    @SuppressWarnings("unused")
    @Mod.EventBusSubscriber(modid = MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents{
        @SubscribeEvent
        public static void registerRecipes(RegisterEvent event) {
            event.register(ForgeRegistries.RECIPE_SERIALIZERS.getRegistryKey(), RegistryRecipe::register);
        }
    }
}
