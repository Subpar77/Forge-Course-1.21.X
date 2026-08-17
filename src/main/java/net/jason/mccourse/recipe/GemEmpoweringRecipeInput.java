package net.jason.mccourse.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record GemEmpoweringRecipeInput(ItemStack input) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        if (index != 0) {
            throw new IllegalArgumentException("No item for index " + index);
        }

        return input;
    }

    @Override
    public int size() {
        return 1;
    }
}
