package com.robertx22.temporary_spawners.mixins;

import com.robertx22.temporary_spawners.configs.TempSpawnersConfig;
import com.robertx22.temporary_spawners.main.ComponentInit;
import com.robertx22.temporary_spawners.temp_spawners.TempSpawnerComponent;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.world.MobSpawnerLogic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobSpawnerLogic.class)
public abstract class MobSpawnerLogicMixin {

    @Shadow
    private boolean isPlayerInRange() {
        return false;
    }

    @Inject(method = "update()V", at = @At(value = "HEAD"), cancellable = true)
    public void onLootGen(CallbackInfo ci) {
        try {
            if (TempSpawnersConfig.get().TEMP_SPAWNERS.ENABLE) {

                MobSpawnerLogic logic = (MobSpawnerLogic) (Object) this;

                BlockEntity en = logic.getWorld()
                    .getBlockEntity(logic.getPos());
                if (en instanceof MobSpawnerBlockEntity) {
                    MobSpawnerBlockEntity be = (MobSpawnerBlockEntity) en;
                    if (!be.getWorld().isClient) {

                        TempSpawnerComponent data = ComponentInit.SPAWNER.get(be);

                        if (isPlayerInRange()) {
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



