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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class GemEmpoweringRecipeCategory implements IRecipeCategory<GemEmpoweringRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "gem_empowering");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "textures/gui/gem_empowering_station_gui.png");

    public static final RecipeType<GemEmpoweringRecipe> GEM_EMPOWERING_TYPE = new RecipeType<>(UID, GemEmpoweringRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public GemEmpoweringRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.GEM_EMPOWERING_STATION.get()));
    }


    @Override
    public RecipeType<GemEmpoweringRecipe> getRecipeType() {
        return GEM_EMPOWERING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Gem Infusing Station");
    }

    @Override
    public @Nullable IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, GemEmpoweringRecipe gemEmpoweringRecipe, IFocusGroup iFocusGroup) {
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 80, 11).addIngredients(gemEmpoweringRecipe.getIngredients().get(0));

        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, 80, 59).addItemStack(gemEmpoweringRecipe.getResultItem(null));

    }
}
