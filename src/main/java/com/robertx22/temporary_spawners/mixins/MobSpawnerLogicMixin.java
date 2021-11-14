package com.robertx22.temporary_spawners.mixins;

import com.robertx22.temporary_spawners.main.MixinMethod;
import net.minecraft.world.spawner.AbstractSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSpawner.class)
public abstract class MobSpawnerLogicMixin {

    @Shadow
    abstract boolean isNearPlayer();

    @Inject(method = "Lnet/minecraft/world/spawner/AbstractSpawner;tick()V", at = @At(value = "HEAD"), cancellable = true)
    public void hookOnServerTick(CallbackInfo ci) {
        AbstractSpawner spawner = (AbstractSpawner) (Object) this;

        try {
            MixinMethod.hookOnServerTick(spawner, ci, isNearPlayer());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


