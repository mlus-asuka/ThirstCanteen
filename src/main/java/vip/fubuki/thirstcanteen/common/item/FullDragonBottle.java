package vip.fubuki.thirstcanteen.common.item;

import dev.ghen.thirst.foundation.tab.ThirstTab;
import net.minecraft.world.item.Rarity;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

public class FullDragonBottle extends Canteen{
    public FullDragonBottle() {
        super(new Properties()
                .stacksTo(1)
                .rarity(Rarity.EPIC)
                .tab(ThirstTab.THIRST_TAB));
        this.usableTime =12;
        this.container = ThirstCanteenItem.DRAGON_BOTTLE.get().getDefaultInstance();
        this.defaultPurity=2;
    }
}
