package vip.fubuki.thirstcanteen.registry;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import vip.fubuki.thirstcanteen.ThirstCanteen;
import vip.fubuki.thirstcanteen.common.item.*;

public class ThirstCanteenItem {
    public static final DeferredRegister.Items ITEMS;
    public static final DeferredItem<Item> MILITARY_BOTTLE;
    public static final DeferredItem<Item> MILITARY_BOTTLE_FULL;
    public static final DeferredItem<Item> DRAGON_BOTTLE;
    public static final DeferredItem<Item> DRAGON_BOTTLE_FULL;
    public static final DeferredItem<Item> LEATHER_CANTEEN;
    public static final DeferredItem<Item> LEATHER_CANTEEN_FULL;


    static {
        ITEMS = DeferredRegister.createItems(ThirstCanteen.MODID);
        MILITARY_BOTTLE = ITEMS.register("military_bottle", MilitaryBottle::new);
        MILITARY_BOTTLE_FULL = ITEMS.register("military_bottle_full", FullMilitaryBottle::new);
        DRAGON_BOTTLE = ITEMS.register("dragon_bottle", DragonBottle::new);
        DRAGON_BOTTLE_FULL = ITEMS.register("dragon_bottle_full", FullDragonBottle::new);
        LEATHER_CANTEEN = ITEMS.register("leather_canteen", LeatherCanteen::new);
        LEATHER_CANTEEN_FULL = ITEMS.register("leather_canteen_full",FullLeatherCanteen::new);
    }

}
