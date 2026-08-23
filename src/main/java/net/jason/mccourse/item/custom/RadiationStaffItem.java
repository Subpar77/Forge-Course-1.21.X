package net.jason.mccourse.item.custom;

import net.jason.mccourse.entity.custom.MagicProjectileEntity;
import net.jason.mccourse.sound.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RadiationStaffItem extends Item {
    public RadiationStaffItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), ModSounds.METAL_DETECTOR_FOUND_ORE.get(), SoundSource.NEUTRAL,
                1.5f, 1f);
        pPlayer.getCooldowns().addCooldown(this, 40);

        if(!pLevel.isClientSide()) {
            MagicProjectileEntity magicProjectile = new MagicProjectileEntity(pLevel, pPlayer);
            magicProjectile.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0f, 1.5f, 0.25f);
            pLevel.addFreshEntity(magicProjectile);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemStack.hurtAndBreak(1, pPlayer, pUsedHand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
        }

        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }
}
