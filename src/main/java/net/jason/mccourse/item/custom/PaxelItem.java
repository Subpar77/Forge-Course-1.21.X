package net.jason.mccourse.item.custom;

import net.jason.mccourse.util.ModTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;

public class PaxelItem extends DiggerItem {


    public PaxelItem(Tier pTier, Item.Properties pProperties) {
        super(pTier, ModTags.Blocks.PAXEL_MINEABLE, pProperties);
    }
}
