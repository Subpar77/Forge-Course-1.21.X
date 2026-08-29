package net.jason.mccourse.screen;

import net.jason.mccourse.MCCourseMod;
import net.jason.mccourse.recipe.KaupenFurnaceRecipeBookComponent;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class KaupenFurnaceScreen extends AbstractFurnaceScreen<KaupenFurnaceMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "textures/gui/kaupen_furnace.png");
    private static final ResourceLocation LIT_TEXTURE = ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "container/kaupen_furnace/lit_progress");
    private static final ResourceLocation BURN_TEXTURE = ResourceLocation.fromNamespaceAndPath(MCCourseMod.MOD_ID, "container/kaupen_furnace/burn_progress");

    public KaupenFurnaceScreen(KaupenFurnaceMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, new KaupenFurnaceRecipeBookComponent(), pPlayerInventory, pTitle, TEXTURE, LIT_TEXTURE, BURN_TEXTURE);
    }
}
