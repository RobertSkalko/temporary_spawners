package com.robertx22.temporary_spawners.main;

import com.robertx22.temporary_spawners.temp_spawners.TempSpawnerComponent;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ComponentInit {

    public static void reg() {

        CapabilityManager.INSTANCE.register(
            TempSpawnerComponent.class,
            new TempSpawnerComponent.Storage(),
            () -> {
                return new TempSpawnerComponent(null);
            });

    }

}
