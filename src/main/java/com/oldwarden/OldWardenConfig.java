package com.oldwarden;

/** All tunable numbers in one place. */
public final class OldWardenConfig {
    private OldWardenConfig() {}
    // Vanilla caps MAX_HEALTH at 1024, so we use 1024 + a damage scale = 2000 effective HP (1000 hearts).
    public static final float REAL_MAX_HEALTH = 2000f;
    public static final double ATTRIBUTE_MAX_HEALTH = 1024.0;
    public static final float DAMAGE_SCALE = (float) (ATTRIBUTE_MAX_HEALTH / REAL_MAX_HEALTH);
    public static final double ATTACK_DAMAGE = 100.0;      // 50 hearts
    public static final float SONIC_DAMAGE = 60.0f;         // 30 hearts, bypasses armor like vanilla
    public static final double FOLLOW_RANGE = 50.0;
    public static final double SPEED = 0.30;   // ~ sprinting player (5.6 b/s). Use 0.23 for walking-player speed.
    public static final int SONIC_COOLDOWN = 100, LEAP_COOLDOWN = 140;
    // Distance bands: < SONIC_MIN he only chases/melees. LEAP_MIN..LEAP_MAX he can leap. >= SONIC_MIN he can sonic boom.
    public static final double SONIC_MIN = 18, SONIC_MAX = 45, LEAP_MIN = 10, LEAP_MAX = 28;
    public static final int CHEST_POUND_CHANCE = 400; // idle taunt: 1 in N ticks when no target
    public static final double LEAP_VERTICAL = 1.2, SLAM_RADIUS = 6.0;
    public static final int SPAWN_WEIGHT = 1, SPAWN_CHANCE = 30; // chance = 1 in SPAWN_CHANCE per attempt
}
