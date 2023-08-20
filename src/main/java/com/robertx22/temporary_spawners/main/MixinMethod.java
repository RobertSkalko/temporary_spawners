package com.robertx22.temporary_spawners.main;

import com.robertx22.temporary_spawners.configs.TemporarySpawnersConfig;
import com.robertx22.temporary_spawners.temp_spawners.TempSpawnerComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class MixinMethod {
    public static void hookOnServerTick(ServerLevel level, BlockPos pos, BaseSpawner spawner, CallbackInfo ci, boolean isnearplayer) {
        try {

            if (TemporarySpawnersConfig.get().ENABLE.get()) {

                BlockEntity en = level
                        .getBlockEntity(pos);
                if (en instanceof SpawnerBlockEntity) {
                    SpawnerBlockEntity be = (SpawnerBlockEntity) en;
                    if (!level.isClientSide) {

                        TempSpawnerComponent data = be.getCapability(TempSpawnerComponent.INSTANCE)
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
