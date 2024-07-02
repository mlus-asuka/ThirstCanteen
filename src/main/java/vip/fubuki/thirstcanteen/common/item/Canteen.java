package vip.fubuki.thirstcanteen.common.item;

import dev.ghen.thirst.content.purity.WaterPurity;
import dev.ghen.thirst.foundation.util.MathHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public abstract class Canteen extends Item implements Drinkable{

    public int usableTime;
    public ItemStack container;
    public int defaultPurity;
    public Canteen(Properties properties,int usableTime) {
        super(properties.defaultDurability(usableTime));
    }

    public int getUseDuration(@NotNull ItemStack p_43001_) {
        return 32;
    }

    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack p_42997_) {
        return UseAnim.DRINK;
    }

    @Override
    public int getMaxUsableTimes() {
        return usableTime;
    }

    @Override
    public int getLeftUsableTimes(ItemStack itemStack) {
        return usableTime - itemStack.getDamageValue();
    }

    public static void spawnItemEntity(Level level, ItemStack stack, double x, double y, double z, double xMotion, double yMotion, double zMotion) {
        ItemEntity entity = new ItemEntity(level, x, y, z, stack);
        entity.setDeltaMovement(xMotion, yMotion, zMotion);
        level.addFreshEntity(entity);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity entity) {
        Player player = entity instanceof Player ? (Player)entity : null;
        if(player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, itemStack);
            level.gameEvent(entity, GameEvent.EAT, entity.getOnPos());
            serverPlayer.getFoodData().eat(0,0);

            itemStack.hurt(1,level.random,serverPlayer);

            int times = getLeftUsableTimes(itemStack);
            if (times == 0) {
                if (!player.getAbilities().instabuild)
                    itemStack.shrink(1);
                spawnItemEntity(level,container,player.getX(),player.getY(), player.getZ(), 0,0,0);
            }
        }
        if(player != null)
            player.awardStat(Stats.ITEM_USED.get(this));
        return itemStack;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();
        Level level = player.level();

        if(!stack.isDamaged())
            return InteractionResult.PASS;

        boolean handled = false;
        BlockPos blockPos = MathHelper.getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY).getBlockPos();
        BlockEntity blockEntity = context.getLevel().getBlockEntity(context.getClickedPos());
        BlockState blockState = context.getLevel().getBlockState(context.getClickedPos());

        int needed = stack.getDamageValue();

        if(context.getLevel().getFluidState(blockPos).is(FluidTags.WATER)){
            stack.getOrCreateTag().putInt("Damage", 0);
            handled=true;
        }else if(blockEntity != null){
            //Handle with Fluid Capability
            LazyOptional<IFluidHandler> capability = blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER);

            if (capability.isPresent()){
                IFluidHandler iFluidHandler = capability.orElse(null);
                int totalAmount=0;
                int purity=0;
                for (int i = 0; i < iFluidHandler.getTanks(); i++) {
                    if (iFluidHandler.getFluidInTank(i).getFluid() != Fluids.WATER)
                        break;
                    else {
                        totalAmount+=iFluidHandler.getFluidInTank(i).getAmount();
                        purity = Math.min(purity,WaterPurity.getPurity(iFluidHandler.getFluidInTank(i)));

                    }
                }
                totalAmount=totalAmount/250;
                int actual = Math.min(needed,totalAmount);
                if(actual<=0)
                    return InteractionResult.PASS;
                iFluidHandler.drain(actual*250, IFluidHandler.FluidAction.EXECUTE);
                stack.getOrCreateTag().putInt("Damage",Math.max(0,needed - actual));
                WaterPurity.addPurity(stack,Math.min(Math.max(defaultPurity,purity),WaterPurity.getPurity(stack)));
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

            stack.getOrCreateTag().putInt("Damage",Math.max(0,needed - waterLevel));
            handled=true;
        }else {
            player.startUsingItem(context.getHand());
        }

        if(handled){
            WaterPurity.addPurity(stack,Math.min(Math.max(defaultPurity,WaterPurity.getBlockPurity(level,blockPos)),WaterPurity.getPurity(stack)));
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
        }

        return InteractionResult.SUCCESS;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionHand) {
        player.startUsingItem(interactionHand);
        return InteractionResultHolder.success(player.getItemInHand(interactionHand));
    }
}
