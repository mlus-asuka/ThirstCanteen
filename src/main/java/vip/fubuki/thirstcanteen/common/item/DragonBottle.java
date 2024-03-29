package vip.fubuki.thirstcanteen.common.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;
import vip.fubuki.thirstcanteen.tab.ThirstCanteenTab;

public class DragonBottle extends EmptyCanteen {
    public DragonBottle() {
        super(new Properties()
                .stacksTo(1)
                .rarity(Rarity.EPIC)
                .tab(ThirstCanteenTab.THIRST_CANTEEN_TAB));
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        return this.useOn(context,ThirstCanteenItem.DRAGON_BOTTLE_FULL.get().getDefaultInstance(),2);
    }
}
