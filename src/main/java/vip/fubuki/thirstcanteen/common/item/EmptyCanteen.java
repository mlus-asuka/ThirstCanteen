package vip.fubuki.thirstcanteen.common.item;

import dev.ghen.thirst.content.purity.WaterPurity;
import dev.ghen.thirst.foundation.util.MathHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class EmptyCanteen extends Item {

    public EmptyCanteen(Properties properties) {
        super(properties);
    }


    public @NotNull InteractionResult useOn(UseOnContext context,ItemStack result,int defaultPurity) {
        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();
        Level level = player.level();

        BlockPos blockPos = MathHelper.getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY).getBlockPos();

        if(context.getLevel().getFluidState(blockPos).is(FluidTags.WATER)){
            stack.shrink(1);
            player.getInventory().add(WaterPurity.addPurity(result,Math.min(defaultPurity,WaterPurity.getBlockPurity(level,blockPos))));
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
        }
        return InteractionResult.SUCCESS;
    }
}
