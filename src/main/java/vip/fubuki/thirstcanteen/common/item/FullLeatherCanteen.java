package vip.fubuki.thirstcanteen.common.item;

import dev.ghen.thirst.foundation.tab.ThirstTab;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

public class FullLeatherCanteen extends Canteen{
    public FullLeatherCanteen() {
        super(new Properties()
                .tab(ThirstTab.THIRST_TAB), 8);
        this.usableTime = 8;
        this.container = ThirstCanteenItem.LEATHER_CANTEEN.get().getDefaultInstance();
        this.defaultPurity=0;
    }
}
