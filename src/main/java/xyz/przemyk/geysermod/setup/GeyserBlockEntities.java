package xyz.przemyk.geysermod.setup;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.przemyk.geysermod.GeyserBlockEntity;
import xyz.przemyk.geysermod.GeyserMod;

public class GeyserBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, GeyserMod.MODID);

    public static void init(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GeyserBlockEntity>> GEYSER = BLOCK_ENTITIES.register("geyser",
        () -> BlockEntityType.Builder.of(GeyserBlockEntity::new,
            GeyserBlocks.STONE_GEYSER.get(),
            GeyserBlocks.HOT_SPRING_GEYSER.get(),
            GeyserBlocks.HYDROTHERMAL_VENT_GEYSER.get(),
            GeyserBlocks.VOLCANIC_GEYSER.get(),
            GeyserBlocks.GOLD_VOLCANO_GEYSER.get()
        ).build(null));
}
