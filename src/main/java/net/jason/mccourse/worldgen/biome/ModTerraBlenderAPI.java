package net.jason.mccourse.worldgen.biome;

import net.jason.mccourse.MCCourseMod;
import net.jason.mccourse.worldgen.biome.custom.ModOverworldRegion;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

public class ModTerraBlenderAPI {
    public static void registerRegions() {
        Regions.register(new ModOverworldRegion(ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "overworld"), 5));
    }
}
