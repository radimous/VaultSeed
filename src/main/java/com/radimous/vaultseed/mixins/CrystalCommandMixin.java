package com.radimous.vaultseed.mixins;

import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.radimous.vaultseed.CrystalSeed;
import iskallia.vault.command.CrystalCommand;
import iskallia.vault.item.crystal.CrystalData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CrystalCommand.class, remap = false)
public abstract class CrystalCommandMixin {
    @Shadow
    protected abstract ItemStack getCrystal(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException;

    @Inject(method = "build", at = @At("HEAD"))
    private void seedCommand(LiteralArgumentBuilder<CommandSourceStack> builder, CallbackInfo ci) {
        builder.then(
            Commands.literal("setSeed")
                .then(Commands.argument("seed", LongArgumentType.longArg(0,281474976710655L)).executes(this::setSeed))
                .executes(this::removeSeed)
        );
    }

    private int setSeed(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ItemStack crystal = this.getCrystal(context);
        CrystalData data = CrystalData.read(crystal);
        long seed = LongArgumentType.getLong(context, "seed");
        ((CrystalSeed) data).setSeed(seed);
        data.write(crystal);
        context.getSource().sendSuccess(new TextComponent("Seed successfully changed to " + seed),false);
        return 0;
    }

    private int removeSeed(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ItemStack crystal = this.getCrystal(context);
        CrystalData data = CrystalData.read(crystal);
        ((CrystalSeed) data).setSeed(null);
        data.write(crystal);
        context.getSource().sendSuccess(new TextComponent("Seed removed successfully, vault will generate with random seed"),false);
        return 0;
    }
}
