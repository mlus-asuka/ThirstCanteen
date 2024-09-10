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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public class EmptyCanteen extends Item {

    public EmptyCanteen(Properties properties) {
        super(properties.stacksTo(1));
    }

    public @NotNull InteractionResult useOn(UseOnContext context, ItemStack result) {
        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();
        Level level = player.level();

        BlockPos blockPos = MathHelper.getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY).getBlockPos();
        BlockEntity blockEntity = context.getLevel().getBlockEntity(context.getClickedPos());
        BlockState blockState = context.getLevel().getBlockState(context.getClickedPos());
        int needed = ((Canteen) result.getItem()).getMaxUsableTimes();
        int defaultPurity = ((Canteen) result.getItem()).defaultPurity;
        boolean handled = false;

        if (context.getLevel().getFluidState(blockPos).is(FluidTags.WATER)) {
            result.getOrCreateTag().putInt("Damage", 0);
            handled=true;
        } else if(blockEntity != null){
            //Handle with Fluid Capability
            LazyOptional<IFluidHandler> capability = blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER);
            if (capability.isPresent()) {
                IFluidHandler iFluidHandler = capability.orElse(null);
                int totalAmount = 0;
                int purity=0;
                for (int i = 0; i < iFluidHandler.getTanks(); i++) {
                    if (iFluidHandler.getFluidInTank(i).getFluid() != Fluids.WATER)
                        break;
                    else {
                        totalAmount += iFluidHandler.getFluidInTank(i).getAmount();
                        purity = WaterPurity.getPurity(iFluidHandler.getFluidInTank(i));
                    }
                }
                totalAmount = totalAmount / 250;
                int actual = Math.min(needed, totalAmount);
                if (actual <= 0)
                    return InteractionResult.PASS;
                iFluidHandler.drain(actual * 250, IFluidHandler.FluidAction.EXECUTE);
                result.getOrCreateTag().putInt("Damage", Math.max(0, needed - actual));
                stack.shrink(1);
                WaterPurity.addPurity(result,Math.max(defaultPurity,purity));
                player.getInventory().add(result);
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
        } else if (blockState.getBlock() instanceof LayeredCauldronBlock) {
            int waterLevel = blockState.getValue(LayeredCauldronBlock.LEVEL);
            int actual = Math.min(needed,waterLevel);
            if(actual<=0)
                return InteractionResult.PASS;
            if (waterLevel - actual > 0) {
                blockState.setValue(LayeredCauldronBlock.LEVEL, waterLevel-actual);
            }else {
                blockState = Blocks.CAULDRON.defaultBlockState();
            }
            level.setBlockAndUpdate(context.getClickedPos(),blockState);


            result.getOrCreateTag().putInt("Damage",Math.max(0,needed - actual));
            handled=true;
        }

        if(handled){
            stack.shrink(1);
            WaterPurity.addPurity(result, Math.max(defaultPurity, WaterPurity.getBlockPurity(level, blockPos)));
            player.getInventory().add(result);
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
        }

        return InteractionResult.SUCCESS;
    }
}
