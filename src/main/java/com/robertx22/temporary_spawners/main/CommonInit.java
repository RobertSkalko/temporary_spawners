package com.robertx22.temporary_spawners.main;

import com.robertx22.temporary_spawners.configs.TemporarySpawnersConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("temporary_spawners")
public class CommonInit {

    public CommonInit() {

        ModLoadingContext.get()
            .registerConfig(ModConfig.Type.SERVER, TemporarySpawnersConfig.SPEC);
        final IEventBus bus = FMLJavaModLoadingContext.get()
            .getModEventBus();

        bus.addListener(this::commonSetupEvent);

        System.out.println("Temporary Spawners loaded.");
    }

    public void commonSetupEvent(FMLCommonSetupEvent event) {
        ComponentInit.reg();
    }
}
