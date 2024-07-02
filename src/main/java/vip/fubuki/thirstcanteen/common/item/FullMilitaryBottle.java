package vip.fubuki.thirstcanteen.common.item;

import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

public class FullMilitaryBottle extends Canteen{
    public FullMilitaryBottle() {
        super(new Properties()
                , 12);
        this.usableTime = 12;
        this.container = ThirstCanteenItem.MILITARY_BOTTLE.get().getDefaultInstance();
        this.defaultPurity=0;
    }

}
