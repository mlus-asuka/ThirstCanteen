package vip.fubuki.thirstcanteen.common.item;

import dev.ghen.thirst.content.purity.WaterPurity;
import dev.ghen.thirst.foundation.util.MathHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class Canteen extends Item implements Drinkable{

    public int usableTime;

    public ItemStack container;
    public int defaultPurity;
    public Canteen(Properties properties) {
        super(properties);
    }

    public int getUseDuration(@NotNull ItemStack p_43001_) {
        return 32;
    }

    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack p_42997_) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUsableTimes() {
        return usableTime;
    }

    @Override
    public int getLeftUsableTimes(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getOrCreateTag();
        if (!compoundTag.contains("Contain", 99)) {
            compoundTag.putInt("Contain", getUsableTimes());
        }
        return compoundTag.getInt("Contain");
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
            int times = getLeftUsableTimes(itemStack) - 1;
            level.gameEvent(entity, GameEvent.EAT, entity.getOnPos());
            serverPlayer.getFoodData().eat(0,0);
            if (times != 0) {
                itemStack.getOrCreateTag().putInt("Contain", times);
            } else {
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
        Level level = player.level;

        if(getLeftUsableTimes(stack) == this.usableTime)
            return InteractionResult.PASS;

        BlockPos blockPos = MathHelper.getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY).getBlockPos();

        if(context.getLevel().getFluidState(blockPos).is(FluidTags.WATER)){
            stack.getOrCreateTag().putInt("Contain", getUsableTimes());
            WaterPurity.addPurity(stack,Math.min(Math.max(defaultPurity,WaterPurity.getBlockPurity(level,blockPos)),WaterPurity.getPurity(stack)));
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
        }
        return InteractionResult.SUCCESS;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if(stack.getOrCreateTag().getInt("Contain")<=0){
            stack.shrink(1);
            spawnItemEntity(level,container,player.getX(),player.getY(),player.getZ(),0,0,0);
        }

        player.startUsingItem(interactionHand);
        return InteractionResultHolder.success(player.getItemInHand(interactionHand));
    }

}
