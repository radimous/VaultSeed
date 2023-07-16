package com.radimous.vaultseed.mixins;

import com.radimous.vaultseed.CrystalSeed;
import iskallia.vault.core.data.adapter.Adapters;
import iskallia.vault.item.crystal.CrystalData;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(value = CrystalData.class, remap = false)
public class CrystalDataMixin implements CrystalSeed {
    private Long seed = null;
    public Long getSeed() {
        return seed;
    }

    public void setSeed(Long seed) {
        this.seed = seed;
    }
    @Inject(method = "writeNbt", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void writeSeedToNbt(CallbackInfoReturnable<Optional<CompoundTag>> cir, CompoundTag nbt){
        Adapters.LONG.writeNbt(seed).ifPresent(seed -> nbt.put("Seed", seed));
    }
    @Inject(method = "readNbt(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void readSeedFromNbt(CompoundTag nbt, CallbackInfo ci){
        this.seed = Adapters.LONG.readNbt(nbt.get("Seed")).orElse(null);
    }

}
