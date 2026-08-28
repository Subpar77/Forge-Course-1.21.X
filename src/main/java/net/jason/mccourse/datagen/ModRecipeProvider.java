package net.jason.mccourse.datagen;

import net.jason.mccourse.MCCourseMod;
import net.jason.mccourse.block.ModBlocks;
import net.jason.mccourse.datagen.custom.GemEmpoweringRecipeBuilder;
import net.jason.mccourse.item.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> ALEXANDRITE_SMELTABLES = List.of(ModItems.RAW_ALEXANDRITE.get(),
            ModBlocks.ALEXANDRITE_ORE.get(), ModBlocks.DEEPSLATE_ALEXANDRITE_ORE.get(), ModBlocks.NETHER_ALEXANDRITE_ORE.get(),
            ModBlocks.END_STONE_ALEXANDRITE_ORE.get());


    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALEXANDRITE_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.ALEXANDRITE.get())
                .unlockedBy("has_alexandrite", has(ModItems.ALEXANDRITE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID,"alexandrite_block"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ALEXANDRITE.get(), 9)
                .requires(ModBlocks.ALEXANDRITE_BLOCK.get())
                .unlockedBy("has_alexandrite_block", has(ModBlocks.ALEXANDRITE_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "alexandrite"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.RAW_ALEXANDRITE_BLOCK.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.RAW_ALEXANDRITE.get())
                .unlockedBy("has_alexandrite", has(ModItems.RAW_ALEXANDRITE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID,"raw_alexandrite_block"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALEXANDRITE_STAIRS.get(), 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', ModBlocks.ALEXANDRITE_BLOCK.get())
                .unlockedBy("has_alexandrite_block", has(ModBlocks.ALEXANDRITE_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID,"alexandrite_stairs"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALEXANDRITE_SLABS.get(), 6)
                .pattern("   ")
                .pattern("   ")
                .pattern("###")
                .define('#', ModBlocks.ALEXANDRITE_BLOCK.get())
                .unlockedBy("has_alexandrite_slab", has(ModBlocks.ALEXANDRITE_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID,"alexandrite_slab"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALEXANDRITE_PRESSURE_PLATE.get())
                .pattern("   ")
                .pattern("## ")
                .pattern("   ")
                .define('#', ModBlocks.ALEXANDRITE_BLOCK.get())
                .unlockedBy("has_alexandrite_block", has(ModBlocks.ALEXANDRITE_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID,"alexandrite_pressure_plate"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALEXANDRITE_FENCE.get(), 3)
                .pattern("   ")
                .pattern("#A#")
                .pattern("#A#")
                .define('#', ModBlocks.ALEXANDRITE_BLOCK.get())
                .define('A', Items.STICK)
                .unlockedBy("has_alexandrite_block", has(ModBlocks.ALEXANDRITE_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID,"alexandrite_fence"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALEXANDRITE_FENCE_GATE.get())
                .pattern("   ")
                .pattern("#A#")
                .pattern("#A#")
                .define('#', Items.STICK)
                .define('A', ModBlocks.ALEXANDRITE_BLOCK.get())
                .unlockedBy("has_alexandrite_block", has(ModBlocks.ALEXANDRITE_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID,"alexandrite_fence_gate"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALEXANDRITE_WALL.get(), 6)
                .pattern("   ")
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.ALEXANDRITE_BLOCK.get())
                .unlockedBy("has_alexandrite_block", has(ModBlocks.ALEXANDRITE_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID,"alexandrite_wall"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALEXANDRITE_DOOR.get(), 3)
                .pattern("## ")
                .pattern("## ")
                .pattern("## ")
                .define('#', ModBlocks.ALEXANDRITE_BLOCK.get())
                .unlockedBy("has_alexandrite_block", has(ModBlocks.ALEXANDRITE_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID,"alexandrite_door"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALEXANDRITE_TRAPDOOR.get(), 2)
                .pattern("   ")
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.ALEXANDRITE_BLOCK.get())
                .unlockedBy("has_alexandrite_block", has(ModBlocks.ALEXANDRITE_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID,"alexandrite_trapdoor"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ALEXANDRITE_SWORD.get())
                .pattern(" # ")
                .pattern(" # ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('#', ModItems.ALEXANDRITE.get())
                .unlockedBy("has_alexandrite", has(ModItems.ALEXANDRITE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID,"alexandrite_sword"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ALEXANDRITE_PICKAXE.get())
                .pattern("###")
                .pattern(" S ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('#', ModItems.ALEXANDRITE.get())
                .unlockedBy("has_alexandrite", has(ModItems.ALEXANDRITE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID,"alexandrite_pickaxe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ALEXANDRITE_AXE.get())
                .pattern("## ")
                .pattern("#S ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('#', ModItems.ALEXANDRITE.get())
                .unlockedBy("has_alexandrite", has(ModItems.ALEXANDRITE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID,"alexandrite_axe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ALEXANDRITE_SHOVEL.get())
                .pattern(" # ")
                .pattern(" S ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('#', ModItems.ALEXANDRITE.get())
                .unlockedBy("has_alexandrite", has(ModItems.ALEXANDRITE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID,"alexandrite_shovel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ALEXANDRITE_HOE.get())
                .pattern("## ")
                .pattern(" S ")
                .pattern(" S ")
                .define('S', Items.STICK)
                .define('#', ModItems.ALEXANDRITE.get())
                .unlockedBy("has_alexandrite", has(ModItems.ALEXANDRITE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID,"alexandrite_hoe"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ALEXANDRITE_PAXEL.get())
                .pattern("PSA")
                .pattern(" I ")
                .pattern(" I ")
                .define('P', ModItems.ALEXANDRITE_PICKAXE.get())
                .define('S', ModItems.ALEXANDRITE_SHOVEL.get())
                .define('A', ModItems.ALEXANDRITE_AXE.get())
                .define('I', Items.STICK)
                .unlockedBy("has_alexandrite_pickaxe", has(ModItems.ALEXANDRITE_PICKAXE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID,"alexandrite_paxel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ALEXANDRITE_HAMMER.get())
                .pattern("#S#")
                .pattern("#S#")
                .pattern(" S ")
                .define('#', ModItems.ALEXANDRITE.get())
                .define('S', Items.STICK)
                .unlockedBy("has_alexandrite", has(ModItems.ALEXANDRITE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID,"alexandrite_hammer"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RAW_ALEXANDRITE.get(), 9)
                .requires(ModBlocks.RAW_ALEXANDRITE_BLOCK.get())
                .unlockedBy("has_alexandrite_block", has(ModBlocks.RAW_ALEXANDRITE_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "raw_alexandrite"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.ALEXANDRITE_BUTTON.get())
                .requires(ModBlocks.ALEXANDRITE_BLOCK.get())
                .unlockedBy("has_alexandrite_block", has(ModBlocks.ALEXANDRITE_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "alexandrite_button"));

        oreSmeltingWithnameSpace(output, ALEXANDRITE_SMELTABLES, RecipeCategory.MISC, ModItems.ALEXANDRITE.get(), 0.25f, 200, "alexandrite");
        oreBlastingWithnameSpace(output, ALEXANDRITE_SMELTABLES, RecipeCategory.MISC, ModItems.ALEXANDRITE.get(), 0.25f, 100, "alexandrite");

        new GemEmpoweringRecipeBuilder(ModItems.RAW_ALEXANDRITE.get(), ModItems.ALEXANDRITE.get(), 3, 160, 50,
                new FluidStack(Fluids.WATER, 2000))
                .unlockedBy("has_raw_alexandrite", has(ModItems.RAW_ALEXANDRITE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "alexandrite_from_gem_empowering"));

        new GemEmpoweringRecipeBuilder(Items.COAL, Items.DIAMOND, 7, 40, 150,
                new FluidStack(Fluids.LAVA, 500))
                .unlockedBy("has_diamond", has(Items.DIAMOND))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "diamond_from_gem_empowering"));

    }

    // Helpers to ensure custom recipes get saved under mod namespace.
    private static void oreSmeltingWithnameSpace(RecipeOutput output, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
        for (ItemLike ingredient : ingredients) {
            String recipeName = getItemName(result) + "_from_smelthing_" + getItemName(ingredient);
            SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), category, result, experience, cookingTime).group(group).unlockedBy(getHasName(ingredient), has(ingredient))
                    .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, recipeName));
        }
    }

    private static void oreBlastingWithnameSpace(RecipeOutput output, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
        for (ItemLike ingredient : ingredients) {
            String recipeName = getItemName(result) + "_from_blasting_" + getItemName(ingredient);
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ingredient), category, result, experience, cookingTime).group(group).unlockedBy(getHasName(ingredient),has(ingredient))
                .save(output,ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, recipeName));
        }
    }
}
