package vip.fubuki.thirstcanteen.common.item;

import net.minecraft.world.item.ItemStack;

public interface Drinkable {
    int getMaxUsableTimes();
    int getLeftUsableTimes(ItemStack itemStack);
}
