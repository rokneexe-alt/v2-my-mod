package com.oldwarden.client;

import com.oldwarden.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class OldWardenClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.OLD_WARDEN, OldWardenRenderer::new);
    }
}
