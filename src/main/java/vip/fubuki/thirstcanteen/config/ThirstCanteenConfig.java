package vip.fubuki.thirstcanteen.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ThirstCanteenConfig {
    private static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<Number> THIRST_RESTORE_EACH_SIP;
    public static final ForgeConfigSpec.ConfigValue<Number> QUENCHED_RESTORE_EACH_SIP;
    public static final ForgeConfigSpec.ConfigValue<Number> LEATHER_CANTEEN_CONTAIN;
    public static final ForgeConfigSpec.ConfigValue<Number> MILITARY_BOTTLE_CONTAIN;
    public static final ForgeConfigSpec.ConfigValue<Number> DRAGON_BOTTLE_CONTAIN;

    static {
        BUILDER.push("Settings");
        THIRST_RESTORE_EACH_SIP = BUILDER.define("ThirstRestoreEachSip",6);
        QUENCHED_RESTORE_EACH_SIP = BUILDER.define("QuenchedRestoreEachSip",8);
        LEATHER_CANTEEN_CONTAIN = BUILDER.define("LeatherCanteenContain",4);
        MILITARY_BOTTLE_CONTAIN  = BUILDER.define("MilitaryBottleContain",6);
        DRAGON_BOTTLE_CONTAIN = BUILDER.define("DragonBottleContain",12);
        SPEC = BUILDER.build();
    }

    public static void setup()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC, "ThirstCanteen.toml");
    }
}
