package net.jason.mccourse.datagen;

import net.jason.mccourse.MCCourseMod;
import net.jason.mccourse.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.Optional;
import java.util.function.Consumer;

public class ModAdvancementProvider implements ForgeAdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
        AdvancementHolder rootAdvancement = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(ModItems.ALEXANDRITE.get()),
                        Component.literal("MC Course"), Component.literal("The Power lies in the Alexandrite!"),
                        Optional.of(ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "textures/block/alexandrite_ore.png")), AdvancementType.TASK,
                        true, true, false))
                .addCriterion("has_alexandrite", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ALEXANDRITE.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "mccourse"));


        AdvancementHolder metalDetector = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(ModItems.METAL_DETECTOR.get()),
                        Component.literal("Metal Detector"), Component.literal("Batteries not included! (Actually doesn't need batteries)"),
                        Optional.empty(), AdvancementType.TASK,
                        true, true, false))
                .parent(rootAdvancement)
                .addCriterion("has_metal_detector", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.METAL_DETECTOR.get()))
                .save(saver, ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "metal_detector"));

    }


}
