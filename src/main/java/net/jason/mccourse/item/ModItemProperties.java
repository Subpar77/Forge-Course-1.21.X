package net.jason.mccourse.item;

import net.jason.mccourse.MCCourseMod;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.DATA_TABLET.get(), ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "on"),
                (pStack, pLevel, pEntity, pSeed) ->
                {CustomData customData = pStack.getOrDefault(DataComponents.CUSTOM_DATA,CustomData.EMPTY);

                    return customData.contains("mccourse.found_ore") ? 1.0f : 0.0f;});

        ItemProperties.register(ModItems.ALEXANDRITE_SHIELD.get(), ResourceLocation.withDefaultNamespace( "blocking"),
                (p_174575_, p_174576_, p_174577_, p_174578_) -> p_174577_ != null && p_174577_.isUsingItem() && p_174577_.getUseItem() == p_174575_ ? 1.0F : 0.0F
        );

        makeBow(ModItems.ALEXANDRITE_BOW.get());
    }

    private static void makeBow(Item item){
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pull"), (p_340951_, p_340952_, p_340953_, p_340954_) -> {
            if (p_340953_ == null) {
                return 0.0F;
            } else {
                return p_340953_.getUseItem() != p_340951_ ? 0.0F : (float)(p_340951_.getUseDuration(p_340953_) - p_340953_.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pulling"),
                (p_174630_, p_174631_, p_174632_, p_174633_) -> p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F
        );
    }


}
