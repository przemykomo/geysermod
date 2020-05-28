package xyz.przemyk.geysermod.worldgen;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import xyz.przemyk.geysermod.Registration;

import java.util.Random;
import java.util.function.Function;

public class GeyserFeature extends Feature<NoFeatureConfig> {

    public GeyserFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        if (rand.nextInt(10) > 7) {
            int x = pos.getX() + rand.nextInt(15);
            int z = pos.getZ() + rand.nextInt(15);
            BlockPos blockPos = new BlockPos(x, worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG, x, z) - 1, z);

            if (worldIn.getBlockState(blockPos).getBlock() == Blocks.STONE) {
                worldIn.setBlockState(blockPos, Registration.GEYSER_BLOCK.get().getDefaultState(), 2);
                return true;
            }
        }

        return false;
    }
}
