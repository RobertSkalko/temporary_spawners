package com.robertx22.temporary_spawners.main;

import com.robertx22.temporary_spawners.configs.TemporarySpawnersConfig;
import com.robertx22.temporary_spawners.temp_spawners.TempSpawnerComponent;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.spawner.AbstractSpawner;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class MixinMethod {
    public static void hookOnServerTick(AbstractSpawner spawner, CallbackInfo ci, boolean isnearplayer) {
        try {

            if (TemporarySpawnersConfig.get().ENABLE.get()) {

                TileEntity en = spawner.getLevel()
                    .getBlockEntity(spawner.getPos());
                if (en instanceof MobSpawnerTileEntity) {
                    MobSpawnerTileEntity be = (MobSpawnerTileEntity) en;
                    if (!spawner.getLevel().isClientSide) {

                        TempSpawnerComponent data = be.getCapability(TempSpawnerComponent.Data)
                            .orElse(new TempSpawnerComponent(null));

                        if (isnearplayer) {
                            data.tickNearPlayer();
                        } else {
                            data.tickWhenNoPlayer();
                        }

                        if (data.shouldCancelTick()) {
                            ci.cancel();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
