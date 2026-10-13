package com.oldwarden;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

public final class ModItems {
    public static final Item OLD_WARDEN_SPAWN_EGG = Registry.register(BuiltInRegistries.ITEM,
            OldWardenMod.id("old_warden_spawn_egg"),
            new SpawnEggItem(ModEntities.OLD_WARDEN, 0xBF9FA0, 0x2A7F9E, new Item.Properties()));

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS)
                .register(entries -> entries.accept(OLD_WARDEN_SPAWN_EGG));
    }
}
