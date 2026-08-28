package net.jason.mccourse.block.entity;

import net.jason.mccourse.block.custom.GemEmpowerStationBlock;
import net.jason.mccourse.item.ModItems;
import net.jason.mccourse.recipe.GemEmpoweringRecipe;
import net.jason.mccourse.recipe.GemEmpoweringRecipeInput;
import net.jason.mccourse.recipe.ModRecipes;
import net.jason.mccourse.screen.GemEmpoweringStationMenu;
import net.jason.mccourse.util.InventoryDirectionEntry;
import net.jason.mccourse.util.InventoryDirectionWrapper;
import net.jason.mccourse.util.ModEnergyStorage;
import net.jason.mccourse.util.WrappedHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class GemEmpoweringStationBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> true;
                case 1 -> hasFluid(stack);
                case 2 -> false;
                case 3 -> stack.getItem() == ModItems.KOHLRABI.get();
                    default -> super.isItemValid(slot, stack);
            };
        }
    };

    private boolean hasFluid(@NotNull ItemStack stack) {
        if(stack.getItem() instanceof BucketItem bucketItem) {
            return bucketItem.getFluid() != Fluids.EMPTY;
        }

        return FluidUtil.getFluidContained(stack).isPresent();
    }

    private static final int INPUT_SLOT = 0;
    private static final int FLUID_INPUT_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;
    private static final int ENERGY_ITEM_SLOT = 3;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            new InventoryDirectionWrapper(itemHandler,
                    new InventoryDirectionEntry(Direction.DOWN, OUTPUT_SLOT, false),
                    new InventoryDirectionEntry(Direction.NORTH, INPUT_SLOT, true),
                    new InventoryDirectionEntry(Direction.SOUTH, OUTPUT_SLOT, false),
                    new InventoryDirectionEntry(Direction.EAST, OUTPUT_SLOT, false),
                    new InventoryDirectionEntry(Direction.WEST, INPUT_SLOT, true),
                    new InventoryDirectionEntry(Direction.UP, INPUT_SLOT, true)).directionsMap;

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;
    private final int DEFAULT_MAX_PROGRESS = 78;

    private int energyAmount = 0;
    private final int DEFAULT_ENERGY_AMOUNT = 100;

    private FluidStack neededFluidStack = FluidStack.EMPTY;


    private final ModEnergyStorage ENERGY_STORAGE = createEnergyStorage();
    private final FluidTank FLUID_TANK = createFluidTank();

    private FluidTank createFluidTank() {
        return new FluidTank(64000){
            @Override
            protected void onContentsChanged() {
                setChanged();
                if(!level.isClientSide()) {
                    level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return true;
            }
        };
    }

    private ModEnergyStorage createEnergyStorage() {
        return new ModEnergyStorage(64000, 200) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        };
    }

    public ItemStack getRenderStack() {
        ItemStack stack = itemHandler.getStackInSlot(OUTPUT_SLOT);

        if(stack.isEmpty()) {
            stack = itemHandler.getStackInSlot(INPUT_SLOT);
        }

        return stack;
    }


    public GemEmpoweringStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.GEM_EMPOWERINGSTATION_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> GemEmpoweringStationBlockEntity.this.progress;
                    case 1 -> GemEmpoweringStationBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> GemEmpoweringStationBlockEntity.this.progress = pValue;
                    case 1 -> GemEmpoweringStationBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public IEnergyStorage getEnergyStorage () {
        return this.ENERGY_STORAGE;
    }

    public FluidStack getFluid() {
        return FLUID_TANK.getFluid();
    }

        public void drops () {
            SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
            for (int i = 0; i < itemHandler.getSlots(); i++) {
                inventory.setItem(i, itemHandler.getStackInSlot(i));
            }

            Containers.dropContents(this.level, this.worldPosition, inventory);
        }

        @Override
        public Component getDisplayName () {
            return Component.literal("Gem Empowering Station");
        }

        @Override
        public @Nullable AbstractContainerMenu createMenu ( int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
            return new GemEmpoweringStationMenu(pContainerId, pPlayerInventory, this, this.data);
        }

        @Override
        public @NotNull <T > LazyOptional < T > getCapability(@NotNull Capability < T > cap, @Nullable Direction side) {
            if(cap == ForgeCapabilities.ENERGY) {
                return lazyEnergyHandler.cast();
            }

            if(cap == ForgeCapabilities.FLUID_HANDLER) {
                return lazyFluidHandler.cast();
            }

            if (cap == ForgeCapabilities.ITEM_HANDLER) {
                if (side == null) {
                    return lazyItemHandler.cast();
                }

                if (directionWrappedHandlerMap.containsKey(side)) {
                    Direction localDir = this.getBlockState().getValue(GemEmpowerStationBlock.FACING);

                    if (side == Direction.DOWN || side == Direction.UP) {
                        return directionWrappedHandlerMap.get(side).cast();
                    }

                    return switch (localDir) {
                        default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                        case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                        case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                        case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                    };

                }


            }

            return super.getCapability(cap, side);
        }

        @Override
        public void onLoad () {
            super.onLoad();
            lazyItemHandler = LazyOptional.of(() -> itemHandler);
            lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
            lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
        }

        @Override
        public void invalidateCaps () {
            super.invalidateCaps();
            lazyItemHandler.invalidate();
            lazyEnergyHandler.invalidate();
            lazyFluidHandler.invalidate();
        }

        @Override
        protected void saveAdditional (CompoundTag pTag, HolderLookup.Provider pRegistries){
            pTag.put("inventory", itemHandler.serializeNBT(pRegistries));
            pTag.putInt("gem_empowering_station.progress", progress);
            pTag.putInt("gem_empowering_station.max_progress", maxProgress);
            pTag.putInt("gem_empowering_station.energy_amount", energyAmount);

            pTag.putInt("energy", ENERGY_STORAGE.getEnergyStored());
            pTag = FLUID_TANK.writeToNBT(pTag);


            super.saveAdditional(pTag, pRegistries);
        }

        @Override
        public void loadAdditional (CompoundTag pTag, HolderLookup.Provider pRegistries){
            super.loadAdditional(pTag, pRegistries);
            itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
            progress = pTag.getInt("gem_empowering_station.progress");
            maxProgress = pTag.getInt("gem_empowering_station.max_progress");
            energyAmount = pTag.getInt("gem_empowering_station.energy_amount");
            ENERGY_STORAGE.setEnergy(pTag.getInt("energy"));
            FLUID_TANK.readFromNBT(pTag);
        }

        public void tick (Level level, BlockPos pPos, BlockState pState){
            fillUpOnEnergy();
            fillUpOnFluid();

            if (isOutputSlotEmptyOrReceivable() && hasRecipe()) {
                increaseCraftingProcess();
                extractEnergy();
                setChanged(level, pPos, pState);

                if (hasProgressFinished()) {
                    craftItem();
                    extractFluid();
                    resetProgress();
                }
            } else {
                resetProgress();
            }

        }

    private void extractFluid() {
        this.FLUID_TANK.drain(neededFluidStack.getAmount(), IFluidHandler.FluidAction.EXECUTE);
    }

    private void fillUpOnFluid() {
        if(hasFluidSourceInSlot(FLUID_INPUT_SLOT)) {
            transferItemFluidToTank(FLUID_INPUT_SLOT);
        }
    }

    private void transferItemFluidToTank(int fluidInputSlot) {
        ItemStack containerStack = this.itemHandler.getStackInSlot(fluidInputSlot);

        if(containerStack.isEmpty()) {
            return;
        }

        // Normal bucket handling
        if(containerStack.getItem() instanceof BucketItem bucketItem) {
            var fluid = bucketItem.getFluid();

            if (fluid == Fluids.EMPTY) {
                return;
            }

            FluidStack bucketFluid = new FluidStack(fluid, 1000);

            int fillAmount = this.FLUID_TANK.fill(bucketFluid, IFluidHandler.FluidAction.SIMULATE);

            if(fillAmount >= 1000) {
                this.FLUID_TANK.fill(bucketFluid, IFluidHandler.FluidAction.EXECUTE);

                this.itemHandler.setStackInSlot(fluidInputSlot, new ItemStack(Items.BUCKET));
            }

            return;
        }

        // Other Forge fluid containers
        FluidUtil.getFluidHandler(containerStack).ifPresent(fluidHandler -> {
        int drainAmount = Math.min(this.FLUID_TANK.getSpace(),1000);

        FluidStack simulatedDrain = fluidHandler.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);

        if(!simulatedDrain.isEmpty()) {
            int fillAmount = this.FLUID_TANK.fill(simulatedDrain, IFluidHandler.FluidAction.SIMULATE);

        if(fillAmount > 0) {
            FluidStack drainedFluid = fluidHandler.drain(fillAmount, IFluidHandler.FluidAction.EXECUTE);

        if(!drainedFluid.isEmpty()) {
            this.FLUID_TANK.fill(drainedFluid, IFluidHandler.FluidAction.EXECUTE);
        }

        this.itemHandler.setStackInSlot(fluidInputSlot, fluidHandler.getContainer()
        );
        }
        }
    });
}

