package com.robertx22.temporary_spawners.temp_spawners;

import net.minecraft.nbt.CompoundNBT;

public interface ICommonCap {
    CompoundNBT saveToNBT();

    void loadFromNBT(CompoundNBT var1);
}
