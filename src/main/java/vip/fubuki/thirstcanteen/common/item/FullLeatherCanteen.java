package vip.fubuki.thirstcanteen.common.item;

import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;
import vip.fubuki.thirstcanteen.tab.ThirstCanteenTab;

public class FullLeatherCanteen extends Canteen{
    public FullLeatherCanteen() {
        super(new Properties()
                .stacksTo(1)
                .tab(ThirstCanteenTab.THIRST_CANTEEN_TAB));
        this.usableTime = 4;
        this.container = ThirstCanteenItem.LEATHER_CANTEEN.get().getDefaultInstance();
        this.defaultPurity=0;
    }
}
