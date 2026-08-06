package net.jason.mccourse.painting;

import net.jason.mccourse.MCCourseMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPaintings {
    public static final ResourceKey<PaintingVariant> SAW_THEM = createKey("saw_them");
    public static final ResourceKey<PaintingVariant> SHRIMP = createKey("shrimp");
    public static final ResourceKey<PaintingVariant> WORLD = createKey("world");

    private static ResourceKey<PaintingVariant> createKey(String name) {
        return ResourceKey.create(Registries.PAINTING_VARIANT, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, name));
    }


    private ModPaintings() {
    }
}
