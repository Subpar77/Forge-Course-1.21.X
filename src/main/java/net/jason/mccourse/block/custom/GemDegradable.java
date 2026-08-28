package net.jason.mccourse.block.custom;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.mojang.serialization.Codec;
import net.jason.mccourse.block.ModBlocks;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.function.Supplier;

public interface GemDegradable extends ChangeOverTimeBlock<GemDegradable.GemDegradationLevel> {
    Supplier<BiMap<Block, Block>> NEXT_BY_BLOCK = Suppliers.memoize(
            () -> ImmutableBiMap.<Block, Block>builder()
                    .put(ModBlocks.RUBY_BLOCK.get(), ModBlocks.EXPOSED_RUBY_BLOCK.get())
                    .put(ModBlocks.EXPOSED_RUBY_BLOCK.get(), ModBlocks.WEATHERED_RUBY_BLOCK.get())
                    .put(ModBlocks.WEATHERED_RUBY_BLOCK.get(), ModBlocks.DEGRADED_RUBY_BLOCK.get())
                    .build()
    );
    Supplier<BiMap<Block, Block>> PREVIOUS_BY_BLOCK = Suppliers.memoize(() -> NEXT_BY_BLOCK.get().inverse());

    static Optional<Block> getPrevious(Block pBlock) {
        return Optional.ofNullable(PREVIOUS_BY_BLOCK.get().get(pBlock));
    }

    static Block getFirst(Block pBlock) {
        Block block = pBlock;

        for (Block block1 = PREVIOUS_BY_BLOCK.get().get(pBlock); block1 != null; block1 = PREVIOUS_BY_BLOCK.get().get(block1)) {
            block = block1;
        }

        return block;
    }

    static Optional<BlockState> getPrevious(BlockState pState) {
        return getPrevious(pState.getBlock()).map(block -> block.withPropertiesOf(pState));
    }

    static Optional<Block> getNext(Block pBlock) {
        return Optional.ofNullable(NEXT_BY_BLOCK.get().get(pBlock));
    }

    static BlockState getFirst(BlockState pState) {
        return getFirst(pState.getBlock()).withPropertiesOf(pState);
    }

    @Override
    default Optional<BlockState> getNext(BlockState pState) {
        return getNext(pState.getBlock()).map(block -> block.withPropertiesOf(pState));
    }

    @Override
    default float getChanceModifier() {
        return this.getAge() ==GemDegradationLevel.UNAFFECTED ? 0.75F : 1.0F;
    }

    public static enum GemDegradationLevel implements StringRepresentable {
        UNAFFECTED("unaffected"),
        EXPOSED("exposed"),
        WEATHERED("weathered"),
        DEGRADED("degraded");

        public static final Codec<net.minecraft.world.level.block.WeatheringCopper.WeatherState> CODEC =
                StringRepresentable.fromEnum(net.minecraft.world.level.block.WeatheringCopper.WeatherState::values);
        private final String name;

        private GemDegradationLevel(final String pName) {
            this.name = pName;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}