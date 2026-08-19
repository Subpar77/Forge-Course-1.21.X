package net.jason.mccourse.enchantment;

import net.jason.mccourse.MCCourseMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> LIGHTNING_STRIKER = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "lightning_striker"));

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);

        context.register(LIGHTNING_STRIKER, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.SWORD_ENCHANTABLE), 10, 2, Enchantment.dynamicCost(1, 10),
                Enchantment.dynamicCost(20, 10), 4, EquipmentSlotGroup.MAINHAND))
                .build(LIGHTNING_STRIKER.location())
        );
    }
}
