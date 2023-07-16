package com.radimous.vaultseed;

import com.google.gson.JsonObject;
import iskallia.vault.item.crystal.data.serializable.ISerializable;
import net.minecraft.nbt.CompoundTag;

public interface CrystalSeed extends ISerializable<CompoundTag, JsonObject> {
    void setSeed(Long seed);

    Long getSeed();
}

