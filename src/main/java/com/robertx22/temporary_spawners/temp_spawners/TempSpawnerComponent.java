package com.robertx22.temporary_spawners.temp_spawners;

import com.robertx22.temporary_spawners.configs.TempSpawnersConfig;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.block.Blocks;
import net.minecraft.block.TntBlock;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class TempSpawnerComponent implements Component, AutoSyncedComponent {

    public int ticksNearPlayer = 0;

    public int cooldownTicks = 0;

    public MobSpawnerBlockEntity spawner;

    public void tickNearPlayer() {

        if (cooldownTicks < 1) {

            ticksNearPlayer++;

            if (ticksNearPlayer > TempSpawnersConfig.get().TEMP_SPAWNERS.SPAWNER_LIFETIME_MINUTES * 20 * 60) {

                if (TempSpawnersConfig.get().TEMP_SPAWNERS.ENABLE_SPAWNER_DESTRUCTION) {
                    spawner.getWorld()
                        .setBlockState(spawner.getPos(), Blocks.AIR.getDefaultState());
                    BlockPos p = spawner.getPos();

                    if (TempSpawnersConfig.get().TEMP_SPAWNERS.NEGATIVE_EFFECTS_AT_EXPIRATION) {
                        TntBlock.primeTnt(spawner.getWorld(), p);
                    }
                } else {
                    cooldownTicks = TempSpawnersConfig.get().TEMP_SPAWNERS.SPAWNER_COOLDOWN_MINUTES * 20 * 60;
                }
            }
        } else {
            cooldownTicks--;
        }
    }

    public void tickWhenNoPlayer() {
        cooldownTicks--;
    }

    public boolean shouldCancelTick() {
        return cooldownTicks > 0;
    }

    public TempSpawnerComponent(MobSpawnerBlockEntity spawner) {
        this.spawner = spawner;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        ticksNearPlayer = tag.getInt("ticks");
        cooldownTicks = tag.getInt("cd");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("ticks", ticksNearPlayer);
        tag.putInt("cd", cooldownTicks);
    }
}
