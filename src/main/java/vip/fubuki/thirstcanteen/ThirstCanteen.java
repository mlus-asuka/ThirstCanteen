package vip.fubuki.thirstcanteen;

import dev.ghen.thirst.content.registry.ThirstComponent;
import dev.ghen.thirst.foundation.common.event.RegisterThirstValueEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
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

    public ThirstCanteen(IEventBus modBus, ModContainer modContainer)
    {
        ThirstCanteenConfig.setup(modContainer);
        NeoForge.EVENT_BUS.register(this);
        ThirstCanteenItem.ITEMS.register(modBus);
        RegistryRecipe.recipeRegister.register(modBus);
    }


    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onRenderItemTooltips(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if(stack.getItem() instanceof Canteen canteen){
            List<Component> tooltip = event.getToolTip();
            tooltip.add(Component.translatable("tooltips.drinkable",canteen.getLeftUsableTimes(stack),canteen.usableTime));
            setPurity(event.getItemStack());
        }

        if(stack.is(ThirstCanteenItem.LEATHER_CANTEEN)){
            event.getToolTip().add(Component.nullToEmpty("Thanks SquARzY for drawing this."));
        }
    }

    public static void setPurity(ItemStack item) {
        if (item.get(ThirstComponent.PURITY) == null) {
            if(item.is(ThirstCanteenItem.MILITARY_BOTTLE_FULL.get()) || item.is(ThirstCanteenItem.LEATHER_CANTEEN_FULL.get()))
                item.set(ThirstComponent.PURITY, 0);
            if(item.is(ThirstCanteenItem.DRAGON_BOTTLE_FULL.get()))
                item.set(ThirstComponent.PURITY, 2);
        }
    }

    @SubscribeEvent
    public void registerDrinks(RegisterThirstValueEvent event){
        event.addDrink(ThirstCanteenItem.MILITARY_BOTTLE_FULL.get(),6,8);
        event.addDrink(ThirstCanteenItem.DRAGON_BOTTLE_FULL.get(),6,8);
        event.addDrink(ThirstCanteenItem.LEATHER_CANTEEN_FULL.get(),6,8);
        event.addContainer(ThirstCanteenItem.MILITARY_BOTTLE_FULL.get());
        event.addContainer(ThirstCanteenItem.DRAGON_BOTTLE_FULL.get());
        event.addContainer(ThirstCanteenItem.LEATHER_CANTEEN_FULL.get());
    }
}
