package net.jason.mccourse;

import com.mojang.logging.LogUtils;
import net.jason.mccourse.block.ModBlocks;
import net.jason.mccourse.block.entity.ModBlockEntities;
import net.jason.mccourse.effect.ModEffects;
import net.jason.mccourse.entity.ModEntities;
import net.jason.mccourse.entity.client.RhinoRenderer;
import net.jason.mccourse.fluid.ModFluidTypes;
import net.jason.mccourse.fluid.ModFluids;
import net.jason.mccourse.item.ModArmorMaterials;
import net.jason.mccourse.item.ModCreativeModeTabs;
import net.jason.mccourse.item.ModItemProperties;
import net.jason.mccourse.item.ModItems;
import net.jason.mccourse.loot.ModLootModifiers;
import net.jason.mccourse.particle.ModParticles;
import net.jason.mccourse.potion.BetterBrewingRecipe;
import net.jason.mccourse.potion.ModPotions;
import net.jason.mccourse.recipe.ModRecipes;
import net.jason.mccourse.screen.GemEmpoweringStationScreen;
import net.jason.mccourse.screen.ModMenuTypes;
import net.jason.mccourse.sound.ModSounds;
import net.jason.mccourse.util.ModWoodTypes;
import net.jason.mccourse.villager.ModVillagers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.brewing.BrewingRecipeRegisterEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(MCCourseMod.MOD_ID)
public class MCCourseMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "mccourse";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public MCCourseMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModArmorMaterials.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        ModSounds.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);
        ModVillagers.register(modEventBus);
        ModParticles.register(modEventBus);
        ModFluidTypes.register(modEventBus);
        ModFluids.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModEntities.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
        event.enqueueWork(() -> {
            ComposterBlock.COMPOSTABLES.put(ModItems.KOHLRABI.get(), 0.35f);
            ComposterBlock.COMPOSTABLES.put(ModItems.KOHLRABI_SEEDS.get(), 0.20f);

            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.SNAPDRAGON.getId(), ModBlocks.POTTED_SNAPDRAGON);


        });

    }

    @SubscribeEvent
    public void onBrewingRecipeRegister(BrewingRecipeRegisterEvent event) {
        event.getBuilder().add(new BetterBrewingRecipe(Potions.AWKWARD, Items.SLIME_BALL, ModPotions.SLIMEY_POTION.getHolder().get()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.ALEXANDRITE);
            event.accept(ModItems.RAW_ALEXANDRITE);
        }

        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.ALEXANDRITE_BLOCK);
            event.accept(ModBlocks.ALEXANDRITE_ORE);
            event.accept(ModBlocks.RAW_ALEXANDRITE_BLOCK);
            event.accept(ModBlocks.DEEPSLATE_ALEXANDRITE_ORE);
            event.accept(ModBlocks.END_STONE_ALEXANDRITE_ORE);
            event.accept(ModBlocks.NETHER_ALEXANDRITE_ORE);
            event.accept(ModBlocks.SOUND_BLOCK);
            event.accept(ModBlocks.ALEXANDRITE_STAIRS);
            event.accept(ModBlocks.ALEXANDRITE_SLABS);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                Sheets.addWoodType(ModWoodTypes.WALNUT);

                ModItemProperties.addCustomItemProperties();

                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_SOAP_WATER.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_SOAP_WATER.get(), RenderType.translucent());

                MenuScreens.register(ModMenuTypes.GEM_EMPOWERING_MENU.get(), GemEmpoweringStationScreen::new);

                EntityRenderers.register(ModEntities.RHINO.get(), RhinoRenderer::new);
            });
        }
    }
}
