package xyz.przemyk.geysermod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class GeyserBlock extends Block {

    public GeyserBlock() {
        super(Properties.from(Blocks.STONE).tickRandomly());
    }

    AxisAlignedBB hurtEntitiesAABB = new AxisAlignedBB(0, 0, 0, 1, 3, 1);

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        BlockPos blockpos = pos.up();
        worldIn.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);
        worldIn.spawnParticle(ParticleTypes.SPLASH, (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.25D, (double)blockpos.getZ() + 0.5D, 200, 0.1D, 3.0D, 0.1D, 0.0D);

        for (LivingEntity entity : worldIn.getEntitiesWithinAABB(LivingEntity.class, hurtEntitiesAABB.offset(blockpos))) {
            entity.attackEntityFrom(DamageSource.HOT_FLOOR, 5.0f);
        }
    }
}