package com.oldwarden.goal;

import com.oldwarden.OldWardenConfig;
import com.oldwarden.entity.OldWardenEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import java.util.EnumSet;

/** Long-range sonic boom: 34 tick charge, then a beam that ignores armor (like the vanilla Warden). */
public class SonicBoomGoal extends Goal {
    private final OldWardenEntity mob;
    private int nextUse = 0, charge = 0;

    public SonicBoomGoal(OldWardenEntity mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override public boolean requiresUpdateEveryTick() { return true; }

    @Override
    public boolean canUse() {
        LivingEntity t = mob.getTarget();
        if (t == null || !t.isAlive() || mob.tickCount < nextUse || !mob.onGround()) return false;
        double d = mob.distanceTo(t);
        return d >= OldWardenConfig.SONIC_MIN && d <= OldWardenConfig.SONIC_MAX && mob.hasLineOfSight(t);
    }

    @Override public boolean canContinueToUse() { LivingEntity t = mob.getTarget(); return t != null && t.isAlive() && charge < 40; }

    @Override
    public void start() {
        charge = 0;
        mob.getNavigation().stop();
        mob.triggerAnim("action", "sonic_boom");
        mob.playSound(SoundEvents.WARDEN_SONIC_CHARGE, 3.0F, 1.0F);
    }

    @Override
    public void tick() {
        LivingEntity t = mob.getTarget();
        if (t == null) return;
        mob.getLookControl().setLookAt(t, 30.0F, 30.0F);
        if (++charge == 34) fire(t);
    }

    private void fire(LivingEntity t) {
        if (!(mob.level() instanceof ServerLevel level)) return;
        Vec3 from = mob.position().add(0.0, 2.2, 0.0);
        Vec3 toTarget = t.getEyePosition().subtract(from);
        Vec3 dir = toTarget.normalize();
        for (int i = 1; i < Mth.floor(toTarget.length()) + 7; i++) {
            Vec3 p = from.add(dir.scale(i));
            level.sendParticles(ParticleTypes.SONIC_BOOM, p.x, p.y, p.z, 1, 0, 0, 0, 0);
        }
        mob.playSound(SoundEvents.WARDEN_SONIC_BOOM, 3.0F, 1.0F);
        if (t.hurt(mob.damageSources().sonicBoom(mob), OldWardenConfig.SONIC_DAMAGE)) {
            double kbY = 0.5 * (1.0 - t.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
            double kbXZ = 2.5 * (1.0 - t.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
            t.push(dir.x * kbXZ, dir.y * kbY, dir.z * kbXZ);
        }
    }

    @Override public void stop() { nextUse = mob.tickCount + OldWardenConfig.SONIC_COOLDOWN + mob.getRandom().nextInt(60); charge = 0; }
}
