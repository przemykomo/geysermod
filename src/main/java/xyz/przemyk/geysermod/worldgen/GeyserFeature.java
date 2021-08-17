package xyz.przemyk.geysermod.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import xyz.przemyk.geysermod.Registration;

import java.util.Random;

public class GeyserFeature extends Feature<NoFeatureConfig> {

    public GeyserFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        if (rand.nextInt(10) > 7) {
            int x = pos.getX() + rand.nextInt(15);
            int z = pos.getZ() + rand.nextInt(15);
            BlockPos blockPos = new BlockPos(x, worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG, x, z) - 1, z);

            if (worldIn.getBlockState(blockPos).getBlock() == Blocks.STONE) {
                worldIn.setBlock(blockPos, Registration.GEYSER_BLOCK.get().defaultBlockState(), 2);
                return true;
            }
        }

        return false;
    }
}
