package xyz.przemyk.geysermod.blocks;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public interface IGeyser {

    default void shoot(ServerLevel worldIn, BlockPos pos, AABB hurtEntitiesAABB) {
        BlockPos blockpos = pos.above();
        worldIn.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (worldIn.random.nextFloat() - worldIn.random.nextFloat()) * 0.8F);
        worldIn.sendParticles(ParticleTypes.SPLASH, (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.25D, (double)blockpos.getZ() + 0.5D, 200, 0.1D, 3.0D, 0.1D, 0.0D);

        for (LivingEntity entity : worldIn.getEntitiesOfClass(LivingEntity.class, hurtEntitiesAABB.move(blockpos))) {
            entity.hurt(DamageSource.HOT_FLOOR, 5.0f);
        }
    }
}
