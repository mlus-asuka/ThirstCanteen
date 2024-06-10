package vip.fubuki.thirstcanteen.common.item;

import net.minecraft.world.item.Rarity;
import vip.fubuki.thirstcanteen.config.ThirstCanteenConfig;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

public class FullDragonBottle extends Canteen{
    public FullDragonBottle() {
        super(new Properties()
                .rarity(Rarity.EPIC), ThirstCanteenConfig.DRAGON_BOTTLE_CONTAIN.get().intValue());
        this.usableTime =ThirstCanteenConfig.DRAGON_BOTTLE_CONTAIN.get().intValue();
        this.container = ThirstCanteenItem.DRAGON_BOTTLE.get().getDefaultInstance();
        this.defaultPurity=2;
    }
}
