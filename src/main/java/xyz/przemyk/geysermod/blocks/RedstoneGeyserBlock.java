package xyz.przemyk.geysermod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import xyz.przemyk.geysermod.GeyserMod;

public class RedstoneGeyserBlock extends Block {
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    public RedstoneGeyserBlock() {
        super(Properties.ofFullCopy(Blocks.STONE));
        registerDefaultState(stateDefinition.any().setValue(TRIGGERED, false));
    }

    protected static final AABB hurtEntitiesAABB = new AABB(0, 0, 0, 1, 3, 1);

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TRIGGERED);
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        boolean powered = worldIn.hasNeighborSignal(pos);
        boolean triggered = state.getValue(TRIGGERED);

        if (powered && !triggered) {
            GeyserMod.shoot((ServerLevel) worldIn, pos, hurtEntitiesAABB);
            worldIn.setBlock(pos, state.setValue(TRIGGERED, true), 4);
        } else if (!powered && triggered) {
            worldIn.setBlock(pos, state.setValue(TRIGGERED, false), 4);
        }
    }

}
