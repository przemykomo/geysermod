package xyz.przemyk.geysermod.worldgen;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import xyz.przemyk.geysermod.Registration;

import java.util.Random;
import java.util.function.Function;

public class NetherGeyserFeature extends Feature<BlockClusterFeatureConfig> {

    public NetherGeyserFeature(Function<Dynamic<?>, ? extends BlockClusterFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, BlockClusterFeatureConfig config) {
        int i = 0;
        BlockPos.Mutable mutableBlockpos = new BlockPos.Mutable();

        for (int j = 0; j < config.tryCount; ++j) {
            mutableBlockpos.setPos(pos).move(rand.nextInt(10) - rand.nextInt(10), rand.nextInt(10) - rand.nextInt(10), rand.nextInt(10) - rand.nextInt(10));
            if ((worldIn.isAirBlock(mutableBlockpos) && worldIn.getBlockState(mutableBlockpos.down()).getBlock() == Blocks.NETHERRACK)) {
                worldIn.setBlockState(mutableBlockpos.down(), Registration.NETHER_GEYSER_BLOCK.get().getDefaultState(), 2);
                ++i;
            }
        }

        return i > 0;
    }
}
