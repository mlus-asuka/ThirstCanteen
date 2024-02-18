package vip.fubuki.thirstcanteen;

import com.mojang.logging.LogUtils;
import dev.ghen.thirst.api.ThirstHelper;
import dev.ghen.thirst.content.purity.WaterPurity;
import dev.ghen.thirst.foundation.common.capability.ModCapabilities;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import vip.fubuki.thirstcanteen.common.crafting.CanteenCampfireRecipeSerializer;
import vip.fubuki.thirstcanteen.common.crafting.CanteenSmeltingRecipeSerializer;
import vip.fubuki.thirstcanteen.common.item.Canteen;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

import java.util.List;
import java.util.Objects;

@Mod("thirstcanteen")
public class ThirstCanteen
{
    public static final Logger LOGGER = LogUtils.getLogger();

    public ThirstCanteen()
    {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        ThirstCanteenItem.ITEMS.register(modBus);
    }


    @SuppressWarnings("unused")
    @Mod.EventBusSubscriber
    public static class ListeningEvents
    {
        private static boolean init = false;

        @SubscribeEvent
        public static void drinkCanteen(LivingEntityUseItemEvent.Finish event)
        {
            if(event.getEntity() instanceof Player && event.getItem().getItem() instanceof Canteen)
            {
                event.getEntity().getCapability(ModCapabilities.PLAYER_THIRST).ifPresent(cap ->
                {
                    ItemStack item = event.getItem();
                    if(WaterPurity.givePurityEffects((Player) event.getEntity(), item))
                        cap.drink((Player) event.getEntity(), 6, 8);
                });
            }
        }

        @SubscribeEvent
        public static void onRenderItemTooltips(ItemTooltipEvent event) {
            ItemStack stack = event.getItemStack();
            if(stack.getItem() instanceof Canteen canteen){
                List<Component> tooltip = event.getToolTip();

                tooltip.add(new TranslatableComponent("tooltips.drinkable",canteen.getLeftUsableTimes(stack),canteen.usableTime));

                int purity = getPurity(event.getItemStack());
                if(purity >= 0 && purity <= 3)
                {
                    String purityText = WaterPurity.getPurityText(purity);

                    int purityColor = WaterPurity.getPurityColor(purity);
                    event.getToolTip()
                            .add((new TextComponent(purityText))
                                    .setStyle(Style.EMPTY.withColor(purityColor)));
                        }

            }

            if(stack.is(ThirstCanteenItem.LEATHER_CANTEEN.get())){
                event.getToolTip().add(Component.nullToEmpty("Thanks SquARzY for drawing this."));
            }
        }

        public static int getPurity(ItemStack item) {
            if (!item.getOrCreateTag().contains("Purity")) {
                if(item.is(ThirstCanteenItem.MILITARY_BOTTLE_FULL.get()))
                    item.getOrCreateTag().putInt("Purity", 0);
                if(item.is(ThirstCanteenItem.DRAGON_BOTTLE_FULL.get()))
                    item.getOrCreateTag().putInt("Purity", 2);
            }

            return Objects.requireNonNull(item.getTag()).getInt("Purity");
        }

        private static void DrinkList(){
            ThirstHelper.addDrink(ThirstCanteenItem.MILITARY_BOTTLE_FULL.get(),6,8);
            ThirstHelper.addDrink(ThirstCanteenItem.DRAGON_BOTTLE_FULL.get(),6,8);
            ThirstHelper.addDrink(ThirstCanteenItem.LEATHER_CANTEEN_FULL.get(),6,8);
        }

        @SubscribeEvent
        public static void registerDrinks(LivingEntityUseItemEvent.Finish event){
            if(!init){
                DrinkList();
                init = true;
            }
        }

        @SubscribeEvent
        public static void registerDrinks(RenderTooltipEvent.GatherComponents event){
            if(!init){
                DrinkList();
                init = true;
            }
        }
    }

    @SuppressWarnings("unused")
    @Mod.EventBusSubscriber(modid = "thirstcanteen",bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents{
        @SubscribeEvent
        public static void registerRecipes(RegistryEvent.Register<RecipeSerializer<?>> event) {
            event.getRegistry().register(CanteenSmeltingRecipeSerializer.INSTANCE
                    .setRegistryName("canteen_smelting"));
            event.getRegistry().register(CanteenCampfireRecipeSerializer.INSTANCE
                    .setRegistryName("canteen_campfire_cooking"));
        }
    }
}
