package xyz.przemyk.geysermod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;
import xyz.przemyk.geysermod.GeyserBlockEntity;
import xyz.przemyk.geysermod.GeyserMod;

public class GeyserBlock extends Block implements EntityBlock {

    public static final IntegerProperty BURST_HEIGHT = IntegerProperty.create("burst_height", 1, 7);
    public static final IntegerProperty BURST_DURATION = IntegerProperty.create("burst_duration", 1, 7);
    private final int frequency;
    private final int durationMin;
    private final int durationMax;
    private final int heightMin;
    private final int heightMax;
    public final ResourceKey<LootTable> burstLootTable;
    public final boolean lava;

    public GeyserBlock(int frequency, int durationMin, int durationMax, int heightMin, int heightMax, String burstLootTable, boolean lava) {
        super(Properties.ofFullCopy(Blocks.STONE));
        this.frequency = frequency;
        this.durationMin = durationMin;
        this.durationMax = durationMax;
        this.heightMin = heightMin;
        this.heightMax = heightMax;
        this.burstLootTable = ResourceKey.create(Registries.LOOT_TABLE,
            ResourceLocation.fromNamespaceAndPath(GeyserMod.MODID, burstLootTable));
        this.lava = lava;
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

    public int getBurstDuration(Level level) {
        return level.random.nextInt(durationMin, durationMax);
    }

    public int getBurstHeight(Level level) {
        return level.random.nextInt(heightMin, heightMax);
    }

    public int getFrequency() {
        return frequency;
    }
}
