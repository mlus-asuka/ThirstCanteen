package vip.fubuki.thirstcanteen.common.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

public class LeatherCanteen extends EmptyCanteen {

    public LeatherCanteen() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        return this.useOn(context,ThirstCanteenItem.LEATHER_CANTEEN_FULL.get().getDefaultInstance(),0);
    }
}
