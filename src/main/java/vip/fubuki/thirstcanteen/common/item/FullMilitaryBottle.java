package vip.fubuki.thirstcanteen.common.item;

import dev.ghen.thirst.foundation.tab.ThirstTab;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

public class FullMilitaryBottle extends Canteen{
    public FullMilitaryBottle() {
        super(new Properties()
                .stacksTo(1)
                .tab(ThirstTab.THIRST_TAB));
        this.usableTime = 6;
        this.container = ThirstCanteenItem.MILITARY_BOTTLE.get().getDefaultInstance();
        this.defaultPurity=0;
    }

}
