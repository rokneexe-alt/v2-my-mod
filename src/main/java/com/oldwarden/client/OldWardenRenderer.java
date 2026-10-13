package com.oldwarden.client;

import com.oldwarden.entity.OldWardenEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class OldWardenRenderer extends GeoEntityRenderer<OldWardenEntity> {
    public OldWardenRenderer(EntityRendererProvider.Context context) {
        super(context, new OldWardenModel());
        this.shadowRadius = 1.2f;
    }

    // The death animation already lays the body down, so disable vanilla's 90 degree death flip.
    protected float getDeathMaxRotation(OldWardenEntity animatable) { return 0f; }
}
