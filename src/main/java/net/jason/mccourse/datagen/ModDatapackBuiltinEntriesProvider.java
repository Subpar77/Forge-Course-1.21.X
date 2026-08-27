package net.jason.mccourse.datagen;

import net.jason.mccourse.MCCourseMod;
import net.jason.mccourse.enchantment.ModEnchantments;
import net.jason.mccourse.worldgen.ModBiomeModifiers;
import net.jason.mccourse.worldgen.ModConfiguredFeatures;
import net.jason.mccourse.worldgen.ModPlacedFeatures;
import net.jason.mccourse.worldgen.biome.ModBiomes;
import net.jason.mccourse.worldgen.dimension.ModDimensions;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDatapackBuiltinEntriesProvider
        extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER =
            new RegistrySetBuilder()
                    .add(
                            Registries.ENCHANTMENT,
                            ModEnchantments::bootstrap)
                    .add(Registries.CONFIGURED_FEATURE,
                            ModConfiguredFeatures::bootstrap)
                    .add(Registries.PLACED_FEATURE,
                            ModPlacedFeatures::bootstrap)
                    .add(Registries.BIOME,
                            ModBiomes::bootstrap)
                    .add(ForgeRegistries.Keys.BIOME_MODIFIERS,
                            ModBiomeModifiers::bootstrap)
                    .add(Registries.DIMENSION_TYPE,
                            ModDimensions::bootstrapType)
                    .add(Registries.LEVEL_STEM,
                            ModDimensions::bootstrapStem);

    public ModDatapackBuiltinEntriesProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> registries
    ) {
        super(
                output,
                registries,
                BUILDER,
                Set.of(MCCourseMod.MOD_ID)
        );
    }
}