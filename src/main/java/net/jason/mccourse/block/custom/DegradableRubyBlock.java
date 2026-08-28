package net.jason.mccourse.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class DegradableRubyBlock extends Block implements GemDegradable {
    private GemDegradationLevel degradationLevel;





    public DegradableRubyBlock(GemDegradationLevel degradationLevel, Properties properties) {
        super(properties);
        this.degradationLevel = degradationLevel;
    }

    @Override
    protected void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
       this.changeOverTime(pState, pLevel, pPos, pRandom);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState pState) {
        return GemDegradable.getNext(pState.getBlock()).isPresent();
    }

    @Override
    public GemDegradationLevel getAge() {
        return degradationLevel;
    }
}
