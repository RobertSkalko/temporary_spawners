package com.robertx22.temporary_spawners.main;

import com.robertx22.library_of_exile.main.ApiForgeEvents;
import com.robertx22.temporary_spawners.temp_spawners.TempSpawnerComponent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;

import java.util.function.Consumer;

public class ComponentInit {


    public static void reg() {

        ApiForgeEvents.registerForgeEvent(RegisterCapabilitiesEvent.class, x -> {
            x.register(TempSpawnerComponent.class);
        });

        MinecraftForge.EVENT_BUS.addGenericListener(BlockEntity.class, (Consumer<AttachCapabilitiesEvent<BlockEntity>>) x -> {
            if (x.getObject() instanceof SpawnerBlockEntity s) {
                x.addCapability(TempSpawnerComponent.RESOURCE, new TempSpawnerComponent(s));
            }
        });


    }

}
