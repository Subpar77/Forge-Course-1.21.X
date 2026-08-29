package net.jason.mccourse.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.jason.mccourse.MCCourseMod;
import net.jason.mccourse.block.ModBlocks;
import net.jason.mccourse.recipe.GemEmpoweringRecipe;
import net.jason.mccourse.recipe.KaupenFurnaceRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class KaupenFurnaceRecipeCategory implements IRecipeCategory<KaupenFurnaceRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "kaupen_furnace");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "textures/gui/kaupen_furnace.png");

    public static final RecipeType<KaupenFurnaceRecipe> KAUPEN_FURNACE_TYPE = new RecipeType<>(UID, KaupenFurnaceRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public KaupenFurnaceRecipeCategory(IDrawable background, IDrawable icon) {
        this.background = background;
        this.icon = icon;
    }

    public KaupenFurnaceRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.KAUPEN_FURNACE_BLOCK.get()));
    }


    @Override
    public RecipeType<KaupenFurnaceRecipe> getRecipeType() {
        return KAUPEN_FURNACE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.mccourse.kaupen_furnace");
    }

    @Override
    public @Nullable IDrawable getBackground() {
        return background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, KaupenFurnaceRecipe kaupenFurnaceRecipe, IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 56, 17).addIngredients(kaupenFurnaceRecipe.getIngredients().get(0));

        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, 116, 35).addItemStack(kaupenFurnaceRecipe.getResultItem(null));
    }
}
