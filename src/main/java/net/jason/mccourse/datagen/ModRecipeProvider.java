package net.jason.mccourse.datagen;

import net.jason.mccourse.MCCourseMod;
import net.jason.mccourse.block.ModBlocks;
import net.jason.mccourse.item.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

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

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RAW_ALEXANDRITE.get(), 9)
                .requires(ModBlocks.RAW_ALEXANDRITE_BLOCK.get())
                .unlockedBy("has_alexandrite_block", has(ModBlocks.RAW_ALEXANDRITE_BLOCK.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "raw_alexandrite"));

/*        nineBlockStorageRecipesWithCustomPacking(output, RecipeCategory.MISC, ModItems.RAW_ALEXANDRITE.get(), RecipeCategory.MISC, ModBlocks.RAW_ALEXANDRITE_BLOCK.get()
        ,"mccourse:raw_alexandrite_block1", "alexandrite");
        nineBlockStorageRecipesRecipesWithCustomUnpacking(output, RecipeCategory.MISC, ModItems.RAW_ALEXANDRITE.get(), RecipeCategory.MISC, ModBlocks.RAW_ALEXANDRITE_BLOCK.get(),
                "mccourse:raw_alexandrite_from_block1", "alexandrite");*/
        oreSmelting(output, ALEXANDRITE_SMELTABLES, RecipeCategory.MISC, ModItems.ALEXANDRITE.get(), 0.25f, 200, "alexandrite");
        oreBlasting(output, ALEXANDRITE_SMELTABLES, RecipeCategory.MISC, ModItems.ALEXANDRITE.get(), 0.25f, 100, "alexandrite");
    }
}
