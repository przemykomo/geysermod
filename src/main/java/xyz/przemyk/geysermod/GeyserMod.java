package xyz.przemyk.geysermod;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import xyz.przemyk.geysermod.setup.GeyserBlockEntities;
import xyz.przemyk.geysermod.setup.GeyserBlocks;
import xyz.przemyk.geysermod.setup.GeyserItems;
import xyz.przemyk.geysermod.setup.GeyserStructures;

@SuppressWarnings("unused")
@Mod(GeyserMod.MODID)
public class GeyserMod {

    public static final String MODID = "geysermod";

    public GeyserMod(IEventBus bus, ModContainer modContainer) {
        GeyserBlocks.init(bus);
        GeyserItems.init(bus);
        GeyserBlockEntities.init(bus);
        GeyserStructures.init(bus);
    }

    public static void shoot(ServerLevel level, BlockPos pos, AABB hurtEntitiesAABB) {
        BlockPos blockpos = pos.above();
        level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);
        level.sendParticles(ParticleTypes.SPLASH, (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.25D, (double)blockpos.getZ() + 0.5D, 5, 0.1D, hurtEntitiesAABB.maxY, 0.1D, 0.0D);

        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, hurtEntitiesAABB.move(blockpos))) {
            entity.hurt(level.damageSources().hotFloor(), 5.0f);
        }
    }

    public static void shootNether(ServerLevel level, BlockPos pos, AABB hurtEntitiesAABB) {
        BlockPos blockpos = pos.above();
        level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);
        level.sendParticles(ParticleTypes.LAVA, (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.25D, (double)blockpos.getZ() + 0.5D, 200, 0.1D, 3.0D, 0.1D, 0.0D);

        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, hurtEntitiesAABB.move(blockpos))) {
            entity.hurt(level.damageSources().lava(), 10.0f);
        }
    }
}
