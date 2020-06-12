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

public class RedstoneNetherGeyserBlock extends Block implements INetherGeyser {
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    public RedstoneNetherGeyserBlock() {
        super(Properties.from(Blocks.NETHERRACK));
        setDefaultState(stateContainer.getBaseState().with(TRIGGERED, false));
    }

    protected static final AxisAlignedBB hurtEntitiesAABB = new AxisAlignedBB(0, 0, 0, 1, 3, 1);

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TRIGGERED);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        boolean powered = worldIn.isBlockPowered(pos);
        boolean triggered = state.get(TRIGGERED);

        if (powered && !triggered) {
            shoot((ServerWorld) worldIn, pos, hurtEntitiesAABB);
            worldIn.setBlockState(pos, state.with(TRIGGERED, true), 4);
        } else if (!powered && triggered) {
            worldIn.setBlockState(pos, state.with(TRIGGERED, false), 4);
        }
    }
}
