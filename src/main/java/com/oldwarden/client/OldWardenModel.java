package com.oldwarden.client;

import com.oldwarden.OldWardenMod;
import com.oldwarden.entity.OldWardenEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class OldWardenModel extends GeoModel<OldWardenEntity> {
    @Override public ResourceLocation getModelResource(OldWardenEntity a) { return OldWardenMod.id("geo/old_warden.geo.json"); }
    @Override public ResourceLocation getTextureResource(OldWardenEntity a) { return OldWardenMod.id("textures/entity/old_warden.png"); }
    @Override public ResourceLocation getAnimationResource(OldWardenEntity a) { return OldWardenMod.id("animations/old_warden.animation.json"); }
}
