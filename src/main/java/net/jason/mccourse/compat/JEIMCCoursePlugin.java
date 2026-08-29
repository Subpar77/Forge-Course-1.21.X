package net.jason.mccourse.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.jason.mccourse.MCCourseMod;
import net.jason.mccourse.recipe.GemEmpoweringRecipe;
import net.jason.mccourse.recipe.KaupenFurnaceRecipe;
import net.jason.mccourse.recipe.ModRecipes;
import net.jason.mccourse.screen.GemEmpoweringStationScreen;
import net.jason.mccourse.screen.KaupenFurnaceScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIMCCoursePlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new GemEmpoweringRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new KaupenFurnaceRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<GemEmpoweringRecipe> empoweringRecipes = recipeManager.getAllRecipesFor(ModRecipes.GEM_EMPOWERING_TYPE.get())
                .stream().map(RecipeHolder::value).toList();
        registration.addRecipes(GemEmpoweringRecipeCategory.GEM_EMPOWERING_TYPE, empoweringRecipes);

        List<KaupenFurnaceRecipe> kaupenFurnaceRecipes = recipeManager.getAllRecipesFor(ModRecipes.KAUPEN_FURNACE_TYPE.get())
                .stream().map(RecipeHolder::value).toList();
        registration.addRecipes(KaupenFurnaceRecipeCategory.KAUPEN_FURNACE_TYPE, kaupenFurnaceRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(GemEmpoweringStationScreen.class, 60, 30, 20, 30,
                GemEmpoweringRecipeCategory.GEM_EMPOWERING_TYPE);

        registration.addRecipeClickArea(KaupenFurnaceScreen.class, 60, 30, 20, 30,
                KaupenFurnaceRecipeCategory.KAUPEN_FURNACE_TYPE);
    }
}
