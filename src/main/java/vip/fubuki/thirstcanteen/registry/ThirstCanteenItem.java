package vip.fubuki.thirstcanteen.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vip.fubuki.thirstcanteen.common.item.*;
import vip.fubuki.thirstcanteen.config.ThirstCanteenConfig;

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
        MILITARY_BOTTLE = ITEMS.register("military_bottle",() -> new EmptyCanteen(new Item.Properties(), LazyOptional.of(()->ThirstCanteenItem.MILITARY_BOTTLE_FULL.get().getDefaultInstance())));
        MILITARY_BOTTLE_FULL = ITEMS.register("military_bottle_full", ()-> new Canteen(new Item.Properties(),
                LazyOptional.of(() -> ThirstCanteenConfig.MILITARY_BOTTLE_USABLE_TIME.get().intValue()), LazyOptional.of(() -> ThirstCanteenItem.MILITARY_BOTTLE.get().getDefaultInstance())));

        DRAGON_BOTTLE = ITEMS.register("dragon_bottle", ()-> new EmptyCanteen(new Item.Properties().rarity(Rarity.EPIC), LazyOptional.of(()->ThirstCanteenItem.DRAGON_BOTTLE_FULL.get().getDefaultInstance())));
        DRAGON_BOTTLE_FULL = ITEMS.register("dragon_bottle_full",()-> new Canteen(new Item.Properties().rarity(Rarity.EPIC),
                LazyOptional.of(() -> ThirstCanteenConfig.DRAGON_BOTTLE_USABLE_TIME.get().intValue()), LazyOptional.of(()->ThirstCanteenItem.DRAGON_BOTTLE.get().getDefaultInstance()) ,
                LazyOptional.of(() -> ThirstCanteenConfig.DRAGON_BOTTLE_DEFAULT_PURITY.get().intValue())));

        LEATHER_CANTEEN = ITEMS.register("leather_canteen", ()-> new EmptyCanteen(new Item.Properties(), LazyOptional.of(()->ThirstCanteenItem.LEATHER_CANTEEN_FULL.get().getDefaultInstance())));
        LEATHER_CANTEEN_FULL = ITEMS.register("leather_canteen_full",()-> new Canteen(new Item.Properties(),
                LazyOptional.of(() -> ThirstCanteenConfig.LEATHER_CANTEEN_USABLE_TIME.get().intValue()), LazyOptional.of(()->ThirstCanteenItem.LEATHER_CANTEEN.get().getDefaultInstance())));
    }
}
