package vip.fubuki.thirstcanteen.common.item;

import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

public class FullLeatherCanteen extends Canteen{
    public FullLeatherCanteen() {
        super(new Properties()
                .stacksTo(1));
        this.usableTime = 4;
        this.container = ThirstCanteenItem.LEATHER_CANTEEN.get().getDefaultInstance();
        this.defaultPurity=0;
    }
}
