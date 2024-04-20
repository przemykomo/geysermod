package xyz.przemyk.geysermod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

public interface IGeyser {

    default void shoot(ServerLevel level, BlockPos pos, AABB hurtEntitiesAABB) {
        BlockPos blockpos = pos.above();
        level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);
        level.sendParticles(ParticleTypes.SPLASH, (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.25D, (double)blockpos.getZ() + 0.5D, 200, 0.1D, 3.0D, 0.1D, 0.0D);

        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, hurtEntitiesAABB.move(blockpos))) {
            entity.hurt(level.damageSources().hotFloor(), 5.0f);
        }
    }
}
