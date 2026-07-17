package vip.fubuki.thirstcanteen.common.item;

import cn.mlus.thirst.foundation.tab.ThirstTab;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

public class FullMilitaryBottle extends Canteen{
    public FullMilitaryBottle() {
        super(new Properties()
                .tab(ThirstTab.THIRST_TAB), 12);
        this.usableTime = 12;
        this.container = ThirstCanteenItem.MILITARY_BOTTLE.get().getDefaultInstance();
        this.defaultPurity=0;
    }

}
