package xyz.przemyk.geysermod;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.przemyk.geysermod.blocks.GeyserBlock;
import xyz.przemyk.geysermod.blocks.NetherGeyserBlock;
import xyz.przemyk.geysermod.blocks.RedstoneGeyserBlock;
import xyz.przemyk.geysermod.blocks.RedstoneNetherGeyserBlock;

@SuppressWarnings("unused")
@Mod(GeyserMod.MODID)
public class GeyserMod {
    public static final String MODID = "geysermod";

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, GeyserMod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, GeyserMod.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, GeyserMod.MODID);

    public static final DeferredHolder<Block, GeyserBlock> GEYSER_BLOCK = BLOCKS.register("geyser", GeyserBlock::new);
    public static final DeferredHolder<Block, NetherGeyserBlock> NETHER_GEYSER_BLOCK = BLOCKS.register("nether_geyser", NetherGeyserBlock::new);
    public static final DeferredHolder<Block, RedstoneGeyserBlock> REDSTONE_GEYSER_BLOCK = BLOCKS.register("redstone_geyser", RedstoneGeyserBlock::new);
    public static final DeferredHolder<Block, RedstoneNetherGeyserBlock> REDSTONE_NETHER_GEYSER_BLOCK = BLOCKS.register("redstone_nether_geyser", RedstoneNetherGeyserBlock::new);

    public static final DeferredHolder<Item, BlockItem> GEYSER_ITEM = ITEMS.register("geyser", () -> new BlockItem(GEYSER_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> NETHER_GEYSER_ITEM = ITEMS.register("nether_geyser", () -> new BlockItem(NETHER_GEYSER_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> REDSTONE_GEYSER_ITEM = ITEMS.register("redstone_geyser", () -> new BlockItem(REDSTONE_GEYSER_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> REDSTONE_NETHER_GEYSER_ITEM = ITEMS.register("redstone_nether_geyser", () -> new BlockItem(REDSTONE_NETHER_GEYSER_BLOCK.get(), new Item.Properties()));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GEYSER_TAB = CREATIVE_MODE_TABS.register("geyser_tab", () -> CreativeModeTab.builder()
            .icon(() -> GEYSER_ITEM.get().getDefaultInstance())
            .title(Component.translatable(MODID + ".geyser_tab"))
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(GEYSER_ITEM.get());
                output.accept(NETHER_GEYSER_ITEM.get());
                output.accept(REDSTONE_GEYSER_ITEM.get());
                output.accept(REDSTONE_NETHER_GEYSER_ITEM.get());
            })).build());

    public GeyserMod(IEventBus modEventBus, ModContainer modContainer) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
