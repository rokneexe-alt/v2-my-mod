package com.oldwarden.goal;

import com.oldwarden.OldWardenConfig;
import com.oldwarden.entity.OldWardenEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import java.util.EnumSet;

/** Big jump toward the target, then a shockwave slam on landing. */
public class LeapSlamGoal extends Goal {
    private final OldWardenEntity mob;
    private int nextUse = 0, airTicks = 0;

    public LeapSlamGoal(OldWardenEntity mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
    }

    @Override public boolean requiresUpdateEveryTick() { return true; }

    @Override
    public boolean canUse() {
        LivingEntity t = mob.getTarget();
        if (t == null || !t.isAlive() || !mob.onGround() || mob.tickCount < nextUse) return false;
        double d = mob.distanceTo(t);
        return d >= OldWardenConfig.LEAP_MIN && d <= OldWardenConfig.LEAP_MAX && mob.getRandom().nextInt(10) == 0;
    }

    @Override public boolean canContinueToUse() { return airTicks < 100 && !(airTicks > 5 && mob.onGround()); }

    @Override
    public void start() {
        LivingEntity t = mob.getTarget();
        Vec3 d = t.position().subtract(mob.position());
        double dist = Math.max(1.0, Math.sqrt(d.x * d.x + d.z * d.z));
        double h = Mth.clamp(dist / 10.3, 0.4, 2.6); // air friction 0.91 => ~10.3x initial speed
        mob.getNavigation().stop();
        mob.launch(d.x / dist * h, OldWardenConfig.LEAP_VERTICAL, d.z / dist * h);
        mob.triggerAnim("action", "leap");
        mob.playSound(SoundEvents.WARDEN_ROAR, 3.0F, 0.6F);
        airTicks = 0;
    }

    @Override
    public void tick() {
        airTicks++;
        LivingEntity t = mob.getTarget();
        if (t != null) mob.getLookControl().setLookAt(t, 60.0F, 60.0F);
    }

    @Override
    public void stop() {
        if (airTicks > 5 && mob.onGround()) slam();
        nextUse = mob.tickCount + OldWardenConfig.LEAP_COOLDOWN + mob.getRandom().nextInt(80);
        airTicks = 0;
    }

    private void slam() {
        if (!(mob.level() instanceof ServerLevel level)) return;
        mob.triggerAnim("action", "slam");
        mob.playSound(SoundEvents.WARDEN_ATTACK_IMPACT, 4.0F, 0.6F);
        float dmg = (float) mob.getAttributeValue(Attributes.ATTACK_DAMAGE);
        double r = OldWardenConfig.SLAM_RADIUS;
        for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, mob.getBoundingBox().inflate(r, 2.0, r),
                x -> x != mob && x.isAlive() && !(x instanceof OldWardenEntity))) {
            if (e.hurt(mob.damageSources().mobAttack(mob), dmg)) {
                Vec3 away = e.position().subtract(mob.position()).normalize();
                e.push(away.x * 1.5, 0.8, away.z * 1.5);
                e.hurtMarked = true;
            }
        }
        level.sendParticles(ParticleTypes.EXPLOSION, mob.getX(), mob.getY() + 0.2, mob.getZ(), 25, 3.0, 0.2, 3.0, 0.0);
        level.sendParticles(ParticleTypes.POOF, mob.getX(), mob.getY() + 0.2, mob.getZ(), 60, 3.0, 0.3, 3.0, 0.05);
    }
}
