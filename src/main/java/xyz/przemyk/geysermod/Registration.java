package xyz.przemyk.geysermod;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.geysermod.blocks.GeyserBlock;
import xyz.przemyk.geysermod.worldgen.GeyserFeature;

@SuppressWarnings("ConstantConditions")
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
//        DeferredWorkQueue.runLater(() -> Biomes.MOUNTAINS.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, GEYSER_FEATURE.get().withConfiguration(new NoFeatureConfig())));
        DeferredWorkQueue.runLater(() -> Biomes.MOUNTAINS.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(GEYSER_FEATURE.get(), IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG)));
    }

    public static final RegistryObject<GeyserBlock> GEYSER_BLOCK = BLOCKS.register("geyser", GeyserBlock::new);

    public static final ItemGroup GEYSER_ITEM_GROUP = new ItemGroup(ItemGroup.GROUPS.length, "geysermod") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(GEYSER_ITEM.get());
        }
    };

    public static final RegistryObject<BlockItem> GEYSER_ITEM = ITEMS.register("geyser", () -> new BlockItem(GEYSER_BLOCK.get(), new Item.Properties().group(GEYSER_ITEM_GROUP)));

    public static final RegistryObject<GeyserFeature> GEYSER_FEATURE = FEATURES.register("geyser_feature", () -> new GeyserFeature(NoFeatureConfig::deserialize));
}
