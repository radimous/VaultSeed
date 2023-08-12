package com.radimous.vaultseed.mixins;

import com.radimous.vaultseed.Config;
import iskallia.vault.client.gui.framework.ScreenTextures;
import iskallia.vault.client.gui.framework.element.ButtonElement;
import iskallia.vault.client.gui.framework.render.Tooltips;
import iskallia.vault.client.gui.framework.render.spi.IElementRenderer;
import iskallia.vault.client.gui.framework.render.spi.ITooltipRendererFactory;
import iskallia.vault.client.gui.framework.screen.AbstractElementScreen;
import iskallia.vault.client.gui.framework.spatial.Spatials;
import iskallia.vault.client.gui.framework.spatial.spi.ISpatial;
import iskallia.vault.client.gui.screen.summary.VaultEndScreen;
import iskallia.vault.core.vault.Vault;
import iskallia.vault.core.vault.stat.VaultSnapshot;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.UUID;

@Mixin(value = VaultEndScreen.class, remap = false)
public abstract class VaultEndScreenMixin extends AbstractElementScreen {
    private VaultEndScreenMixin(Component title, IElementRenderer elementRenderer, ITooltipRendererFactory<AbstractElementScreen> tooltipRendererFactory) {
        super(title, elementRenderer, tooltipRendererFactory);
    }

    @Shadow
    public abstract ISpatial getTabContentSpatial();

    @Inject(method = "<init>(Liskallia/vault/core/vault/stat/VaultSnapshot;Lnet/minecraft/network/chat/Component;Ljava/util/UUID;ZZ)V", at = @At("TAIL"))
    private void ok(VaultSnapshot snapshot, Component title, UUID asPlayer, boolean isHistory, boolean fromLink, CallbackInfo ci) {
        if (!Config.SHOW_COPY_SEED_BUTTON.get()) return;
        String seed = Long.toString((snapshot.getEnd().get(Vault.SEED) & 281474976710655L));
        this.addElement(
            new ButtonElement<>(Spatials.zero(), ScreenTextures.BUTTON_SKILL_ALTAR_COPY_TEXTURES, () -> Minecraft.getInstance().keyboardHandler.setClipboard(seed))
                .layout(
                    (screen, gui, parent, world) -> world.width(18)
                        .height(18)
                        .translateX(gui.right() - 76)
                        .translateY(this.getTabContentSpatial().bottom() + gui.height() - 36))
                .tooltip(Tooltips.multi(() -> List.of(new TextComponent("Copy Seed"), new TextComponent(seed).withStyle(ChatFormatting.GOLD))))
        );
    }
}
