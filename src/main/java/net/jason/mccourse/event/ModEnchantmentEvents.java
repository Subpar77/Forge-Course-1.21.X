package net.jason.mccourse.event;

import com.google.common.eventbus.Subscribe;
import net.jason.mccourse.MCCourseMod;
import net.jason.mccourse.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber(modid = MCCourseMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)

public class ModEnchantmentEvents {

    private static final List<PendingLightningStrike> PENDING_STRIKES = new ArrayList<>();

    private record PendingLightningStrike(ServerLevel level, Vec3 position, long strikeTime) {
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) {
            return;
        }

        if (!(attacker.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        ItemStack weapon = attacker.getMainHandItem();

//        RegistryAccess registryAccess = serverLevel.registryAccess();

        Holder<Enchantment> lightningStriker = serverLevel.registryAccess().registryOrThrow(Registries.ENCHANTMENT)
                .getHolderOrThrow(ModEnchantments.LIGHTNING_STRIKER);

        int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(lightningStriker, weapon);

        if (enchantmentLevel <= 0) {
            return;
        }

        Vec3 targetPosition = event.getEntity().position();

        for (int i = 0; i < enchantmentLevel; i++) {
            PENDING_STRIKES.add(new PendingLightningStrike(serverLevel, targetPosition, serverLevel.getGameTime() + i * 10l));
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Iterator<PendingLightningStrike> iterator = PENDING_STRIKES.iterator();

        while (iterator.hasNext()) {
            PendingLightningStrike pendingStrike = iterator.next();

            if (pendingStrike.level().getGameTime() < pendingStrike.strikeTime()) {
                continue;
            }

            spawnLightning(pendingStrike.level(), pendingStrike.position());

            iterator.remove();

        }
    }

    private static void spawnLightning(ServerLevel serverLevel, Vec3 position) {
        LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(serverLevel);

        if (lightningBolt == null) {
            return;
        }

        lightningBolt.moveTo(position);
        lightningBolt.setVisualOnly(false);
        serverLevel.addFreshEntity(lightningBolt);
    }
}

