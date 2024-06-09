package vip.fubuki.thirstcanteen.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ThirstCanteenConfig {
    private static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<Number> THIRST_RESTORE_EACH_SIP;
    public static final ForgeConfigSpec.ConfigValue<Number> QUENCHED_RESTORE_EACH_SIP;

    static {
        BUILDER.push("Settings");
        THIRST_RESTORE_EACH_SIP = BUILDER.define("ThirstRestore",6);
        QUENCHED_RESTORE_EACH_SIP = BUILDER.define("QuenchedRestore",8);
        SPEC = BUILDER.build();
    }

    public static void setup()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC, "ThirstCanteen.toml");
    }
}
