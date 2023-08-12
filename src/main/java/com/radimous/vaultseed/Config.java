package com.radimous.vaultseed;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static ForgeConfigSpec.BooleanValue SHOW_COPY_SEED_BUTTON;
    public static ForgeConfigSpec SPEC;

    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        SHOW_COPY_SEED_BUTTON = BUILDER
            .comment("Show copy seed button on the vault end screen")
            .define("showCopySeedButton", true);
        SPEC = BUILDER.build();
    }
}
