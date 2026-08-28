package net.jason.mccourse.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jason.mccourse.MCCourseMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

public class GemEmpoweringRecipe implements Recipe<GemEmpoweringRecipeInput> {

    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final int craftTime;
    private final int energyAmount;
    private final FluidStack fluidStack;

    public GemEmpoweringRecipe(NonNullList<Ingredient> inputItems, ItemStack output, int craftTime, int energyAmount, FluidStack fluidStack) {
        this.inputItems = inputItems;
        this.output = output;
        this.craftTime = craftTime;
        this.energyAmount = energyAmount;
        this.fluidStack = fluidStack;
    }

    @Override
    public boolean matches(GemEmpoweringRecipeInput pInput, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }

        return inputItems.get(0).test(pInput.getItem(0));
    }

    public int getCraftTime() {
        return craftTime;
    }

    public int getEnergyAmount() {
        return energyAmount;
    }

    public FluidStack getFluidStack() {
        return fluidStack;
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
                                        ingredients -> ingredients)
                                .forGetter(recipe -> recipe.inputItems),

                        ItemStack.CODEC.fieldOf("output").forGetter(recipe -> recipe.output),

                        Codec.INT.fieldOf("craftTime").forGetter(recipe -> recipe.craftTime),

                        Codec.INT.fieldOf("energyAmount").forGetter(recipe -> recipe.energyAmount),

                        BuiltInRegistries.FLUID.byNameCodec().fieldOf("fluidType")
                                .forGetter(recipe -> recipe.fluidStack.getFluid()),

                        Codec.INT.fieldOf("fluidAmount")
                                .forGetter(recipe -> recipe.fluidStack.getAmount())
                ).apply(instance,
                        (ingredients, output, craftTime, energyAmount, fluid, fluidAmount) ->
                                new GemEmpoweringRecipe(ingredients, output, craftTime, energyAmount, new FluidStack(fluid, fluidAmount))));

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

            int craftTime = buffer.readInt();
            int energyAmount = buffer.readInt();

            ResourceLocation fluidId = buffer.readResourceLocation();

            int fluidAmount = buffer.readInt();

            FluidStack fluidStack = new FluidStack(BuiltInRegistries.FLUID.get(fluidId), fluidAmount);

            return new GemEmpoweringRecipe(inputs, output, craftTime, energyAmount, fluidStack);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, GemEmpoweringRecipe recipe) {
            buffer.writeVarInt(recipe.inputItems.size());

            for (Ingredient ingredient : recipe.inputItems) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }

            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);

            buffer.writeInt(recipe.craftTime);
            buffer.writeInt(recipe.energyAmount);

            buffer.writeResourceLocation(BuiltInRegistries.FLUID.getKey(recipe.fluidStack.getFluid()));
            buffer.writeInt(recipe.fluidStack.getAmount());
        }

    }
}
