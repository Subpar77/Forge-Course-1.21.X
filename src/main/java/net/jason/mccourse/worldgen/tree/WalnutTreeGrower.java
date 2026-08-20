package net.jason.mccourse.worldgen.tree;

import net.jason.mccourse.worldgen.ModConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class WalnutTreeGrower {
    public static final TreeGrower WALNUT = new TreeGrower("walnut", Optional.empty(), Optional.of(ModConfiguredFeatures.WALNUT_KEY),
            Optional.empty());
    }
