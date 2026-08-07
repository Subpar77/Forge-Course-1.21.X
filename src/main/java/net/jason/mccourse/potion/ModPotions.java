package net.jason.mccourse.potion;

import net.jason.mccourse.MCCourseMod;
import net.jason.mccourse.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
         DeferredRegister.create(ForgeRegistries.POTIONS, MCCourseMod.MOD_ID);

    public static final RegistryObject<Potion> SLIMEY_POTION = POTIONS.register("slimey_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.SLIMEY_EFFECT.getHolder().get(), 200, 0)));


    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
