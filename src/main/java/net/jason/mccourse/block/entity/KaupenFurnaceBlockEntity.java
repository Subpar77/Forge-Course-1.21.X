package net.jason.mccourse.block.entity;

import net.jason.mccourse.item.ModItems;
import net.jason.mccourse.recipe.KaupenFurnaceRecipe;
import net.jason.mccourse.screen.KaupenFurnaceMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class KaupenFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
    public static final Map<Item, Integer> BURN_DURATION_MAP =
            Map.of(ModItems.PEAT_BRICK.get(), 100,
                    ModItems.KOHLRABI.get(), 200,
                    Items.BLAZE_POWDER, 800);

    public static boolean isKaupenFuel(ItemStack stack) {
        return BURN_DURATION_MAP.containsKey(stack.getItem());
    }


    public KaupenFurnaceBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.KAUPEN_FURNACE_BLOCK_ENTITY.get(), pPos, pBlockState, KaupenFurnaceRecipe.Type.INSTANCE);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.mccourse.kaupen_furnace");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new KaupenFurnaceMenu(pContainerId, pInventory, this, dataAccess);
    }

    @Override
    protected int getBurnDuration(ItemStack pFuel) {
        return BURN_DURATION_MAP.getOrDefault(pFuel.getItem(), 0);
    }
}
