package vip.fubuki.thirstcanteen.config;


import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ThirstCanteenConfig {
    private static final ModConfigSpec SPEC;
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<Number> THIRST_RESTORE_EACH_SIP;
    public static final ModConfigSpec.ConfigValue<Number> QUENCHED_RESTORE_EACH_SIP;
    public static final ModConfigSpec.ConfigValue<Number> LEATHER_CANTEEN_USABLE_TIME;
    public static final ModConfigSpec.ConfigValue<Number> MILITARY_BOTTLE_USABLE_TIME;
    public static final ModConfigSpec.ConfigValue<Number> DRAGON_BOTTLE_USABLE_TIME;
    public static final ModConfigSpec.ConfigValue<Number> DRAGON_BOTTLE_DEFAULT_PURITY;

    static {
        BUILDER.push("Settings");
        THIRST_RESTORE_EACH_SIP = BUILDER.define("ThirstRestoreEachSip",6);
        QUENCHED_RESTORE_EACH_SIP = BUILDER.define("QuenchedRestoreEachSip",8);
        DRAGON_BOTTLE_DEFAULT_PURITY = BUILDER.define("DragonBottleDefaultPurity",2);
        LEATHER_CANTEEN_USABLE_TIME = BUILDER.define("LeatherCanteenUsableTime", 8);
        MILITARY_BOTTLE_USABLE_TIME = BUILDER.define("MilitaryBottleUsableTime", 12);
        DRAGON_BOTTLE_USABLE_TIME = BUILDER.define("DragonBottleUsableTime", 16);
        SPEC = BUILDER.build();
    }

    public static void setup(ModContainer modContainer)
    {
        modContainer.registerConfig(ModConfig.Type.COMMON, SPEC, "ThirstCanteen.toml");
    }
}
