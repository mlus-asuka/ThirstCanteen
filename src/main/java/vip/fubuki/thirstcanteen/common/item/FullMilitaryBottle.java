package vip.fubuki.thirstcanteen.common.item;

import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

public class FullMilitaryBottle extends Canteen{
    public FullMilitaryBottle() {
        super(new Properties()
                .stacksTo(1));
        this.usableTime = 6;
        this.container = ThirstCanteenItem.MILITARY_BOTTLE.get().getDefaultInstance();
        this.defaultPurity=0;
    }

}
