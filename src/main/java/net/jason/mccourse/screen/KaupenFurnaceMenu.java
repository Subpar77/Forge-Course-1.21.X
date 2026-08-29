package net.jason.mccourse.screen;

import net.jason.mccourse.block.entity.KaupenFurnaceBlockEntity;
import net.jason.mccourse.recipe.KaupenFurnaceRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;

public class KaupenFurnaceMenu extends AbstractFurnaceMenu {
    protected KaupenFurnaceMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf friendlyByteBuf) {
        this(pContainerId, pPlayerInventory);
    }

    public KaupenFurnaceMenu(int pContainerId, Inventory pPlayerInventory, Container container, ContainerData data) {
        super(ModMenuTypes.KAUPEN_FURNACE_MENU.get(), KaupenFurnaceRecipe.Type.INSTANCE, RecipeBookType.FURNACE, pContainerId, pPlayerInventory, container, data);
    }

    public KaupenFurnaceMenu(int pContainerId, Inventory pPlayerInventory) {
        super(ModMenuTypes.KAUPEN_FURNACE_MENU.get(), KaupenFurnaceRecipe.Type.INSTANCE , RecipeBookType.FURNACE, pContainerId, pPlayerInventory);
    }

    @Override
    protected boolean isFuel(ItemStack pStack) {
        return KaupenFurnaceBlockEntity.isKaupenFuel(pStack);
    }
}
