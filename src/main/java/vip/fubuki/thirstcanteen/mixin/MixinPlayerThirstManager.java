package vip.fubuki.thirstcanteen.mixin;

import dev.ghen.thirst.api.ThirstHelper;
import dev.ghen.thirst.content.purity.WaterPurity;
import dev.ghen.thirst.content.thirst.PlayerThirstManager;
import dev.ghen.thirst.foundation.common.capability.IThirst;
import dev.ghen.thirst.foundation.common.capability.ModAttachment;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vip.fubuki.thirstcanteen.common.item.Canteen;
import vip.fubuki.thirstcanteen.config.ThirstCanteenConfig;

@Mixin(value = PlayerThirstManager.class,remap = false)
public class MixinPlayerThirstManager {
    @Inject(method = "drink",at= @At(value = "HEAD"), cancellable = true)
    private static void getThirst(LivingEntityUseItemEvent.Finish event, CallbackInfo ci){
        if (event.getEntity() instanceof Player && ThirstHelper.itemRestoresThirst(event.getItem())) {
            IThirst cap = event.getEntity().getData(ModAttachment.PLAYER_THIRST);
            ItemStack item = event.getItem();
            if (WaterPurity.givePurityEffects((Player)event.getEntity(), item)) {
                if(item.getItem() instanceof Canteen){
                    cap.drink(ThirstCanteenConfig.THIRST_RESTORE_EACH_SIP.get().intValue(), ThirstCanteenConfig.QUENCHED_RESTORE_EACH_SIP.get().intValue());
                }else {
                    cap.drink(ThirstHelper.getThirst(item), ThirstHelper.getQuenched(item));
                }
            }
        }
        ci.cancel();
    }
}
