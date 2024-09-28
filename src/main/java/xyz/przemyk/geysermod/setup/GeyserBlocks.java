package xyz.przemyk.geysermod.setup;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.przemyk.geysermod.GeyserMod;
import xyz.przemyk.geysermod.blocks.GeyserBlock;
import xyz.przemyk.geysermod.blocks.NetherGeyserBlock;
import xyz.przemyk.geysermod.blocks.RedstoneGeyserBlock;
import xyz.przemyk.geysermod.blocks.RedstoneNetherGeyserBlock;

public class GeyserBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, GeyserMod.MODID);

    public static void init(IEventBus bus) {
        BLOCKS.register(bus);
    }

    public static final DeferredHolder<Block, GeyserBlock> STONE_GEYSER = BLOCKS.register("stone_geyser", GeyserBlock::new);
    public static final DeferredHolder<Block, NetherGeyserBlock> BLACKSTONE_GEYSER = BLOCKS.register("blackstone_geyser", NetherGeyserBlock::new);
    public static final DeferredHolder<Block, RedstoneGeyserBlock> REDSTONE_GEYSER = BLOCKS.register("redstone_geyser", RedstoneGeyserBlock::new);
    public static final DeferredHolder<Block, RedstoneNetherGeyserBlock> REDSTONE_NETHER_GEYSER = BLOCKS.register("redstone_nether_geyser", RedstoneNetherGeyserBlock::new);
}
