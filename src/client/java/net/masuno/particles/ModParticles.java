package net.masuno.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.client.particle.FireworkParticles.SparkProvider;

@Environment(EnvType.CLIENT)
public class ModParticles {
   public static final SimpleParticleType WINDWAVE = FabricParticleTypes.simple();
   public static final SimpleParticleType SHOCKWAVE = FabricParticleTypes.simple();
   public static final SimpleParticleType FLICK = FabricParticleTypes.simple();
   public static final SimpleParticleType FLASH = FabricParticleTypes.simple();
   public static final SimpleParticleType DIAMOND_SCRAP = FabricParticleTypes.simple();
   public static final SimpleParticleType NETHERITE_SCRAP = FabricParticleTypes.simple();
   public static final SimpleParticleType REVIVE = FabricParticleTypes.simple();
   public static final SimpleParticleType REVIVE_SPARK = FabricParticleTypes.simple();
   public static final SimpleParticleType SHIELD_WAVE = FabricParticleTypes.simple();
   public static final SimpleParticleType DEATH_SPARK = FabricParticleTypes.simple();
   public static final SimpleParticleType DEATH_SKULL = FabricParticleTypes.simple();
   public static final SimpleParticleType ENDER_PEARL_TRAIL = FabricParticleTypes.simple();

   public static void Register() {
      Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath("maseffects", "smash"), SHOCKWAVE);
      Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath("maseffects", "windwave"), WINDWAVE);
      Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath("maseffects", "flick"), FLICK);
      Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath("maseffects", "flash"), FLASH);
      Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath("maseffects", "diamond_scrap"), DIAMOND_SCRAP);
      Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath("maseffects", "netherite_scrap"), NETHERITE_SCRAP);
      Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath("maseffects", "revive"), REVIVE);
      Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath("maseffects", "revive_spark"), REVIVE_SPARK);
      Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath("maseffects", "shield_wave"), SHIELD_WAVE);
      Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath("maseffects", "death_spark"), DEATH_SPARK);
      Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath("maseffects", "death_skull"), DEATH_SKULL);
      Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath("maseffects", "pearl_trail"), ENDER_PEARL_TRAIL);
      ParticleFactoryRegistry.getInstance().register(SHOCKWAVE, SmashParticle.Factory::new);
      ParticleFactoryRegistry.getInstance().register(WINDWAVE, WindParticle.Factory::new);
      ParticleFactoryRegistry.getInstance().register(FLICK, FlickParticle.Factory::new);
      ParticleFactoryRegistry.getInstance().register(FLASH, SparkProvider::new);
      ParticleFactoryRegistry.getInstance().register(DIAMOND_SCRAP, ScrapParticle.Factory::new);
      ParticleFactoryRegistry.getInstance().register(NETHERITE_SCRAP, ScrapParticle.Factory::new);
      ParticleFactoryRegistry.getInstance().register(REVIVE, ReviveParticle.Factory::new);
      ParticleFactoryRegistry.getInstance().register(REVIVE_SPARK, ReviveSparkParticle.Factory::new);
      ParticleFactoryRegistry.getInstance().register(SHIELD_WAVE, ShieldWave.Factory::new);
      ParticleFactoryRegistry.getInstance().register(DEATH_SPARK, DeathSpark.Factory::new);
      ParticleFactoryRegistry.getInstance().register(DEATH_SKULL, DeathSkull.Factory::new);
      ParticleFactoryRegistry.getInstance().register(ENDER_PEARL_TRAIL, EnderPearlTrail.Factory::new);
   }
}
