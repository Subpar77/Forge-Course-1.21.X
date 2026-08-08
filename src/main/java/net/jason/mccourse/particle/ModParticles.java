package net.jason.mccourse.particle;

import net.jason.mccourse.MCCourseMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPE =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MCCourseMod.MOD_ID);

public static final RegistryObject<SimpleParticleType> ALEXANDRITE_PARTICLES =
        PARTICLE_TYPE.register("alexandrite_particles", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPE.register(eventBus);
    }
}
