package net.jason.mccourse.block.custom;

import net.jason.mccourse.worldgen.dimension.ModDimensions;
import net.jason.mccourse.worldgen.portal.KaupenTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class KaupenPortalBlock extends Block {
    public KaupenPortalBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        handleKaupenPortal(pPlayer, pPos);

        return InteractionResult.SUCCESS;
    }

    private void handleKaupenPortal(Entity player, BlockPos pPos) {
        if (player.level() instanceof ServerLevel serverLevel) {
            MinecraftServer minecraftServer = serverLevel.getServer();
            ResourceKey<Level> resourceKey = player.level().dimension() == ModDimensions.KAUPENDIM_LEVEL_KEY ?
                    Level.OVERWORLD : ModDimensions.KAUPENDIM_LEVEL_KEY;

            ServerLevel portalDimension = minecraftServer.getLevel(resourceKey);
            if (portalDimension != null && !player.isPassenger()) {
                boolean goingIntoKaupenDim = resourceKey.equals(ModDimensions.KAUPENDIM_LEVEL_KEY);

                KaupenTeleporter teleporter = new KaupenTeleporter(pPos, goingIntoKaupenDim);

                player.changeDimension(teleporter.getDimensionTransition(player, portalDimension));
            }
        }
    }


}
