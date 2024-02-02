package vip.fubuki.thirstcanteen.mixin;

import dev.ghen.thirst.api.ThirstHelper;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vip.fubuki.thirstcanteen.common.item.Canteen;

@Mixin(value = ThirstHelper.class,remap = false)
public class MixinThirstHelper {
    @Inject(method = "getThirst", at=@At("HEAD"), cancellable = true)
    private static void getThirst(ItemStack itemStack, CallbackInfoReturnable<Integer> cir){
       if(itemStack.getItem() instanceof Canteen canteen){
           cir.setReturnValue(canteen.getLeftUsableTimes(itemStack) * 6);
       }
    }

    @Inject(method = "getQuenched", at=@At("HEAD"), cancellable = true)
    private static void getQuenched(ItemStack itemStack, CallbackInfoReturnable<Integer> cir){
        if(itemStack.getItem() instanceof Canteen canteen){
            cir.setReturnValue(canteen.getLeftUsableTimes(itemStack) * 8);
        }
    }
}
