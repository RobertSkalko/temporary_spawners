package com.robertx22.temporary_spawners.temp_spawners;

import com.robertx22.library_of_exile.components.ICap;
import com.robertx22.temporary_spawners.configs.TemporarySpawnersConfig;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber
public class TempSpawnerComponent implements ICap {


    public static final ResourceLocation RESOURCE = new ResourceLocation("temporary_spawners", "spawner_data");
    public static Capability<TempSpawnerComponent> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {
    });

    transient final LazyOptional<TempSpawnerComponent> supp = LazyOptional.of(() -> this);


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == INSTANCE) {
            return supp.cast();
        }
        return LazyOptional.empty();

    }

    public static TempSpawnerComponent get(Level provider) {
        return provider.getCapability(INSTANCE)
                .orElse(null);
    }


    public SpawnerBlockEntity spawner;

    public void tickNearPlayer() {

        if (cooldownTicks < 1) {

            ticksNearPlayer++;

            if (ticksNearPlayer > TemporarySpawnersConfig.get().SPAWNER_LIFETIME_MINUTES.get() * 20 * 60) {
                ticksNearPlayer = 0;

                if (TemporarySpawnersConfig.get().ENABLE_SPAWNER_DESTRUCTION.get()) {
                    spawner.getLevel()
                            .destroyBlock(spawner.getBlockPos(), false);
                } else {
                    cooldownTicks = TemporarySpawnersConfig.get().SPAWNER_COOLDOWN_MINUTES.get() * 20 * 60;
                }
            }
        } else {
            cooldownTicks--;

            ServerLevel sw = (ServerLevel) spawner.getLevel();

            if (cooldownTicks % 10 == 0) {
                sw.sendParticles(ParticleTypes.HEART, spawner.getBlockPos()
                        .getX() + 0.5F, spawner.getBlockPos()
                        .getY() + 1.5F, spawner.getBlockPos()
                        .getZ() + 0.5F, 1, 0.0D, 0.0D, 0.0D, 0F);
            }

        }
    }

    public void tickWhenNoPlayer() {
        cooldownTicks--;
    }

    public boolean shouldCancelTick() {
        return cooldownTicks > 0;
    }

    public TempSpawnerComponent(SpawnerBlockEntity spawner) {
        this.spawner = spawner;
    }

    int ticksNearPlayer = 0;
    int cooldownTicks = 0;

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("ticks", ticksNearPlayer);
        tag.putInt("cd", cooldownTicks);
        return tag;
    }


    @Override
    public void deserializeNBT(CompoundTag nbt) {
        ticksNearPlayer = nbt.getInt("ticks");
        cooldownTicks = nbt.getInt("cd");
    }

    @Override
    public String getCapIdForSyncing() {
        return "spawners";
    }


}
