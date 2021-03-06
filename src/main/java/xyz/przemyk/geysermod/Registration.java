package xyz.przemyk.geysermod;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.geysermod.blocks.GeyserBlock;
import xyz.przemyk.geysermod.blocks.NetherGeyserBlock;
import xyz.przemyk.geysermod.blocks.RedstoneGeyserBlock;
import xyz.przemyk.geysermod.blocks.RedstoneNetherGeyserBlock;
import xyz.przemyk.geysermod.worldgen.GeyserFeature;
import xyz.przemyk.geysermod.worldgen.NetherGeyserFeature;

@SuppressWarnings("unused")
public class Registration {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, GeyserMod.MODID);
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, GeyserMod.MODID);
    public static final DeferredRegister<Feature<?>> FEATURES = new DeferredRegister<>(ForgeRegistries.FEATURES, GeyserMod.MODID);

    public static void init() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        FEATURES.register(eventBus);
    }

    @SuppressWarnings("deprecation")
    public static void commonSetup() {
        DeferredWorkQueue.runLater(Registration::addFeatures);
    }

    private static void addFeatures() {
        Biomes.MOUNTAINS.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, GEYSER_FEATURE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));

        BlockClusterFeatureConfig NETHER_GEYSER_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(NETHER_GEYSER_BLOCK.get().getDefaultState()), new SimpleBlockPlacer())).tries(10).func_227317_b_().build();
        Biomes.NETHER.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, NETHER_GEYSER_FEATURE.get().withConfiguration(NETHER_GEYSER_CONFIG).withPlacement(Placement.HELL_FIRE.configure(new FrequencyConfig(10))));
    }

    public static final RegistryObject<GeyserBlock> GEYSER_BLOCK = BLOCKS.register("geyser", GeyserBlock::new);
    public static final RegistryObject<NetherGeyserBlock> NETHER_GEYSER_BLOCK = BLOCKS.register("nether_geyser", NetherGeyserBlock::new);
    public static final RegistryObject<RedstoneGeyserBlock> REDSTONE_GEYSER_BLOCK = BLOCKS.register("redstone_geyser", RedstoneGeyserBlock::new);
    public static final RegistryObject<RedstoneNetherGeyserBlock> REDSTONE_NETHER_GEYSER_BLOCK = BLOCKS.register("redstone_nether_geyser", RedstoneNetherGeyserBlock::new);

    public static final ItemGroup GEYSER_ITEM_GROUP = new ItemGroup(ItemGroup.GROUPS.length, "geysermod") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(GEYSER_ITEM.get());
        }
    };

    public static final RegistryObject<BlockItem> GEYSER_ITEM = ITEMS.register("geyser", () -> new BlockItem(GEYSER_BLOCK.get(), new Item.Properties().group(GEYSER_ITEM_GROUP)));
    public static final RegistryObject<BlockItem> NETHER_GEYSER_ITEM = ITEMS.register("nether_geyser", () -> new BlockItem(NETHER_GEYSER_BLOCK.get(), new Item.Properties().group(GEYSER_ITEM_GROUP)));
    public static final RegistryObject<BlockItem> REDSTONE_GEYSER_ITEM = ITEMS.register("redstone_geyser", () -> new BlockItem(REDSTONE_GEYSER_BLOCK.get(), new Item.Properties().group(GEYSER_ITEM_GROUP)));
    public static final RegistryObject<BlockItem> REDSTONE_NETHER_GEYSER_ITEM = ITEMS.register("redstone_nether_geyser", () -> new BlockItem(REDSTONE_NETHER_GEYSER_BLOCK.get(), new Item.Properties().group(GEYSER_ITEM_GROUP)));

    public static final RegistryObject<GeyserFeature> GEYSER_FEATURE = FEATURES.register("geyser_feature", () -> new GeyserFeature(NoFeatureConfig::deserialize));
    public static final RegistryObject<NetherGeyserFeature> NETHER_GEYSER_FEATURE = FEATURES.register("nether_geyser_feature", () -> new NetherGeyserFeature(BlockClusterFeatureConfig::deserialize));
}
