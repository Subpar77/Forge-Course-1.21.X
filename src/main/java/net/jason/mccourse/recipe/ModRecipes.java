package net.jason.mccourse.recipe;

import net.jason.mccourse.MCCourseMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MCCourseMod.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MCCourseMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<GemEmpoweringRecipe>> GEM_EMPOWERING_SERIALIZER =
            SERIALIZERS.register("gem_empowering", () -> GemEmpoweringRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<GemEmpoweringRecipe>> GEM_EMPOWERING_TYPE =
            RECIPE_TYPES.register("gem_empowering", () -> GemEmpoweringRecipe.Type.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }


}
