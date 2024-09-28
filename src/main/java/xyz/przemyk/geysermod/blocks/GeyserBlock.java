package xyz.przemyk.geysermod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;
import xyz.przemyk.geysermod.GeyserBlockEntity;

public class GeyserBlock extends Block implements EntityBlock {

    public static final IntegerProperty BURST_HEIGHT = IntegerProperty.create("burst_height", 1, 7);
    public static final IntegerProperty BURST_DURATION = IntegerProperty.create("burst_duration", 1, 7);

    public GeyserBlock() {
        super(Properties.ofFullCopy(Blocks.STONE));
        registerDefaultState(stateDefinition.any().setValue(BURST_HEIGHT, 3).setValue(BURST_DURATION, 3));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BURST_HEIGHT, BURST_DURATION);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new GeyserBlockEntity(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return (level, blockPos, blockState, blockEntity) -> ((GeyserBlockEntity) blockEntity).tick(level, blockPos, blockState);
    }
}
