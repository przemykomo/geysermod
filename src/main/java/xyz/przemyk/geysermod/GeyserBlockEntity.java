package xyz.przemyk.geysermod;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.phys.AABB;
import xyz.przemyk.geysermod.blocks.GeyserBlock;
import xyz.przemyk.geysermod.setup.GeyserBlockEntities;

import java.util.List;

public class GeyserBlockEntity extends BlockEntity {

    public GeyserBlockEntity(BlockPos pPos, BlockState pState) {
        super(GeyserBlockEntities.GEYSER.get(), pPos, pState);
    }

    private int burstDuration = 0;
    private int burstHeight = 0;

    private int ticks = 0;

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        if (pTag.contains("duration")) {
            burstDuration = pTag.getInt("duration");
        } else {
            burstDuration = ((GeyserBlock) getBlockState().getBlock()).getBurstDuration(level);
        }

        if (pTag.contains("height")) {
            burstHeight = pTag.getInt("height");
        } else {
            burstHeight = ((GeyserBlock) getBlockState().getBlock()).getBurstHeight(level);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.putInt("duration", burstDuration);
        pTag.putInt("height", burstHeight);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (!level.isClientSide) {
            GeyserBlock block = (GeyserBlock) blockState.getBlock();
            if (ticks == 0) {
                burstDuration = block.getBurstDuration(level);
                burstHeight = block.getBurstHeight(level);
            }

            ticks++;
            if (ticks % block.getFrequency() == 0) {
                LootParams lootParams = new LootParams.Builder((ServerLevel) level).create(LootContextParamSets.EMPTY);
                LootTable lootTable = level.getServer().reloadableRegistries().getLootTable(block.burstLootTable);
                List<ItemStack> itemStacks = lootTable.getRandomItems(lootParams);

                for (ItemStack itemStack : itemStacks) {
                    ItemEntity itemEntity = new ItemEntity(level, blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, itemStack);
                    itemEntity.setDeltaMovement((Math.random() - 0.5) / 5, 1, (Math.random() - 0.5) / 5);
                    level.addFreshEntity(itemEntity);
                }

            } else if (ticks % block.getFrequency() < burstDuration) {
                BlockPos blockpos = blockPos.above();
                level.playSound(null, blockPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.1F, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);

                double halfDeviation = burstHeight / 2.0 / Math.sqrt(3);

                ((ServerLevel) level).sendParticles(block.lava ? ParticleTypes.FLAME : ParticleTypes.SPLASH,
                    (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.25D + halfDeviation, (double)blockpos.getZ() + 0.5D, burstHeight + 2,
                    0.1D, halfDeviation, 0.1D, 0.0D);

                AABB hurtEntitiesAABB = new AABB(0, 0, 0, 1, burstHeight, 1);
                for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, hurtEntitiesAABB.move(blockpos))) {
                    entity.hurt(level.damageSources().hotFloor(), 1.0f);
                }
            }
        }
    }
}
