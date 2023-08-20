package com.robertx22.temporary_spawners.mixins;

import com.robertx22.temporary_spawners.main.MixinMethod;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseSpawner.class)
public abstract class MobSpawnerLogicMixin {

    @Shadow
    abstract boolean isNearPlayer(Level pLevel, BlockPos pPos);

    @Inject(method = "serverTick", at = @At(value = "HEAD"), cancellable = true)
    public void hookOnServerTick(ServerLevel level, BlockPos pos, CallbackInfo ci) {
        BaseSpawner spawner = (BaseSpawner) (Object) this;

        try {
            MixinMethod.hookOnServerTick(level, pos, spawner, ci, isNearPlayer(level, pos));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


