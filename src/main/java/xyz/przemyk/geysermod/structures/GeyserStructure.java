package xyz.przemyk.geysermod.structures;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import xyz.przemyk.geysermod.setup.GeyserStructures;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class GeyserStructure extends Structure {
    public static final DimensionPadding DEFAULT_DIMENSION_PADDING = DimensionPadding.ZERO;
    public static final LiquidSettings DEFAULT_LIQUID_SETTINGS = LiquidSettings.APPLY_WATERLOGGING;
    public static final MapCodec<GeyserStructure> CODEC = RecordCodecBuilder.<GeyserStructure>mapCodec(
            structureInstance -> structureInstance.group(
                    settingsCodec(structureInstance),
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                    Codec.intRange(0, 20).fieldOf("size").forGetter(structure -> structure.maxDepth),
                    HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                    Codec.BOOL.fieldOf("use_expansion_hack").forGetter(structure -> structure.useExpansionHack),
                    Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
                    Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter),
                    Codec.list(PoolAliasBinding.CODEC).optionalFieldOf("pool_aliases", List.of()).forGetter(structure -> structure.poolAliases),
                    DimensionPadding.CODEC
                        .optionalFieldOf("dimension_padding", DEFAULT_DIMENSION_PADDING)
                        .forGetter(structure -> structure.dimensionPadding),
                    LiquidSettings.CODEC.optionalFieldOf("liquid_settings", DEFAULT_LIQUID_SETTINGS).forGetter(structure -> structure.liquidSettings),
                    BlockState.CODEC.optionalFieldOf("spread_block").forGetter(structure -> structure.spreadBlock),
                    Codec.intRange(-20, 20).fieldOf("vertical_offset").forGetter(structure -> structure.verticalOffset)
                )
                .apply(structureInstance, GeyserStructure::new)
        )
        .validate(GeyserStructure::verifyRange);
    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int maxDepth;
    private final HeightProvider startHeight;
    private final boolean useExpansionHack;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;
    private final List<PoolAliasBinding> poolAliases;
    private final DimensionPadding dimensionPadding;
    private final LiquidSettings liquidSettings;
    private final Optional<BlockState> spreadBlock;
    private final int verticalOffset;

    private static DataResult<GeyserStructure> verifyRange(GeyserStructure p_286886_) {
        int i = switch (p_286886_.terrainAdaptation()) {
            case NONE -> 0;
            case BURY, BEARD_THIN, BEARD_BOX, ENCAPSULATE -> 12;
        };
        return p_286886_.maxDistanceFromCenter + i > 128
            ? DataResult.error(() -> "Structure size including terrain adaptation must not exceed 128")
            : DataResult.success(p_286886_);
    }

    public GeyserStructure(
        Structure.StructureSettings structureSettings,
        Holder<StructureTemplatePool> startPool,
        Optional<ResourceLocation> startJigsawName,
        int maxDepth,
        HeightProvider startHeight,
        boolean useExpansionHack,
        Optional<Heightmap.Types> projectStartToHeightmap,
        int maxDistanceFromCenter,
        List<PoolAliasBinding> poolAliases,
        DimensionPadding dimensionPadding,
        LiquidSettings liquidSettings,
        Optional<BlockState> spreadBlock,
        int verticalOffset
    ) {
        super(structureSettings);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.maxDepth = maxDepth;
        this.startHeight = startHeight;
        this.useExpansionHack = useExpansionHack;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
        this.poolAliases = poolAliases;
        this.dimensionPadding = dimensionPadding;
        this.liquidSettings = liquidSettings;
        this.spreadBlock = spreadBlock;
        this.verticalOffset = verticalOffset;
    }

    @SuppressWarnings("deprecation")
    @Override
    public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
        ChunkPos chunkpos = context.chunkPos();
        int x = chunkpos.getMinBlockX();
        int z = chunkpos.getMinBlockZ();
        int relY = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
        BlockPos blockpos = new BlockPos(x, relY, z);
        int landY = context.chunkGenerator().getFirstOccupiedHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
        BlockState blockState = context.chunkGenerator().getBaseColumn(x, z, context.heightAccessor(), context.randomState()).getBlock(landY);

        if (!blockState.getFluidState().isEmpty()) {
            return Optional.empty();
        }

        Optional<Structure.GenerationStub> opt = JigsawPlacement.addPieces(
            context,
            this.startPool,
            this.startJigsawName,
            this.maxDepth,
            blockpos,
            this.useExpansionHack,
            this.projectStartToHeightmap,
            this.maxDistanceFromCenter,
            PoolAliasLookup.create(this.poolAliases, blockpos, context.seed()),
            this.dimensionPadding,
            this.liquidSettings
        );

        return opt.map(generationStub -> {
            StructurePiecesBuilder builder = generationStub.getPiecesBuilder();
            BoundingBox boundingBox = builder.getBoundingBox();
            builder.offsetPiecesVertically(verticalOffset);
            spreadBlock.ifPresent(block ->
                builder.pieces.addFirst(
                new GeyserSpreadPiece(
                    boundingBox,
                    block
                )
            ));
            return new GenerationStub(generationStub.position(), Either.right(builder));
        });
    }

    @Override
    public StructureType<?> type() {
        return GeyserStructures.GEYSER.get();
    }
}
