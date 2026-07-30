package net.jason.mccourse.item.custom;

import net.jason.mccourse.item.ModArmorMaterials;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.function.Supplier;

public class ModArmorItem extends ArmorItem {
    private static final Map<ResourceKey<ArmorMaterial>, Supplier<MobEffectInstance>> MATERIAL_TO_EFFECT_MAP =
            Map.of(ModArmorMaterials.ALEXANDRITE.getKey(),
                    ()-> new MobEffectInstance(MobEffects.JUMP, 200, 1));


    public ModArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        Player player = (entity instanceof Player) ? (Player) entity : null;
        if(!level.isClientSide() && hasFullSuitOfArmorOn(player)) {
            evaluateArmorEffects(player);
        }
    }

    private void evaluateArmorEffects(Player player) {
        for(Map.Entry<ResourceKey<ArmorMaterial>, Supplier<MobEffectInstance>> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            ResourceKey<ArmorMaterial> mapArmorMaterial = entry.getKey();
            MobEffectInstance mapEffect = entry.getValue().get();

            if(hasPlayerCorrectArmorOn(mapArmorMaterial, player)) {
                addEffectToPlayer(player, mapEffect);
            }
        }
    }

    private void addEffectToPlayer(Player player, MobEffectInstance mapEffect) {
        boolean hasPlayerEffect = player.hasEffect(mapEffect.getEffect());

        if(!hasPlayerEffect) {
            player.addEffect(new MobEffectInstance(mapEffect.getEffect(), mapEffect.getDuration(),
                    mapEffect.getAmplifier()));
        }
    }

    private boolean hasPlayerCorrectArmorOn(ResourceKey<ArmorMaterial> mapArmorMaterial, Player player) {
        for(ItemStack armorStack : player.getArmorSlots()) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem boots = ((ArmorItem) player.getInventory().getArmor(0).getItem());
        ArmorItem leggings = ((ArmorItem) player.getInventory().getArmor(1).getItem());
        ArmorItem chestplate = ((ArmorItem) player.getInventory().getArmor(2).getItem());
        ArmorItem helmet = ((ArmorItem) player.getInventory().getArmor(3).getItem());

        return boots.getMaterial().is(mapArmorMaterial) && leggings.getMaterial().is(mapArmorMaterial)
                && chestplate.getMaterial().is(mapArmorMaterial) && helmet.getMaterial().is(mapArmorMaterial);

    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        ItemStack boots = player.getInventory().getArmor(0);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack chestplate = player.getInventory().getArmor(2);
        ItemStack helmet = player.getInventory().getArmor(3);

        return !boots.isEmpty() && !leggings.isEmpty() && !chestplate.isEmpty() && !helmet.isEmpty();
    }

}

