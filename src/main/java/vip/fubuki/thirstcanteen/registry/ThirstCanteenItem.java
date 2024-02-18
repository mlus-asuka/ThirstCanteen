package vip.fubuki.thirstcanteen.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vip.fubuki.thirstcanteen.common.item.*;

public class ThirstCanteenItem {
    public static final DeferredRegister<Item> ITEMS;
    public static final RegistryObject<Item> MILITARY_BOTTLE;
    public static final RegistryObject<Item> MILITARY_BOTTLE_FULL;
    public static final RegistryObject<Item> DRAGON_BOTTLE;
    public static final RegistryObject<Item> DRAGON_BOTTLE_FULL;
    public static final RegistryObject<Item> LEATHER_CANTEEN;
    public static final RegistryObject<Item> LEATHER_CANTEEN_FULL;


    public ThirstCanteenItem() {}

    static {
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "thirstcanteen");
        MILITARY_BOTTLE = ITEMS.register("military_bottle", MilitaryBottle::new);
        MILITARY_BOTTLE_FULL = ITEMS.register("military_bottle_full", FullMilitaryBottle::new);
        DRAGON_BOTTLE = ITEMS.register("dragon_bottle", DragonBottle::new);
        DRAGON_BOTTLE_FULL = ITEMS.register("dragon_bottle_full", FullDragonBottle::new);
        LEATHER_CANTEEN = ITEMS.register("leather_canteen", LeatherCanteen::new);
        LEATHER_CANTEEN_FULL = ITEMS.register("leather_canteen_full",FullLeatherCanteen::new);
    }

}
