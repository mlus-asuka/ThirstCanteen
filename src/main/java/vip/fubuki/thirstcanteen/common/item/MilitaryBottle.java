package vip.fubuki.thirstcanteen.common.item;

import dev.ghen.thirst.foundation.tab.ThirstTab;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

public class MilitaryBottle extends EmptyCanteen {
    public MilitaryBottle() {
        super(new Properties().stacksTo(1).tab(ThirstTab.THIRST_TAB));
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        return this.useOn(context,ThirstCanteenItem.MILITARY_BOTTLE_FULL.get().getDefaultInstance(),0);
    }
}
