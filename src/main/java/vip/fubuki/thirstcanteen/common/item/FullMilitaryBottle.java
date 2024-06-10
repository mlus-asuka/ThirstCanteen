package vip.fubuki.thirstcanteen.common.item;

import dev.ghen.thirst.foundation.tab.ThirstTab;
import vip.fubuki.thirstcanteen.config.ThirstCanteenConfig;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

public class FullMilitaryBottle extends Canteen{
    public FullMilitaryBottle() {
        super(new Properties()
                .tab(ThirstTab.THIRST_TAB), ThirstCanteenConfig.MILITARY_BOTTLE_CONTAIN.get().intValue());
        this.usableTime = ThirstCanteenConfig.MILITARY_BOTTLE_CONTAIN.get().intValue();
        this.container = ThirstCanteenItem.MILITARY_BOTTLE.get().getDefaultInstance();
        this.defaultPurity=0;
    }

}
