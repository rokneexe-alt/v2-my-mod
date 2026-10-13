package com.oldwarden;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;

public class OldWardenMod implements ModInitializer {
    public static final String MOD_ID = "oldwarden";
    public static ResourceLocation id(String path) { return ResourceLocation.fromNamespaceAndPath(MOD_ID, path); }

    @Override
    public void onInitialize() {
        ModEntities.register();
        ModItems.register();
    }
}
