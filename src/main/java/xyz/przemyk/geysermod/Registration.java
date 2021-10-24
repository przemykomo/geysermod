package xyz.przemyk.geysermod;

import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
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

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, GeyserMod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GeyserMod.MODID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, GeyserMod.MODID);

    public static final RegistryObject<GeyserBlock> GEYSER_BLOCK = BLOCKS.register("geyser", GeyserBlock::new);
    public static final RegistryObject<NetherGeyserBlock> NETHER_GEYSER_BLOCK = BLOCKS.register("nether_geyser", NetherGeyserBlock::new);
    public static final RegistryObject<RedstoneGeyserBlock> REDSTONE_GEYSER_BLOCK = BLOCKS.register("redstone_geyser", RedstoneGeyserBlock::new);
    public static final RegistryObject<RedstoneNetherGeyserBlock> REDSTONE_NETHER_GEYSER_BLOCK = BLOCKS.register("redstone_nether_geyser", RedstoneNetherGeyserBlock::new);

    public static final CreativeModeTab GEYSER_ITEM_GROUP = new CreativeModeTab(CreativeModeTab.TABS.length, GeyserMod.MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(GEYSER_ITEM.get());
        }
    };

    public static final RegistryObject<BlockItem> GEYSER_ITEM = ITEMS.register("geyser", () -> new BlockItem(GEYSER_BLOCK.get(), new Item.Properties().tab(GEYSER_ITEM_GROUP)));
    public static final RegistryObject<BlockItem> NETHER_GEYSER_ITEM = ITEMS.register("nether_geyser", () -> new BlockItem(NETHER_GEYSER_BLOCK.get(), new Item.Properties().tab(GEYSER_ITEM_GROUP)));
    public static final RegistryObject<BlockItem> REDSTONE_GEYSER_ITEM = ITEMS.register("redstone_geyser", () -> new BlockItem(REDSTONE_GEYSER_BLOCK.get(), new Item.Properties().tab(GEYSER_ITEM_GROUP)));
    public static final RegistryObject<BlockItem> REDSTONE_NETHER_GEYSER_ITEM = ITEMS.register("redstone_nether_geyser", () -> new BlockItem(REDSTONE_NETHER_GEYSER_BLOCK.get(), new Item.Properties().tab(GEYSER_ITEM_GROUP)));

    public static final RegistryObject<GeyserFeature> GEYSER_FEATURE = FEATURES.register("geyser_feature", () -> new GeyserFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<NetherGeyserFeature> NETHER_GEYSER_FEATURE = FEATURES.register("nether_geyser_feature", () -> new NetherGeyserFeature(RandomPatchConfiguration.CODEC));

    public static ConfiguredFeature<?, ?> GEYSER_CONFIGURED_FEATURE;
    public static ConfiguredFeature<?, ?> NETHER_GEYSER_CONFIGURED_FEATURE;

    public static void init() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        FEATURES.register(eventBus);
        eventBus.addListener(Registration::commonSetup);
        MinecraftForge.EVENT_BUS.addListener(Registration::addFeatures);
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(GeyserMod.MODID, "geyser"), GEYSER_CONFIGURED_FEATURE = GEYSER_FEATURE.get().configured(FeatureConfiguration.NONE));
            BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(GeyserMod.MODID, "patch_test"), NETHER_GEYSER_CONFIGURED_FEATURE = NETHER_GEYSER_FEATURE.get().configured((new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(NETHER_GEYSER_BLOCK.get().defaultBlockState()), SimpleBlockPlacer.INSTANCE)).tries(64).noProjection().build()).range(Features.Decorators.FULL_RANGE).rarity(6));
        });
    }

    private static void addFeatures(BiomeLoadingEvent event) {
        Biome.BiomeCategory category = event.getCategory();
        if (category == Biome.BiomeCategory.EXTREME_HILLS) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.LOCAL_MODIFICATIONS).add(() -> GEYSER_CONFIGURED_FEATURE);
        } else if (category == Biome.BiomeCategory.NETHER) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_DECORATION).add(() -> NETHER_GEYSER_CONFIGURED_FEATURE);
        }
    }
}
