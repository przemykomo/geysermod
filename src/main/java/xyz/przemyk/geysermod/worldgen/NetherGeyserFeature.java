package xyz.przemyk.geysermod.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

import java.util.Random;

public class NetherGeyserFeature extends Feature<RandomPatchConfiguration> {

    public NetherGeyserFeature(Codec<RandomPatchConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<RandomPatchConfiguration> ctx) {
//        Random rand = ctx.random();
//        WorldGenLevel worldIn = ctx.level();
//        RandomPatchConfiguration config = ctx.config();
//        BlockPos pos = ctx.origin();
//        int i = 0;
//        BlockPos.MutableBlockPos mutableBlockpos = new BlockPos.MutableBlockPos();
//
//        for (int j = 0; j < 20; ++j) {
//            mutableBlockpos.set(pos).move(rand.nextInt(10) - rand.nextInt(10), rand.nextInt(10) - rand.nextInt(10), rand.nextInt(10) - rand.nextInt(10));
//            if ((worldIn.isEmptyBlock(mutableBlockpos) && worldIn.getBlockState(mutableBlockpos.below()).getBlock() == Blocks.NETHERRACK)) {
//                System.out.println(mutableBlockpos);
//                worldIn.setBlock(mutableBlockpos.below(), Registration.NETHER_GEYSER_BLOCK.get().defaultBlockState(), 2);
//                ++i;
//            }
//        }
//
//        return i > 0;

        RandomPatchConfiguration config = ctx.config();
        Random random = ctx.random();
        BlockPos origin = ctx.origin();
        WorldGenLevel level = ctx.level();
        BlockState state = config.stateProvider.getState(random, origin);
        BlockPos blockPos;
        if (config.project) {
            blockPos = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, origin);
        } else {
            blockPos = origin;
        }

        int success = 0;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for(int var10 = 0; var10 < config.tries; ++var10) {
            mutableBlockPos.setWithOffset(blockPos, random.nextInt(config.xspread + 1) - random.nextInt(config.xspread + 1), random.nextInt(config.yspread + 1) - random.nextInt(config.yspread + 1), random.nextInt(config.zspread + 1) - random.nextInt(config.zspread + 1));
            BlockPos below = mutableBlockPos.below();
            BlockState belowBlockState = level.getBlockState(below);
            if ((level.isEmptyBlock(mutableBlockPos) || config.canReplace && level.getBlockState(mutableBlockPos).getMaterial().isReplaceable()) && state.canSurvive(level, mutableBlockPos) && (config.whitelist.isEmpty() || config.whitelist.contains(belowBlockState.getBlock())) && !config.blacklist.contains(belowBlockState) && (!config.needWater || level.getFluidState(below.west()).is(FluidTags.WATER) || level.getFluidState(below.east()).is(FluidTags.WATER) || level.getFluidState(below.north()).is(FluidTags.WATER) || level.getFluidState(below.south()).is(FluidTags.WATER))) {
                config.blockPlacer.place(level, below, state, random);
                ++success;
            }
        }

        return success > 0;
    }
}
