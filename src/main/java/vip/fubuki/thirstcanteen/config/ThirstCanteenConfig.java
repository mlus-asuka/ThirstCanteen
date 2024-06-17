package vip.fubuki.thirstcanteen.config;


import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ThirstCanteenConfig {
    private static final ModConfigSpec SPEC;
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<Number> THIRST_RESTORE_EACH_SIP;
    public static final ModConfigSpec.ConfigValue<Number> QUENCHED_RESTORE_EACH_SIP;

    static {
        BUILDER.push("Settings");
        THIRST_RESTORE_EACH_SIP = BUILDER.define("ThirstRestoreEachSip",6);
        QUENCHED_RESTORE_EACH_SIP = BUILDER.define("QuenchedRestoreEachSip",8);
        SPEC = BUILDER.build();
    }

    public static void setup(ModContainer modContainer)
    {
        modContainer.registerConfig(ModConfig.Type.COMMON, SPEC, "ThirstCanteen.toml");
    }
}
