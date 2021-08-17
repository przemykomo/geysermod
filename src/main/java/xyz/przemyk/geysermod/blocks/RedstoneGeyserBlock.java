package xyz.przemyk.geysermod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import net.minecraft.block.AbstractBlock.Properties;

public class RedstoneGeyserBlock extends Block implements IGeyser {
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    public RedstoneGeyserBlock() {
        super(Properties.copy(Blocks.STONE));
        registerDefaultState(stateDefinition.any().setValue(TRIGGERED, false));
    }

    protected static final AxisAlignedBB hurtEntitiesAABB = new AxisAlignedBB(0, 0, 0, 1, 3, 1);

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TRIGGERED);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        boolean powered = worldIn.hasNeighborSignal(pos);
        boolean triggered = state.getValue(TRIGGERED);

        if (powered && !triggered) {
            shoot((ServerWorld) worldIn, pos, hurtEntitiesAABB);
            worldIn.setBlock(pos, state.setValue(TRIGGERED, true), 4);
        } else if (!powered && triggered) {
            worldIn.setBlock(pos, state.setValue(TRIGGERED, false), 4);
        }
    }

}
