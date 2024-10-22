package xyz.przemyk.geysermod.setup;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.przemyk.geysermod.GeyserMod;
import xyz.przemyk.geysermod.structures.GeyserSpreadPiece;
import xyz.przemyk.geysermod.structures.GeyserStructure;

public class GeyserStructures {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(BuiltInRegistries.STRUCTURE_TYPE, GeyserMod.MODID);
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECES = DeferredRegister.create(BuiltInRegistries.STRUCTURE_PIECE, GeyserMod.MODID);

    public static void init(IEventBus bus) {
        STRUCTURE_TYPES.register(bus);
        STRUCTURE_PIECES.register(bus);
    }

    public static final DeferredHolder<StructureType<?>, StructureType<GeyserStructure>> GEYSER = STRUCTURE_TYPES.register("geyser", () -> () -> GeyserStructure.CODEC);
    public static final DeferredHolder<StructurePieceType, StructurePieceType> GEYSER_SPREAD = STRUCTURE_PIECES.register("geyser_spread", () -> GeyserSpreadPiece::new);
}
