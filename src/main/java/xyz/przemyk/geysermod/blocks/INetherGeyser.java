package xyz.przemyk.geysermod.blocks;

import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public interface INetherGeyser {

    default void shoot(ServerWorld worldIn, BlockPos pos, AxisAlignedBB hurtEntitiesAABB) {
        BlockPos blockpos = pos.up();
        worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);
        worldIn.spawnParticle(ParticleTypes.LAVA, (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.25D, (double)blockpos.getZ() + 0.5D, 200, 0.1D, 3.0D, 0.1D, 0.0D);

        for (LivingEntity entity : worldIn.getEntitiesWithinAABB(LivingEntity.class, hurtEntitiesAABB.offset(blockpos))) {
            entity.attackEntityFrom(DamageSource.LAVA, 10.0f);
        }
    }
}
