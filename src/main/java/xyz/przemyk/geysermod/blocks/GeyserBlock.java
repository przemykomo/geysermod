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

public class GeyserBlock extends Block implements IGeyser {

    public GeyserBlock() {
        super(Properties.from(Blocks.STONE).tickRandomly());
    }

    protected AxisAlignedBB hurtEntitiesAABB = new AxisAlignedBB(0, 0, 0, 1, 3, 1);

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        shoot(worldIn, pos, hurtEntitiesAABB);
    }
}
