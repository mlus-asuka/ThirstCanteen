package vip.fubuki.thirstcanteen.common.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;
import vip.fubuki.thirstcanteen.tab.ThirstCanteenTab;

public class LeatherCanteen extends EmptyCanteen {

    public LeatherCanteen() {
        super(new Properties().stacksTo(1).tab(ThirstCanteenTab.THIRST_CANTEEN_TAB));
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        return this.useOn(context,ThirstCanteenItem.LEATHER_CANTEEN_FULL.get().getDefaultInstance(),0);
    }
}
