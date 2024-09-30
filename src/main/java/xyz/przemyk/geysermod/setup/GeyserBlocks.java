package xyz.przemyk.geysermod.setup;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.przemyk.geysermod.GeyserMod;
import xyz.przemyk.geysermod.blocks.GeyserBlock;
import xyz.przemyk.geysermod.blocks.RedstoneGeyserBlock;
import xyz.przemyk.geysermod.blocks.RedstoneNetherGeyserBlock;

public class GeyserBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, GeyserMod.MODID);

    public static void init(IEventBus bus) {
        BLOCKS.register(bus);
    }

    public static final DeferredHolder<Block, GeyserBlock> STONE_GEYSER = BLOCKS.register("stone_geyser", () ->
        new GeyserBlock(6 * 60 * 20, 10 * 20, 14 * 20, 10, 14, "geyser_burst/basic", false));

    public static final DeferredHolder<Block, GeyserBlock> HOT_SPRING_GEYSER = BLOCKS.register("hot_spring", () ->
        new GeyserBlock(90 * 20, 50 * 20, 70 * 20, 1, 3, "geyser_burst/hot_spring", false));

    public static final DeferredHolder<Block, GeyserBlock> HYDROTHERMAL_VENT_GEYSER = BLOCKS.register("hydro_vent", () ->
        new GeyserBlock(65 * 20, 50 * 20, 60 * 20, 2, 6, "geyser_burst/hydro_vent", false));

    public static final DeferredHolder<Block, GeyserBlock> VOLCANIC_GEYSER = BLOCKS.register("volcanic", () ->
        new GeyserBlock(9 * 60 * 20, 18 * 20, 22 * 20, 2, 6, "geyser_burst/volcanic", true));

    public static final DeferredHolder<Block, GeyserBlock> GOLD_VOLCANO_GEYSER = BLOCKS.register("gold_volcano", () ->
        new GeyserBlock(13 * 60 * 20, 25 * 20, 35 * 20, 10, 14, "geyser_burst/gold_volcano", true));


    public static final DeferredHolder<Block, RedstoneGeyserBlock> REDSTONE_GEYSER = BLOCKS.register("redstone_geyser", RedstoneGeyserBlock::new);
    public static final DeferredHolder<Block, RedstoneNetherGeyserBlock> REDSTONE_NETHER_GEYSER = BLOCKS.register("redstone_nether_geyser", RedstoneNetherGeyserBlock::new);
}
