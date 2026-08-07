package net.jason.mccourse.potion;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.IBrewingRecipe;

// BetterBrewingRecipe Class by CAS-ual-TY from https://github.com/CAS-ual-TY/Extra-Potions (GPL-3.0 License)
// https://github.com/CAS-ual-TY/Extra-Potions/blob/main/LICENSE
public class BetterBrewingRecipe implements IBrewingRecipe {
    private final Holder<Potion> input;
    private final Item ingredient;
    private final Holder<Potion> output;

    public BetterBrewingRecipe(Holder<Potion> input, Item ingredient, Holder<Potion> output) {
        this.input = input;
        this.ingredient = ingredient;
        this.output = output;
    }

    @Override
    public boolean isInput(ItemStack input) {
        PotionContents contents = input.get(DataComponents.POTION_CONTENTS);

        return contents != null && contents.is(this.input);
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return ingredient.getItem() == this.ingredient;
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if(!this.isInput(input) || !this.isIngredient(ingredient)) {
            return ItemStack.EMPTY;
        }

        ItemStack itemStack = new ItemStack(input.getItem());
        itemStack.set(DataComponents.POTION_CONTENTS, new PotionContents(this.output));
        return itemStack;
    }
}