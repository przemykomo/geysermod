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

public class NetherGeyserBlock extends Block implements INetherGeyser {

    public NetherGeyserBlock() {
        super(Properties.from(Blocks.NETHERRACK).tickRandomly());
    }

    protected AxisAlignedBB hurtEntitiesAABB = new AxisAlignedBB(0, 0, 0, 1, 3, 1);

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        shoot(worldIn, pos, hurtEntitiesAABB);
    }
}
