package com.radimous.vaultseed.mixins;

import com.radimous.vaultseed.CrystalSeed;
import iskallia.vault.core.Version;
import iskallia.vault.core.vault.VaultFactory;
import iskallia.vault.item.crystal.CrystalData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Random;

@Mixin(value = VaultFactory.class, remap = false)
public class VaultFactoryMixin {
    @ModifyVariable(method = "create", at = @At(value = "STORE"), ordinal = 0)
    private static long setVaultSeed(long value, Version version, CrystalData crystal){
        Long seed = ((CrystalSeed)crystal).getSeed();
        return seed == null ? new Random().nextLong() : seed;
    }

}
