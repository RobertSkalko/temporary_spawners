package com.robertx22.temporary_spawners.main;

import com.robertx22.temporary_spawners.configs.TempSpawnersConfig;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class CommonInit implements ModInitializer {

    @Override
    public void onInitialize() {
        AutoConfig.register(TempSpawnersConfig.class, JanksonConfigSerializer::new);

        System.out.println("Temporary Spawners loaded.");
    }

}
