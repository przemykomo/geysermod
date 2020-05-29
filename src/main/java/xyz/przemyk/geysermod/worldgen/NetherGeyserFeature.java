package xyz.przemyk.geysermod.worldgen;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import xyz.przemyk.geysermod.Registration;

import java.util.Random;
import java.util.function.Function;

public class NetherGeyserFeature extends Feature<NoFeatureConfig> {

    public NetherGeyserFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int i = 0;
        BlockPos.MutableBlockPos mutableBlockpos = new BlockPos.MutableBlockPos();

        for (int j = 0; j < 10; ++j) {
            mutableBlockpos.setPos(pos).move(rand.nextInt(10) - rand.nextInt(10), rand.nextInt(10) - rand.nextInt(10), rand.nextInt(10) - rand.nextInt(10));
            if ((worldIn.isAirBlock(mutableBlockpos) && worldIn.getBlockState(mutableBlockpos.down()).getBlock() == Blocks.NETHERRACK)) {
                worldIn.setBlockState(mutableBlockpos.down(), Registration.NETHER_GEYSER_BLOCK.get().getDefaultState(), 2);
                ++i;
            }
        }

        return i > 0;
    }
}
