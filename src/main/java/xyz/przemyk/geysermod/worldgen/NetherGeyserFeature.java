package xyz.przemyk.geysermod.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import xyz.przemyk.geysermod.Registration;

import java.util.Random;

public class NetherGeyserFeature extends Feature<BlockClusterFeatureConfig> {

    public NetherGeyserFeature(Codec<BlockClusterFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, BlockClusterFeatureConfig config) {
        int i = 0;
        BlockPos.Mutable mutableBlockpos = new BlockPos.Mutable();

        for (int j = 0; j < config.tries; ++j) {
            mutableBlockpos.set(pos).move(rand.nextInt(10) - rand.nextInt(10), rand.nextInt(10) - rand.nextInt(10), rand.nextInt(10) - rand.nextInt(10));
            if ((worldIn.isEmptyBlock(mutableBlockpos) && worldIn.getBlockState(mutableBlockpos.below()).getBlock() == Blocks.NETHERRACK)) {
                worldIn.setBlock(mutableBlockpos.below(), Registration.NETHER_GEYSER_BLOCK.get().defaultBlockState(), 2);
                ++i;
            }
        }

        return i > 0;
    }
}
