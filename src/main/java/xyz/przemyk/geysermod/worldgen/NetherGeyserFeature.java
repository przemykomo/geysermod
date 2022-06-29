package xyz.przemyk.geysermod.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class NetherGeyserFeature extends Feature<RandomPatchConfiguration> {

    public NetherGeyserFeature(Codec<RandomPatchConfiguration> p_66605_) {
        super(p_66605_);
    }

    public boolean place(FeaturePlaceContext<RandomPatchConfiguration> p_160210_) {
        RandomPatchConfiguration randompatchconfiguration = p_160210_.config();
        RandomSource random = p_160210_.random();
        BlockPos blockpos = p_160210_.origin();
        WorldGenLevel worldgenlevel = p_160210_.level();
        int i = 0;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        int j = randompatchconfiguration.xzSpread() + 1;
        int k = randompatchconfiguration.ySpread() + 1;

        for(int l = 0; l < randompatchconfiguration.tries(); ++l) {
            blockpos$mutableblockpos.setWithOffset(blockpos, random.nextInt(j) - random.nextInt(j), random.nextInt(k) - random.nextInt(k), random.nextInt(j) - random.nextInt(j));
//            BlockPos blockpos2 = blockpos$mutableblockpos.below();
//            BlockState blockstate1 = worldgenlevel.getBlockState(blockpos2);
//            if (blockstate1.is(Tags.Blocks.NETHERRACK)) {
                if (randompatchconfiguration.feature().get().place(worldgenlevel, p_160210_.chunkGenerator(), random, blockpos$mutableblockpos)) {
                    ++i;
                    System.out.println(blockpos$mutableblockpos);
                    break;
                }
//            }
        }

        return i > 0;
    }
}
