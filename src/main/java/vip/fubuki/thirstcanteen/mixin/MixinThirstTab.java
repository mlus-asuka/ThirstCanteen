package vip.fubuki.thirstcanteen.mixin;

import dev.ghen.thirst.foundation.tab.ThirstTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

import java.util.Collection;

@Mixin(value = ThirstTab.class,remap = false)
public class MixinThirstTab {
    @Inject(method = "DisplayItems",at = @At(value = "RETURN"), cancellable = true)
    private static void AddItemToTab(CallbackInfoReturnable<Collection<ItemStack>> cir){
        Collection<ItemStack> items = cir.getReturnValue();
        ThirstCanteenItem.ITEMS.getEntries().forEach(itemRegistryObject -> items.add(itemRegistryObject.get().getDefaultInstance()));
        cir.setReturnValue(items);
    }
}
