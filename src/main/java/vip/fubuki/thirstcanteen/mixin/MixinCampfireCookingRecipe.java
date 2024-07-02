package vip.fubuki.thirstcanteen.mixin;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vip.fubuki.thirstcanteen.common.item.Canteen;

@Mixin(AbstractCookingRecipe.class)
public class MixinCampfireCookingRecipe {

    @Mutable
    @Shadow @Final protected ItemStack result;

    @Shadow @Final protected RecipeType<?> type;

    @Inject(method = "matches", at =@At("TAIL"))
    public void matches(Container container, Level level, CallbackInfoReturnable<Boolean> cir){
        if(cir.getReturnValue() && container.getItem(0).getItem() instanceof Canteen){
            assemble(container);
        }
    }

    private void assemble(Container container){
        int purity;
        int damage;
        int added = this.type==RecipeType.CAMPFIRE_COOKING?1:2;

        ItemStack stack = container.getItem(0);

        CompoundTag compoundTag = stack.getOrCreateTag();
        purity = Math.min(compoundTag.getInt("Purity")+added,3);
        damage = compoundTag.getInt("Damage");

        ItemStack result0 = this.result.copy();
        CompoundTag tag = result0.getOrCreateTag();
        tag.putInt("Purity",purity);
        tag.putInt("Damage",damage);
        this.result = result0;

    }
}
