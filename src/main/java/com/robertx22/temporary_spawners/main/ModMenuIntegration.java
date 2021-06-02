package com.robertx22.temporary_spawners.main;

import com.robertx22.temporary_spawners.configs.TempSpawnersConfig;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (screen) -> {
            return AutoConfig.getConfigScreen(TempSpawnersConfig.class, screen)
                .get();
        };
    }

    @Override
    public String getModId() {
        return "temporary_spawners";
    }
}
