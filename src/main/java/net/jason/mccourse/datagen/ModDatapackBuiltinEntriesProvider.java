package net.jason.mccourse.datagen;

import net.jason.mccourse.MCCourseMod;
import net.jason.mccourse.enchantment.ModEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDatapackBuiltinEntriesProvider
        extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER =
            new RegistrySetBuilder()
                    .add(
                            Registries.ENCHANTMENT,
                            ModEnchantments::bootstrap
                    );

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