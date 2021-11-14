package com.robertx22.temporary_spawners.temp_spawners;

import com.robertx22.temporary_spawners.configs.TemporarySpawnersConfig;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TempSpawnerComponent implements ICommonCap {

    @CapabilityInject(TempSpawnerComponent.class)
    public static final Capability<TempSpawnerComponent> Data = null;

    @Mod.EventBusSubscriber
    public static class EventHandler {
        @SubscribeEvent
        public static void onEntityConstruct(AttachCapabilitiesEvent<TileEntity> event) {
            if (event.getObject() instanceof MobSpawnerTileEntity) {
                event.addCapability(RESOURCE, new Provider((MobSpawnerTileEntity) event.getObject()));
            }
        }
    }

    public static final ResourceLocation RESOURCE = new ResourceLocation("temporary_spawners", "chunk_data");

    public static TempSpawnerComponent get(MobSpawnerTileEntity provider) {
        return provider.getCapability(Data)
            .orElse(new TempSpawnerComponent(provider));
    }

    public static class Provider extends BaseProvider<TempSpawnerComponent, MobSpawnerTileEntity> {
        public Provider(MobSpawnerTileEntity owner) {
            super(owner);
        }

        @Override
        public TempSpawnerComponent newDefaultImpl(MobSpawnerTileEntity owner) {
            return new TempSpawnerComponent(owner);
        }

        @Override
        public Capability<TempSpawnerComponent> dataInstance() {
            return Data;
        }
    }

    public static class Storage implements BaseStorage<TempSpawnerComponent> {

    }

    public MobSpawnerTileEntity spawner;

    public void tickNearPlayer() {

        if (cooldownTicks < 1) {

            ticksNearPlayer++;

            if (ticksNearPlayer > TemporarySpawnersConfig.get().SPAWNER_LIFETIME_MINUTES.get() * 20 * 60) {

                if (TemporarySpawnersConfig.get().ENABLE_SPAWNER_DESTRUCTION.get()) {

                    spawner.getLevel()
                        .destroyBlock(spawner.getBlockPos(), false);
                    //    .setBlock(spawner.getBlockPos(), Blocks.AIR.defaultBlockState(), 0);
                    //    BlockPos p = spawner.getPos();

                } else {
                    cooldownTicks = TemporarySpawnersConfig.get().SPAWNER_COOLDOWN_MINUTES.get() * 20 * 60;
                }
            }
        } else {
            cooldownTicks--;

            ServerWorld sw = (ServerWorld) spawner.getLevel();

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

    public TempSpawnerComponent(MobSpawnerTileEntity spawner) {
        this.spawner = spawner;
    }

    int ticksNearPlayer = 0;
    int cooldownTicks = 0;

    @Override
    public CompoundNBT saveToNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("ticks", ticksNearPlayer);
        tag.putInt("cd", cooldownTicks);
        return tag;
    }

    @Override
    public void loadFromNBT(CompoundNBT tag) {
        ticksNearPlayer = tag.getInt("ticks");
        cooldownTicks = tag.getInt("cd");
    }

}
