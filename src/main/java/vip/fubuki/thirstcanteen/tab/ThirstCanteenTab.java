package vip.fubuki.thirstcanteen.tab;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import vip.fubuki.thirstcanteen.ThirstCanteen;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

public class ThirstCanteenTab{

    public static final DeferredRegister<CreativeModeTab> THIRST_CANTEEN_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ThirstCanteen.MODID);

    static final RegistryObject<CreativeModeTab> DEFAULT = THIRST_CANTEEN_TAB.register("default", () -> CreativeModeTab
            .builder().title(Component.translatable("itemGroup." + ThirstCanteen.MODID))
            .icon(() -> new ItemStack(ThirstCanteenItem.MILITARY_BOTTLE.get()))
            .displayItems((params, output) -> ThirstCanteenItem.ITEMS.getEntries().forEach(item -> output.accept(item.get()))).build());
}
