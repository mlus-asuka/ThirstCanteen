package vip.fubuki.thirstcanteen.common.item;

import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

public class FullMilitaryBottle extends Canteen{
    public FullMilitaryBottle() {
        super(new Properties(),12,ThirstCanteenItem.MILITARY_BOTTLE.get().getDefaultInstance());
    }

}
