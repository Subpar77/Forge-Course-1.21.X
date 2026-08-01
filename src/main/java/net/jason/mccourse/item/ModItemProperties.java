package net.jason.mccourse.item;

import net.jason.mccourse.MCCourseMod;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.component.CustomData;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.DATA_TABLET.get(), ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "on"),
                (pStack, pLevel, pEntity, pSeed) ->
                {CustomData customData = pStack.getOrDefault(DataComponents.CUSTOM_DATA,CustomData.EMPTY);

                    return customData.contains("mccourse.found_ore") ? 1.0f : 0.0f;});

    }


}
