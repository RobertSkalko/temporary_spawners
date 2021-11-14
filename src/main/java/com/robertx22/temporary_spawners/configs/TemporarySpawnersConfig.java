package com.robertx22.temporary_spawners.configs;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class TemporarySpawnersConfig {

    public static final ForgeConfigSpec spec;
    public static final TemporarySpawnersConfig COMMON;

    static {
        final Pair<TemporarySpawnersConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(TemporarySpawnersConfig::new);
        spec = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static TemporarySpawnersConfig get() {
        return COMMON;
    }

    TemporarySpawnersConfig(ForgeConfigSpec.Builder b) {
        b.comment("Temporary spawner Configs")
            .push("general");

        ENABLE = b.define("enable", true);
        ENABLE_SPAWNER_DESTRUCTION = b.define("ENABLE_SPAWNER_DESTRUCTION", false);

        SPAWNER_LIFETIME_MINUTES = b.defineInRange("SPAWNER_LIFETIME_MINUTES", 5, 0, 100000);
        SPAWNER_COOLDOWN_MINUTES = b.defineInRange("SPAWNER_COOLDOWN_MINUTES", 60, 0, 100000);

        b.pop();
    }

    public ForgeConfigSpec.BooleanValue ENABLE;
    public ForgeConfigSpec.BooleanValue ENABLE_SPAWNER_DESTRUCTION;
    public ForgeConfigSpec.IntValue SPAWNER_LIFETIME_MINUTES;
    public ForgeConfigSpec.IntValue SPAWNER_COOLDOWN_MINUTES;

}
