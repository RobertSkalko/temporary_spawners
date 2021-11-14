package com.robertx22.temporary_spawners.temp_spawners;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public interface BaseStorage<TYPE extends ICommonCap> extends Capability.IStorage<TYPE> {
    default INBT writeNBT(Capability<TYPE> capability, TYPE instance, Direction side) {
        return instance.saveToNBT();
    }

    default void readNBT(Capability<TYPE> capability, TYPE instance, Direction side, INBT nbt) {
        instance.loadFromNBT((CompoundNBT) nbt);
    }
}
