package net.jason.mccourse.block;

import com.mojang.blaze3d.shaders.Uniform;
import net.jason.mccourse.MCCourseMod;
import net.jason.mccourse.block.custom.AlexandriteLampBlock;
import net.jason.mccourse.block.custom.GemEmpowerStationBlock;
import net.jason.mccourse.block.custom.KohlrabiCropBlock;
import net.jason.mccourse.block.custom.SoundBlock;
import net.jason.mccourse.fluid.ModFluids;
import net.jason.mccourse.item.ModItems;
import net.jason.mccourse.sound.ModSounds;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MCCourseMod.MOD_ID);

    public static final RegistryObject<Block> ALEXANDRITE_BLOCK = registerBlock("alexandrite_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));
    public static final RegistryObject<Block> RAW_ALEXANDRITE_BLOCK = registerBlock("raw_alexandrite_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                    .requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));

    public static final RegistryObject<Block> ALEXANDRITE_ORE = registerBlock("alexandrite_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(2,5),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(5.0F, 6.0F)
                    .sound(SoundType.METAL)));
    public static final RegistryObject<Block> DEEPSLATE_ALEXANDRITE_ORE = registerBlock("deepslate_alexandrite_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(3,7),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE)
                    .requiresCorrectToolForDrops()
                    .strength(5.0F, 6.0F)
                    .sound(SoundType.METAL)));
    public static final RegistryObject<Block> END_STONE_ALEXANDRITE_ORE = registerBlock("end_stone_alexandrite_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(5,8),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE)
                    .requiresCorrectToolForDrops()
                    .strength(5.0F, 6.0F)
                    .sound(SoundType.METAL)));
    public static final RegistryObject<Block> NETHER_ALEXANDRITE_ORE = registerBlock("nether_alexandrite_ore",
            () -> new DropExperienceBlock(
                    UniformInt.of(3,6),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERRACK)
                    .requiresCorrectToolForDrops()
                    .strength(5.0F, 6.0F)
                    .sound(SoundType.METAL)));

    public static final RegistryObject<Block> SOUND_BLOCK = registerBlock("sound_block",
            () -> new SoundBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> ALEXANDRITE_STAIRS = registerBlock("alexandrite_stairs",
            () -> new StairBlock(ModBlocks.ALEXANDRITE_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.GRANITE_STAIRS).sound(SoundType.METAL)));
    public static final RegistryObject<Block> ALEXANDRITE_SLABS = registerBlock("alexandrite_slabs",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRANITE_SLAB).sound(SoundType.METAL)));

    public static final RegistryObject<Block> ALEXANDRITE_PRESSURE_PLATE = registerBlock("alexandrite_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.IRON, BlockBehaviour.Properties.ofFullCopy(Blocks.GRANITE_STAIRS).noCollission().strength(0.5f)
                    .sound(SoundType.METAL)));
    public static final RegistryObject<Block> ALEXANDRITE_BUTTON = registerBlock("alexandrite_button",
            () -> new ButtonBlock(BlockSetType.IRON,10, BlockBehaviour.Properties.ofFullCopy(Blocks.GRANITE_SLAB).sound(SoundType.METAL)));


    public static final RegistryObject<Block> ALEXANDRITE_FENCE = registerBlock("alexandrite_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ALEXANDRITE_FENCE_GATE = registerBlock("alexandrite_fence_gate",
            () -> new FenceGateBlock(WoodType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE));
    public static final RegistryObject<Block> ALEXANDRITE_WALL = registerBlock("alexandrite_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> ALEXANDRITE_DOOR = registerBlock("alexandrite_door",
            () -> new DoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ALEXANDRITE_TRAPDOOR = registerBlock("alexandrite_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.IRON, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> ALEXANDRITE_LAMP = registerBlock("alexandrite_lamp",
            () -> new AlexandriteLampBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).sound(ModSounds.ALEXANDRITE_LAMP_SOUNDS)
                    .strength(1f)
                    .lightLevel(state -> state.getValue(AlexandriteLampBlock.CLICKED) ? 15 : 0)));

    public static final RegistryObject<Block> KOHLRABI_CROP = BLOCKS.register("kholrabi_crop",
            () -> new KohlrabiCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).noCollission().noOcclusion()));

    public static final RegistryObject<Block> SNAPDRAGON = registerBlock("snapdragon",
            () -> new FlowerBlock(MobEffects.BLINDNESS, 6, BlockBehaviour.Properties.ofFullCopy(Blocks.ALLIUM)));
    public static final RegistryObject<Block> POTTED_SNAPDRAGON = BLOCKS.register("potted_snapdragon",
            () -> new FlowerPotBlock((() -> (FlowerPotBlock) Blocks.FLOWER_POT), SNAPDRAGON, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_ALLIUM)));

    public static final RegistryObject<Block> GEM_EMPOWERING_STATION = registerBlock("gem_empowering_station",
            () -> new GemEmpowerStationBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));

    public static final RegistryObject<LiquidBlock> SOAP_WATER_BLOCK = BLOCKS.register("soap_water_block",
            () -> new LiquidBlock(ModFluids.SOURCE_SOAP_WATER, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static  <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
