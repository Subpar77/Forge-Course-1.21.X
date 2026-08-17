package net.jason.mccourse.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jason.mccourse.MCCourseMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.checkerframework.checker.units.qual.C;

public class GemEmpoweringRecipe implements Recipe<GemEmpoweringRecipeInput> {

    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;

    public GemEmpoweringRecipe(NonNullList<Ingredient> inputItems, ItemStack output) {
        this.inputItems = inputItems;
        this.output = output;
        //this.id = id;
    }

    @Override
    public boolean matches(GemEmpoweringRecipeInput pInput, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        return inputItems.get(0).test(pInput.getItem(0));
    }

    @Override
    public ItemStack assemble(GemEmpoweringRecipeInput pInput, HolderLookup.Provider pRegistries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return output.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputItems;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.GEM_EMPOWERING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.GEM_EMPOWERING_TYPE.get();
    }

    public static class Type implements RecipeType<GemEmpoweringRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();
        public static final String ID = "gem_empowering";
    }

    public static class Serializer implements RecipeSerializer<GemEmpoweringRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "gem_empowering");

        private static final MapCodec<GemEmpoweringRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Ingredient.CODEC.listOf().fieldOf("ingredients").xmap(list -> {
                                    NonNullList<Ingredient> ingredients = NonNullList.create();
                                    ingredients.addAll(list);
                                    return ingredients;
                                },
                                ingredients -> ingredients).forGetter(recipe -> recipe.inputItems),
                        ItemStack.CODEC.fieldOf("output").forGetter(recipe -> recipe.output))
                .apply(instance, ((ingredients, output) -> new GemEmpoweringRecipe(ingredients, output))));

        private static StreamCodec<RegistryFriendlyByteBuf, GemEmpoweringRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<GemEmpoweringRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, GemEmpoweringRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static GemEmpoweringRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            int ingredientCount = buffer.readVarInt();
            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredientCount, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            }

            ItemStack output = ItemStack.STREAM_CODEC.decode(buffer);

            return new GemEmpoweringRecipe(inputs, output);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, GemEmpoweringRecipe recipe) {
            buffer.writeVarInt(recipe.inputItems.size());

            for (Ingredient ingredient : recipe.inputItems) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }

            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
        }

    }
}
