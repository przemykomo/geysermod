package xyz.przemyk.geysermod.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import xyz.przemyk.geysermod.setup.GeyserStructures;

public class GeyserSpreadPiece extends StructurePiece {

    public GeyserSpreadPiece(
        StructureTemplateManager pStructureTemplateManager,
        BoundingBox box,
        BlockPos pTemplatePosition) {
        super(GeyserStructures.GEYSER_SPREAD.get(), 0, box);
    }

    public GeyserSpreadPiece(StructurePieceSerializationContext structurePieceSerializationContext, CompoundTag compoundTag) {
        super(GeyserStructures.GEYSER_SPREAD.get(), compoundTag);
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {

    }

    @Override
    public void postProcess(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkGenerator pGenerator, RandomSource pRandom, BoundingBox pBox, ChunkPos pChunkPos, BlockPos pPos) {
        BlockPos blockpos = this.boundingBox.getCenter();
        int x = blockpos.getX();
        int z = blockpos.getZ();
        float[] afloat = new float[]{1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.9F, 0.9F, 0.8F, 0.7F, 0.6F, 0.4F, 0.2F};
        int fLen = afloat.length;
        int span = (this.boundingBox.getXSpan() + this.boundingBox.getZSpan()) / 2;
        int spanRng = pRandom.nextInt(Math.max(1, 8 - span / 2));
        BlockPos.MutableBlockPos blockpos$mutableblockpos = BlockPos.ZERO.mutable();

        for (int i = x - fLen; i <= x + fLen; i++) {
            for (int j = z - fLen; j <= z + fLen; j++) {
                int a = Math.abs(i - x) + Math.abs(j - z);
                int b = Math.max(0, a + spanRng);
                if (b < fLen) {
                    float f = afloat[b];
                    if (pRandom.nextDouble() < (double)f) {
                        int k2 = pLevel.getHeight(Heightmap.Types.WORLD_SURFACE_WG, i, j) - 1;
                        blockpos$mutableblockpos.set(i, k2, j);
                        if (Math.abs(k2 - this.boundingBox.minY()) <= 3 && this.canBlockBeReplaced(pLevel, blockpos$mutableblockpos)) {
                            this.replaceBlock(pRandom, pLevel, blockpos$mutableblockpos);
//                            if (this.properties.overgrown) {
//                                this.maybeAddLeavesAbove(pRandom, pLevel, blockpos$mutableblockpos);
//                            }
//
//                            this.addNetherrackDripColumn(pRandom, pLevel, blockpos$mutableblockpos.below());
                        }
                    }
                }
            }
        }
    }

    private void replaceBlock(RandomSource random, WorldGenLevel level, BlockPos pos) {
        level.setBlock(pos, Blocks.GOLD_BLOCK.defaultBlockState(), 3);
    }

    private boolean canBlockBeReplaced(WorldGenLevel level, BlockPos pos) {
        return !level.getBlockState(pos).is(BlockTags.FEATURES_CANNOT_REPLACE);
    }
}
