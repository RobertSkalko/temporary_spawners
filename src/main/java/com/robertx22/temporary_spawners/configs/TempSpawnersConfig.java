package com.robertx22.temporary_spawners.configs;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

@me.sargunvohra.mcmods.autoconfig1u.annotation.Config(name = "temporary_spawners")
public class TempSpawnersConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public TemporarySpawnersConfig TEMP_SPAWNERS = new TemporarySpawnersConfig();

    public TempSpawnersConfig() {

    }

    public static TempSpawnersConfig get() {
        return AutoConfig.getConfigHolder(TempSpawnersConfig.class)
            .getConfig();
    }

}
