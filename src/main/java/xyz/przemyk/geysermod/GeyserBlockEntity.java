package xyz.przemyk.geysermod;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import xyz.przemyk.geysermod.blocks.GeyserBlock;
import xyz.przemyk.geysermod.setup.GeyserBlockEntities;

public class GeyserBlockEntity extends BlockEntity {

    public GeyserBlockEntity(BlockPos pPos, BlockState pState) {
        super(GeyserBlockEntities.GEYSER.get(), pPos, pState);
        DURATION = pState.getValue(GeyserBlock.BURST_DURATION) * 20;
        HEIGHT = pState.getValue(GeyserBlock.BURST_HEIGHT);
    }

    private final int DURATION;
    private final int HEIGHT;

    private int ticks = 0;

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (!level.isClientSide && ticks++ % 200 < DURATION) {
            BlockPos blockpos = blockPos.above();
            level.playSound(null, blockPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.1F, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);

            double halfDeviation = HEIGHT / 2.0 / Math.sqrt(3);

            ((ServerLevel) level).sendParticles(ParticleTypes.SPLASH,
                (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.25D + halfDeviation, (double)blockpos.getZ() + 0.5D, HEIGHT + 2,
                0.1D, halfDeviation, 0.1D, 0.0D);

            AABB hurtEntitiesAABB = new AABB(0, 0, 0, 1, HEIGHT, 1);
            for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, hurtEntitiesAABB.move(blockpos))) {
                entity.hurt(level.damageSources().hotFloor(), 1.0f);
            }
        }
    }
}
