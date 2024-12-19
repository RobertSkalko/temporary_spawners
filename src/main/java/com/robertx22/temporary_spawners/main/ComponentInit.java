package com.robertx22.temporary_spawners.main;

import com.robertx22.temporary_spawners.temp_spawners.TempSpawnerComponent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.event.IModBusEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Consumer;

public class ComponentInit {

    
    public static <T extends Event> void registerForgeEvent(Class<T> clazz, Consumer<T> event) {
        registerForgeEvent(clazz, event, EventPriority.NORMAL);
    }

    public static <T extends Event> void registerForgeEvent(Class<T> clazz, Consumer<T> event, EventPriority priority) {
        if (!IModBusEvent.class.isAssignableFrom(clazz) && !clazz.isAssignableFrom(IModBusEvent.class)) {
            MinecraftForge.EVENT_BUS.addListener(priority, event);
        } else {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(priority, event);
        }
    }

    public static void reg() {

        registerForgeEvent(RegisterCapabilitiesEvent.class, x -> {
            x.register(TempSpawnerComponent.class);
        });

        MinecraftForge.EVENT_BUS.addGenericListener(BlockEntity.class, (Consumer<AttachCapabilitiesEvent<BlockEntity>>) x -> {
            if (x.getObject() instanceof SpawnerBlockEntity s) {
                x.addCapability(TempSpawnerComponent.RESOURCE, new TempSpawnerComponent(s));
            }
        });


    }

}
