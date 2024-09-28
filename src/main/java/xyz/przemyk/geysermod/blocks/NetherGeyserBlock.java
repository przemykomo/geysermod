package xyz.przemyk.geysermod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import xyz.przemyk.geysermod.GeyserMod;

public class NetherGeyserBlock extends Block {

    public NetherGeyserBlock() {
        super(Properties.ofFullCopy(Blocks.NETHERRACK).randomTicks());
    }

    protected AABB hurtEntitiesAABB = new AABB(0, 0, 0, 1, 3, 1);

    @Override
    public void randomTick(BlockState blockState, ServerLevel level, BlockPos blockPos, RandomSource randomSource) {
        GeyserMod.shootNether(level, blockPos, hurtEntitiesAABB);
    }
}
