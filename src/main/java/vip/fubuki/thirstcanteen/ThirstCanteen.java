package vip.fubuki.thirstcanteen;

import dev.ghen.thirst.content.registry.ThirstComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import vip.fubuki.thirstcanteen.common.item.Canteen;
import vip.fubuki.thirstcanteen.compat.ThirstCompatEvent;
import vip.fubuki.thirstcanteen.config.ThirstCanteenConfig;
import vip.fubuki.thirstcanteen.registry.RegistryRecipe;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

import java.util.List;

@Mod(ThirstCanteen.MODID)
public class ThirstCanteen
{
    public static final String MODID = "thirstcanteen";
    public static boolean legendSurvivalOverhaulLoaded = false;
    public static boolean thirstLoaded = false;

    public ThirstCanteen(IEventBus modBus, ModContainer modContainer)
    {
        ThirstCanteenConfig.setup(modContainer);
        NeoForge.EVENT_BUS.register(this);
        ThirstCanteenItem.ITEMS.register(modBus);
        RegistryRecipe.recipeRegister.register(modBus);

        if(ModList.get().isLoaded("thirst")){
            thirstLoaded = true;
            ThirstCompatEvent.register(NeoForge.EVENT_BUS);
        }

        if(ModList.get().isLoaded("legendarysurvivaloverhaul")){
            legendSurvivalOverhaulLoaded = true;
        }
    }


    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onRenderItemTooltips(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if(stack.getItem() instanceof Canteen canteen){
            List<Component> tooltip = event.getToolTip();
            tooltip.add(Component.translatable("tooltips.drinkable",canteen.getLeftUsableTimes(stack),canteen.getMaxUsableTimes()));

            if(thirstLoaded)
                setPurity(event.getItemStack());
        }

        if(stack.is(ThirstCanteenItem.LEATHER_CANTEEN)){
            event.getToolTip().add(Component.nullToEmpty("Thanks SquARzY for drawing this."));
        }
    }

    public static void setPurity(ItemStack item) {
        if (item.get(ThirstComponent.PURITY) == null) {
            if(item.getItem() instanceof Canteen canteen)
                item.set(ThirstComponent.PURITY, canteen.getDefaultPurity());
        }
    }
}
