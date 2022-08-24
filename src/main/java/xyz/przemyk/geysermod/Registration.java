package xyz.przemyk.geysermod;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.przemyk.geysermod.blocks.GeyserBlock;
import xyz.przemyk.geysermod.blocks.NetherGeyserBlock;
import xyz.przemyk.geysermod.blocks.RedstoneGeyserBlock;
import xyz.przemyk.geysermod.blocks.RedstoneNetherGeyserBlock;
import xyz.przemyk.geysermod.worldgen.GeyserFeature;
import xyz.przemyk.geysermod.worldgen.NetherGeyserFeature;

import java.util.List;

@SuppressWarnings("unused")
public class Registration {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, GeyserMod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GeyserMod.MODID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, GeyserMod.MODID);
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, GeyserMod.MODID);
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, GeyserMod.MODID);

    public static final RegistryObject<GeyserBlock> GEYSER_BLOCK = BLOCKS.register("geyser", GeyserBlock::new);
    public static final RegistryObject<NetherGeyserBlock> NETHER_GEYSER_BLOCK = BLOCKS.register("nether_geyser", NetherGeyserBlock::new);
    public static final RegistryObject<RedstoneGeyserBlock> REDSTONE_GEYSER_BLOCK = BLOCKS.register("redstone_geyser", RedstoneGeyserBlock::new);
    public static final RegistryObject<RedstoneNetherGeyserBlock> REDSTONE_NETHER_GEYSER_BLOCK = BLOCKS.register("redstone_nether_geyser", RedstoneNetherGeyserBlock::new);

    public static final CreativeModeTab GEYSER_ITEM_GROUP = new CreativeModeTab(GeyserMod.MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(GEYSER_ITEM.get());
        }
    };

    public static final RegistryObject<BlockItem> GEYSER_ITEM = ITEMS.register("geyser", () -> new BlockItem(GEYSER_BLOCK.get(), new Item.Properties().tab(GEYSER_ITEM_GROUP)));
    public static final RegistryObject<BlockItem> NETHER_GEYSER_ITEM = ITEMS.register("nether_geyser", () -> new BlockItem(NETHER_GEYSER_BLOCK.get(), new Item.Properties().tab(GEYSER_ITEM_GROUP)));
    public static final RegistryObject<BlockItem> REDSTONE_GEYSER_ITEM = ITEMS.register("redstone_geyser", () -> new BlockItem(REDSTONE_GEYSER_BLOCK.get(), new Item.Properties().tab(GEYSER_ITEM_GROUP)));
    public static final RegistryObject<BlockItem> REDSTONE_NETHER_GEYSER_ITEM = ITEMS.register("redstone_nether_geyser", () -> new BlockItem(REDSTONE_NETHER_GEYSER_BLOCK.get(), new Item.Properties().tab(GEYSER_ITEM_GROUP)));

    public static final RegistryObject<GeyserFeature> GEYSER_FEATURE = FEATURES.register("geyser", () -> new GeyserFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<NetherGeyserFeature> NETHER_GEYSER_FEATURE = FEATURES.register("nether_geyser", () -> new NetherGeyserFeature(RandomPatchConfiguration.CODEC));

    public static final RegistryObject<ConfiguredFeature<?, ?>> CONFIGURED_GEYSER_FEATURE = CONFIGURED_FEATURES.register("geyser", () -> new ConfiguredFeature<>(GEYSER_FEATURE.get(), NoneFeatureConfiguration.INSTANCE));
    public static final RegistryObject<ConfiguredFeature<?, ?>> CONFIGURED_NETHER_GEYSER_FEATURE = CONFIGURED_FEATURES.register("nether_geyser", () -> new ConfiguredFeature<>(NETHER_GEYSER_FEATURE.get(), FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(NETHER_GEYSER_BLOCK.get())), List.of(Blocks.NETHERRACK))));

    @SuppressWarnings("OptionalGetWithoutIsPresent") public static final RegistryObject<PlacedFeature> PLACED_GEYSER_FEATURE = PLACED_FEATURES.register("geyser", () -> new PlacedFeature(CONFIGURED_GEYSER_FEATURE.getHolder().get(), List.of()));
    @SuppressWarnings("OptionalGetWithoutIsPresent") public static final RegistryObject<PlacedFeature> PLACED_NETHER_GEYSER_FEATURE = PLACED_FEATURES.register("nether_geyser", () -> new PlacedFeature(CONFIGURED_NETHER_GEYSER_FEATURE.getHolder().get(), List.of(InSquarePlacement.spread(), PlacementUtils.RANGE_10_10, BiomeFilter.biome())));

    public static void init() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        FEATURES.register(eventBus);
        CONFIGURED_FEATURES.register(eventBus);
        PLACED_FEATURES.register(eventBus);
        MinecraftForge.EVENT_BUS.addListener(Registration::addFeatures);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static void addFeatures(BiomeLoadingEvent event) {
        Biome.BiomeCategory category = event.getCategory();
        if (category == Biome.BiomeCategory.MOUNTAIN) {
            event.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, PLACED_GEYSER_FEATURE.getHolder().get());
        } else if (category == Biome.BiomeCategory.NETHER) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, PLACED_NETHER_GEYSER_FEATURE.getHolder().get());
        }
    }
}
