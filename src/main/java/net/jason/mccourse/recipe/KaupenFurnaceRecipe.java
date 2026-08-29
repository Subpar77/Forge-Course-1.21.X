package net.jason.mccourse.recipe;

import net.jason.mccourse.block.ModBlocks;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public class KaupenFurnaceRecipe extends AbstractCookingRecipe {
    public KaupenFurnaceRecipe(String pGroup, CookingBookCategory pCategory, Ingredient pIngredient, ItemStack pResult, float pExperience, int pCookingTime) {
        super(Type.INSTANCE, pGroup, pCategory, pIngredient, pResult, pExperience, pCookingTime);
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.KAUPEN_FURNACE_BLOCK.get());
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.KAUPEN_FURNACE_TYPE.get();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.KAUPEN_FURNACE_SERIALIZER.get();
    }

    public static class Type implements RecipeType<KaupenFurnaceRecipe> {
        public static final Type INSTANCE = new Type();
    }


}
