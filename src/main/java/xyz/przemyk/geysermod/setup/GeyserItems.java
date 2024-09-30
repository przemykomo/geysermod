package xyz.przemyk.geysermod.setup;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.przemyk.geysermod.GeyserMod;

@SuppressWarnings("unused")
public class GeyserItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, GeyserMod.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, GeyserMod.MODID);

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
        CREATIVE_MODE_TABS.register(bus);
    }

    public static final DeferredHolder<Item, BlockItem> GEYSER_ITEM = ITEMS.register("stone_geyser",() -> new BlockItem(GeyserBlocks.STONE_GEYSER.get(), new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> HOT_SPRING_GEYSER = ITEMS.register("hot_spring",() -> new BlockItem(GeyserBlocks.HOT_SPRING_GEYSER.get(), new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> HYDROTHERMAL_VENT_GEYSER = ITEMS.register("hydro_vent",() -> new BlockItem(GeyserBlocks.HYDROTHERMAL_VENT_GEYSER.get(), new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> VOLCANIC_GEYSER = ITEMS.register("volcanic",() -> new BlockItem(GeyserBlocks.VOLCANIC_GEYSER.get(), new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> GOLD_VOLCANO_GEYSER = ITEMS.register("gold_volcano",() -> new BlockItem(GeyserBlocks.GOLD_VOLCANO_GEYSER.get(), new Item.Properties()));

    //    public static final DeferredHolder<Item, BlockItem> NETHER_GEYSER_ITEM = ITEMS.register("blackstone_geyser", () -> new BlockItem(GeyserBlocks.BLACKSTONE_GEYSER.get(), new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> REDSTONE_GEYSER_ITEM = ITEMS.register("redstone_geyser", () -> new BlockItem(GeyserBlocks.REDSTONE_GEYSER.get(), new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> REDSTONE_NETHER_GEYSER_ITEM = ITEMS.register("redstone_nether_geyser", () -> new BlockItem(GeyserBlocks.REDSTONE_NETHER_GEYSER.get(), new Item.Properties()));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GEYSER_TAB = CREATIVE_MODE_TABS.register("geyser_tab", () -> CreativeModeTab.builder()
        .icon(() -> GEYSER_ITEM.get().getDefaultInstance())
        .title(Component.translatable(GeyserMod.MODID + ".geyser_tab"))
        .displayItems(((itemDisplayParameters, output) -> {
            output.accept(GEYSER_ITEM.get());
            output.accept(HOT_SPRING_GEYSER.get());
            output.accept(HYDROTHERMAL_VENT_GEYSER.get());
            output.accept(VOLCANIC_GEYSER.get());
            output.accept(GOLD_VOLCANO_GEYSER.get());
//            output.accept(NETHER_GEYSER_ITEM.get());
            output.accept(REDSTONE_GEYSER_ITEM.get());
            output.accept(REDSTONE_NETHER_GEYSER_ITEM.get());
        })).build());
}
