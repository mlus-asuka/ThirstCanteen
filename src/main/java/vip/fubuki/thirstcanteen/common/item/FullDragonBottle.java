package vip.fubuki.thirstcanteen.common.item;

import net.minecraft.world.item.Rarity;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

public class FullDragonBottle extends Canteen{
    public FullDragonBottle() {
        super(new Properties()
                .rarity(Rarity.EPIC), 12);
        this.usableTime = 12;
        this.container = ThirstCanteenItem.DRAGON_BOTTLE.get().getDefaultInstance();
        this.defaultPurity=2;
    }
}
