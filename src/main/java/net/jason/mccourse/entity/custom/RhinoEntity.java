package net.jason.mccourse.entity.custom;

import net.jason.mccourse.entity.ModEntities;
import net.jason.mccourse.entity.ai.RhinoAttackGoal;
import net.jason.mccourse.entity.variant.RhinoVariant;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;

public class RhinoEntity extends TamableAnimal implements PlayerRideable {
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(RhinoEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT =
            SynchedEntityData.defineId(RhinoEntity.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;

    public final AnimationState sitAnimationState = new AnimationState();

    public RhinoEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(1, new RhinoAttackGoal(this, 1.00, true));

        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.25d, 18f, 7f));

        this.goalSelector.addGoal(1, new FollowParentGoal(this, 1.1d));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));

        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 4f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes().add(Attributes.MAX_HEALTH, 35D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.1f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.ATTACK_DAMAGE, 2f)
                .add(Attributes.STEP_HEIGHT, 1f);

    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return false;
    }


    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.RHINO.get().create(pLevel);
    }

    private void setupAnimationSates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if(this.isAttacking() && attackAnimationTimeout <= 0) {
            attackAnimationTimeout = 80;
            attackAnimationState.start(this.tickCount);
        } else {
            --this.attackAnimationTimeout;
        }

        if(!this.isAttacking()) {
            attackAnimationState.stop();
        }

        if(this.isInSittingPose()) {
            sitAnimationState.startIfStopped(this.tickCount);
        } else {
            sitAnimationState.stop();
        }

    }

    protected void updateWalkAnimation(float v) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(v * 6.0f, 1.0f);
        } else {
            f = 0.0F;
        }

        this.walkAnimation.update(f,0.2F);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationSates();
        }
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(ATTACKING, false);
        pBuilder.define(DATA_ID_TYPE_VARIANT, 0);
    }

    /* VARIANT */

    public RhinoVariant getVariant() {
        return RhinoVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    private void setVariant(RhinoVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pSpawnType, @Nullable SpawnGroupData pSpawnGroupData) {
        RhinoVariant variant = Util.getRandom(RhinoVariant.values(), this.random);
        this.setVariant(variant);
        return super.finalizeSpawn(pLevel, pDifficulty, pSpawnType, pSpawnGroupData);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(DATA_ID_TYPE_VARIANT, pCompound.getInt("Variant"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Variant", this.getTypeVariant());
    }

    /* SOUNDS */

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.HOGLIN_AMBIENT;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.RAVAGER_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH;
    }

    /* TAMABLE */

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        Item item = itemstack.getItem();

        Item itemForTaming = Items.APPLE;

        if(item == itemForTaming && !isTame()) {
            if(this.level().isClientSide()) {
                return InteractionResult.CONSUME;
            } else {
                if (!pPlayer.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                if(!ForgeEventFactory.onAnimalTame(this, pPlayer)) {
                        super.tame(pPlayer);
                        this.navigation.recomputePath();
                        this.setTarget(null);
                        this.level().broadcastEntityEvent(this, (byte)7);
                        setOrderedToSit(true);
                        this.setInSittingPose(true);
                }

                return InteractionResult.SUCCESS;
            }
        }

        // TOGGLES SITTING FOR OUR ENTITY
        if(isTame() && pHand == InteractionHand.MAIN_HAND) {
            if(!pPlayer.isCrouching()) {
                setRiding(pPlayer);
            } else {
                /* Toggles Sitting for our Entity */
                setOrderedToSit(!isOrderedToSit());
                setInSittingPose(!isOrderedToSit());
            }


            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(pPlayer, pHand);
    }

    /* RIDEABLE */

    private void setRiding(Player pPlayer) {
        this.setInSittingPose(false);

        pPlayer.setYRot(this.getYRot());
        pPlayer.setXRot(this.getXRot());
        pPlayer.startRiding(this);
    }

    @Override
    public @Nullable LivingEntity getControllingPassenger() {
        return ((LivingEntity) this.getFirstPassenger());
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if(this.isVehicle() && getControllingPassenger() instanceof Player){
            LivingEntity livingentity = this.getControllingPassenger();
            this.setYRot(livingentity.getYRot());
            this.yRotO = this.getYRot();
            this.setXRot(livingentity.getXRot() * 0.5f);
            this.setRot(this.getYRot(), this.getXRot());
            this.yBodyRot = this.getYRot();
            this.yHeadRot = this.yBodyRot;
            float f = livingentity.xxa * 0.5f;
            float f1 = livingentity.zza;

            if(this.isControlledByLocalInstance()) {
                float newSpeed = (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED);
                if(Minecraft.getInstance().options.keySprint.isDown()) {
                    newSpeed *= 2f;
                }

                this.setSpeed(newSpeed);
                super.travel(new Vec3(f, pTravelVector.y, f1));
            }


        } else {
            super.travel(pTravelVector);
        }
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity pPassenger) {
        Direction direction = this.getMotionDirection();
        if (direction.getAxis() != Direction.Axis.Y) {
            int [][] offsets = DismountHelper.offsetsForDirection(direction);
            BlockPos blockpos = this.blockPosition();
            BlockPos.MutableBlockPos blockPos$mutableblockpos = new BlockPos.MutableBlockPos();

            for (Pose pose : pPassenger.getDismountPoses()) {
                AABB aabb = pPassenger.getLocalBoundsForPose(pose);

                for(int[] offset : offsets) {
                    blockPos$mutableblockpos.set(blockpos.getX() + offset[0], blockpos.getY(), blockpos.getZ() + offset[1]);
                    double d0 = this.level().getBlockFloorHeight(blockPos$mutableblockpos);
                    if (DismountHelper.isBlockFloorValid(d0)) {
                        Vec3 vec3 = Vec3.upFromBottomCenterOf(blockPos$mutableblockpos, d0);
                        if (DismountHelper.canDismountTo(this.level(), pPassenger, aabb.move(vec3))) {
                            pPassenger.setPose(pose);
                        }
                    }
                }
            }
        }


        return super.getDismountLocationForPassenger(pPassenger);
    }
}
