package xyz.przemyk.geysermod.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import xyz.przemyk.geysermod.Registration;

import java.util.Random;

public class GeyserFeature extends Feature<NoneFeatureConfiguration> {

    public GeyserFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        Random rand = ctx.random();
        WorldGenLevel worldIn = ctx.level();
        BlockPos pos = ctx.origin();
        if (rand.nextInt(10) > 7) {
            int x = pos.getX() + rand.nextInt(15);
            int z = pos.getZ() + rand.nextInt(15);
            BlockPos blockPos = new BlockPos(x, worldIn.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z) - 1, z);

            if (worldIn.getBlockState(blockPos).getBlock() == Blocks.STONE) {
                worldIn.setBlock(blockPos, Registration.GEYSER_BLOCK.get().defaultBlockState(), 2);
                return true;
            }
        }

        return false;
    }
}
