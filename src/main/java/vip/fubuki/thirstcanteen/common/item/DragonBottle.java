package vip.fubuki.thirstcanteen.common.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

public class DragonBottle extends EmptyCanteen {
    public DragonBottle() {
        super(new Properties()
                .stacksTo(1)
                .rarity(Rarity.EPIC));
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        return this.useOn(context,ThirstCanteenItem.DRAGON_BOTTLE_FULL.get().getDefaultInstance(),2);
    }
}
