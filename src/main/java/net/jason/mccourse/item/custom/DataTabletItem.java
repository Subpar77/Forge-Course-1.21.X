package net.jason.mccourse.item.custom;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import java.util.List;

public class DataTabletItem extends Item {
    public DataTabletItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);

        if(!pLevel.isClientSide()) {
            CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.remove("mccourse.found_ore"));

            CustomData remainingData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);

            if(remainingData.isEmpty()) {
                stack.remove(DataComponents.CUSTOM_DATA);
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, pLevel.isClientSide());
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        CustomData customData = pStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        return !customData.isEmpty() || super.isFoil(pStack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        CustomData customData = pStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);

        if(customData.contains("mccourse.found_ore")) {
            String currentFoundOre = customData.copyTag().getString("mccourse.found_ore");
            pTooltipComponents.add(Component.literal(currentFoundOre));
        }

        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
