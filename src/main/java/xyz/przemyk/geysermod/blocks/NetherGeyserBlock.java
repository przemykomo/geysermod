package xyz.przemyk.geysermod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.Random;

public class NetherGeyserBlock extends Block implements INetherGeyser {

    public NetherGeyserBlock() {
        super(Properties.copy(Blocks.NETHERRACK).randomTicks());
    }

    protected AABB hurtEntitiesAABB = new AABB(0, 0, 0, 1, 3, 1);

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        shoot(worldIn, pos, hurtEntitiesAABB);
    }
}
