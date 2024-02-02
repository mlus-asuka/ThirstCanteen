package vip.fubuki.thirstcanteen.tab;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

public class ThirstCanteenTab extends CreativeModeTab {

    public static final ThirstCanteenTab THIRST_CANTEEN_TAB = new ThirstCanteenTab("ThirstCanteen");

    public ThirstCanteenTab(String label) {
        super(label);
    }

    @Override
    public ItemStack makeIcon() {
        return ThirstCanteenItem.MILITARY_BOTTLE.get().getDefaultInstance();
    }
}
