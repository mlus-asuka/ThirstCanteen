package vip.fubuki.thirstcanteen.common.item;

import dev.ghen.thirst.content.purity.WaterPurity;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class Canteen extends Item implements Drinkable{

    Supplier<Integer> usableTime;
    Supplier<ItemStack> container;
    Supplier<Integer> defaultPurity;

    public Canteen(Properties properties, Supplier<Integer> usableTime, Supplier<ItemStack> container, Supplier<Integer> defaultPurity) {
        super(properties.stacksTo(1));
        this.usableTime = usableTime;
        this.container = container;
        this.defaultPurity = defaultPurity;
    }

    public Canteen(Properties properties, Supplier<Integer> usableTime, Supplier<ItemStack> container) {
        this(properties.stacksTo(1), usableTime, container, ()-> 0);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity user) {
        return 32;
    }

    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack p_42997_) {
        return UseAnim.DRINK;
    }

    @Override
    public int getMaxUsableTimes() {
        return usableTime.get();
    }

    @Override
    public int getLeftUsableTimes(ItemStack itemStack) {
        itemStack.getComponents().get(DataComponents.CUSTOM_DATA);
        CustomData customData = itemStack.getComponents().get(DataComponents.CUSTOM_DATA);
        CompoundTag compoundTag = (customData != null) ? customData.copyTag() : new CompoundTag();
        return compoundTag.getInt("Contain");
    }

    public int getDefaultPurity(){
        return Math.min(defaultPurity.get(), 3);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity entity) {
        Player player = entity instanceof Player ? (Player)entity : null;
        if(player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, itemStack);
            serverPlayer.gameEvent(GameEvent.EAT);
            serverPlayer.getFoodData().eat(0,0);

            setContain(itemStack,getLeftUsableTimes(itemStack) - 1);

            int times = getLeftUsableTimes(itemStack);

            player.awardStat(Stats.ITEM_USED.get(this));
            if (times == 0 && !player.getAbilities().instabuild) {
                ItemStack containerStack = new ItemStack(container.get().getItem());

                if (containerStack.isEmpty() || !player.getInventory().add(containerStack)) {
                    player.drop(containerStack, false);
                }
                itemStack.shrink(1);
                return itemStack;
            }
        }
        if(player != null)
            player.awardStat(Stats.ITEM_USED.get(this));
        return itemStack;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);

        if(getLeftUsableTimes(stack) == getMaxUsableTimes()){
            player.startUsingItem(interactionHand);
            return InteractionResultHolder.pass(stack);
        }


        boolean handled = false;
        BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        BlockPos blockPos = blockHitResult.getBlockPos();
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        BlockState blockState = level.getBlockState(blockPos);

        int needed = stack.getDamageValue();

        if(level.getFluidState(blockPos).is(FluidTags.WATER)){
            setContain(stack, getMaxUsableTimes());
            handled=true;
        }else if(blockEntity != null){
            //Handle with Fluid Capability
            IFluidHandler iFluidHandler = Capabilities.FluidHandler.BLOCK.getCapability(level,blockPos,blockState,blockEntity,null);
            if (iFluidHandler != null){
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
                    return InteractionResultHolder.pass(stack);
                iFluidHandler.drain(actual * 250, IFluidHandler.FluidAction.EXECUTE);
                setContain(stack,Math.min(getMaxUsableTimes() , needed + actual));
                WaterPurity.addPurity(stack,Math.min(Math.max(getDefaultPurity(), purity), WaterPurity.getPurity(stack)));
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
        } else if (blockState.getBlock() instanceof LayeredCauldronBlock) {
            int waterLevel = blockState.getValue(LayeredCauldronBlock.LEVEL);
            int actual = Math.min(needed,waterLevel);
            if(actual<=0)
                return InteractionResultHolder.pass(stack);
            if (waterLevel - actual > 0) {
                blockState.setValue(LayeredCauldronBlock.LEVEL, waterLevel-actual);
            }else {
                blockState = Blocks.CAULDRON.defaultBlockState();
            }
            level.setBlockAndUpdate(blockPos, blockState);

            setContain(stack,Math.min(getMaxUsableTimes(), needed + waterLevel));
            handled = true;
        }else {
            player.startUsingItem(interactionHand);
        }

        if(handled){
            WaterPurity.addPurity(stack,Math.min(Math.max(getDefaultPurity(), WaterPurity.getBlockPurity(level,blockPos)),WaterPurity.getPurity(stack)));
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
        }

        return InteractionResultHolder.success(player.getItemInHand(interactionHand));
    }

    public static void setContain(ItemStack itemStack,int contain){
        itemStack.getComponents().get(DataComponents.CUSTOM_DATA);
        CustomData customData = itemStack.getComponents().get(DataComponents.CUSTOM_DATA);
        CompoundTag compoundTag = (customData != null) ? customData.copyTag() : new CompoundTag();
        compoundTag.putInt("Contain", Math.max(0, contain));
        itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(compoundTag));
    }
}
