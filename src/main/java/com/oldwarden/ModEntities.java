package com.oldwarden;

import com.oldwarden.entity.OldWardenEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;

public final class ModEntities {
    public static final EntityType<OldWardenEntity> OLD_WARDEN = Registry.register(
            BuiltInRegistries.ENTITY_TYPE, OldWardenMod.id("old_warden"),
            EntityType.Builder.of(OldWardenEntity::new, MobCategory.MONSTER)
                    .sized(1.3f, 3.2f).fireImmune().clientTrackingRange(16).build("old_warden"));

    public static void register() {
        FabricDefaultAttributeRegistry.register(OLD_WARDEN, OldWardenEntity.createAttributes());
        SpawnPlacements.register(OLD_WARDEN, SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, OldWardenEntity::checkSpawnRules);
        // Natural spawn in deserts only.
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.DESERT), MobCategory.MONSTER,
                OLD_WARDEN, OldWardenConfig.SPAWN_WEIGHT, 1, 1);
    }
}
