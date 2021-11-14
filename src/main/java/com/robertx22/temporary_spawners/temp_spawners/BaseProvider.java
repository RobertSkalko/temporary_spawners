package com.robertx22.temporary_spawners.temp_spawners;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public abstract class BaseProvider<TYPE, OWNER_CLASS> implements ICapabilitySerializable<CompoundNBT> {
    public OWNER_CLASS owner;
    TYPE impl;
    private final LazyOptional<TYPE> cap;

    public BaseProvider(OWNER_CLASS owner) {
        this.owner = owner;
        this.impl = this.newDefaultImpl(owner);
        this.cap = LazyOptional.of(() -> {
            return this.impl;
        });
    }

    public abstract TYPE newDefaultImpl(OWNER_CLASS var1);

    public abstract Capability<TYPE> dataInstance();

    public CompoundNBT serializeNBT() {
        return (CompoundNBT) this.dataInstance()
            .getStorage()
            .writeNBT(this.dataInstance(), this.impl, (Direction) null);
    }

    public void deserializeNBT(CompoundNBT nbt) {
        this.dataInstance()
            .getStorage()
            .readNBT(this.dataInstance(), this.impl, (Direction) null, nbt);
    }

    @Nonnull
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == this.dataInstance() ? this.cap.cast() : LazyOptional.empty();
    }
}