//    private void fillTankWithFluid(FluidStack stack, ItemStack container) {
//        this.FLUID_TANK.fill(new FluidStack(stack.getFluid(), stack.getAmount()), IFluidHandler.FluidAction.EXECUTE);
//
//        this.itemHandler.extractItem(FLUID_INPUT_SLOT, 1, false);
//        this.itemHandler.insertItem(FLUID_INPUT_SLOT, container, false);
//    }

    private boolean hasFluidSourceInSlot(int fluidInputSlot) {
        ItemStack stack = this.itemHandler.getStackInSlot(fluidInputSlot);

        return !stack.isEmpty() && hasFluid(stack);
    }

    private void extractEnergy() {
        this.ENERGY_STORAGE.extractEnergy(energyAmount, false);
    }

    private void fillUpOnEnergy() {
        if(hasEnergyInSlot(ENERGY_ITEM_SLOT)) {
            this.ENERGY_STORAGE.receiveEnergy(3200, false);
        }
    }

    private boolean hasEnergyInSlot(int energyItemSlot) {
        return !this.itemHandler.getStackInSlot(energyItemSlot).isEmpty() &&
                this.itemHandler.getStackInSlot(energyItemSlot).is(ModItems.KOHLRABI.get());
    }


    private void craftItem () {
            Optional<RecipeHolder<GemEmpoweringRecipe>> recipe = getCurrentRecipe();
            ItemStack resultItem = recipe.get().value().getResultItem(getLevel().registryAccess());

            this.itemHandler.extractItem(INPUT_SLOT, 1, false);

            this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(resultItem.getItem(),
                    this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + resultItem.getCount()));
        }

        private void resetProgress () {
            this.progress = 0;
            this.maxProgress = DEFAULT_MAX_PROGRESS;
            this.energyAmount = DEFAULT_ENERGY_AMOUNT;
            this.neededFluidStack = FluidStack.EMPTY;
        }

        private boolean hasProgressFinished () {
            return this.progress >= this.maxProgress;
        }

        private void increaseCraftingProcess () {
            this.progress++;
        }

        private boolean hasRecipe () {
            Optional<RecipeHolder<GemEmpoweringRecipe>> recipe = getCurrentRecipe();


            if (recipe.isEmpty()) {
                return false;
            }

            GemEmpoweringRecipe currentRecipe = recipe.get().value();

            ItemStack resultItem = currentRecipe.getResultItem(getLevel().registryAccess());

            // Get the machine requirements from THIS recipe
            this.maxProgress = currentRecipe.getCraftTime();
            this.energyAmount = currentRecipe.getEnergyAmount();
            this.neededFluidStack = currentRecipe.getFluidStack();

            return canInsertAmountIntoOutputSlot(resultItem.getCount())
                    && canInsertItemIntoOutputSlot(resultItem.getItem())
                    && hasEnoughEnergyToCraft()
                    && hasEnoughFluidToCraft();
        }

    private boolean hasEnoughFluidToCraft() {
        FluidStack tankFluid = this.FLUID_TANK.getFluid();

        if (neededFluidStack.isEmpty() || tankFluid.isEmpty()) {
            return false;
        }

        return tankFluid.getFluid() == neededFluidStack.getFluid() &&
                tankFluid.getAmount() >= neededFluidStack.getAmount();
    }

    private boolean hasEnoughEnergyToCraft() {
        return this.ENERGY_STORAGE.getEnergyStored() >= energyAmount;
    }

    private Optional<RecipeHolder<GemEmpoweringRecipe>> getCurrentRecipe () {
            GemEmpoweringRecipeInput input = new GemEmpoweringRecipeInput(this.itemHandler.getStackInSlot(INPUT_SLOT));

            return this.level.getRecipeManager().getRecipeFor(ModRecipes.GEM_EMPOWERING_TYPE.get(), input, level);
        }

        private boolean canInsertItemIntoOutputSlot (@NotNull Item item){
            return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
        }

        private boolean canInsertAmountIntoOutputSlot ( int count){
            ItemStack outputStack = this.itemHandler.getStackInSlot(OUTPUT_SLOT);

            return outputStack.isEmpty() || outputStack.getMaxStackSize() >= outputStack.getCount() + count;
        }

        private boolean isOutputSlotEmptyOrReceivable () {
            return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                    this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() < this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
        }

        @Nullable
        @Override
        public ClientboundBlockEntityDataPacket getUpdatePacket () {
            return ClientboundBlockEntityDataPacket.create(this);
        }

        @Override
        public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
            return saveWithoutMetadata(pRegistries);
        }

        @Override
        public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookup) {
            super.onDataPacket(net, pkt, lookup);
        }


}