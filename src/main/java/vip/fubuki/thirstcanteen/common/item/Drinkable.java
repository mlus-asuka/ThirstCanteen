package vip.fubuki.thirstcanteen.common.item;

import net.minecraft.world.item.ItemStack;

public interface Drinkable {

    int getUsableTimes();
    int getLeftUsableTimes(ItemStack itemStack);

}
