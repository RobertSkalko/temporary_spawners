package com.robertx22.temporary_spawners.mixins;

import com.robertx22.temporary_spawners.configs.TempSpawnersConfig;
import com.robertx22.temporary_spawners.main.ComponentInit;
import com.robertx22.temporary_spawners.temp_spawners.TempSpawnerComponent;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobSpawnerLogic.class)
public abstract class MobSpawnerLogicMixin {

    @Shadow
    private boolean isPlayerInRange(World world, BlockPos pos) {
        return false;
    }

    @Inject(method = "serverTick(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;)V", at = @At(value = "HEAD"), cancellable = true)
    public void hookOnServerTick(ServerWorld world, BlockPos pos, CallbackInfo ci) {
        try {
            if (TempSpawnersConfig.get().TEMP_SPAWNERS.ENABLE) {

                //MobSpawnerLogic logic = (MobSpawnerLogic) (Object) this;

                BlockEntity en = world
                    .getBlockEntity(pos);
                if (en instanceof MobSpawnerBlockEntity) {
                    MobSpawnerBlockEntity be = (MobSpawnerBlockEntity) en;
                    if (!world.isClient) {

                        TempSpawnerComponent data = ComponentInit.SPAWNER.get(be);

                        if (isPlayerInRange(world, pos)) {
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



