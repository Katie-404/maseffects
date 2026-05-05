package net.masuno.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.registry.Registry;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.client.particle.FireworksSparkParticle.ExplosionFactory;

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
      Registry.register(Registries.PARTICLE_TYPE, Identifier.of("maseffects", "smash"), SHOCKWAVE);
      Registry.register(Registries.PARTICLE_TYPE, Identifier.of("maseffects", "windwave"), WINDWAVE);
      Registry.register(Registries.PARTICLE_TYPE, Identifier.of("maseffects", "flick"), FLICK);
      Registry.register(Registries.PARTICLE_TYPE, Identifier.of("maseffects", "flash"), FLASH);
      Registry.register(Registries.PARTICLE_TYPE, Identifier.of("maseffects", "diamond_scrap"), DIAMOND_SCRAP);
      Registry.register(Registries.PARTICLE_TYPE, Identifier.of("maseffects", "netherite_scrap"), NETHERITE_SCRAP);
      Registry.register(Registries.PARTICLE_TYPE, Identifier.of("maseffects", "revive"), REVIVE);
      Registry.register(Registries.PARTICLE_TYPE, Identifier.of("maseffects", "revive_spark"), REVIVE_SPARK);
      Registry.register(Registries.PARTICLE_TYPE, Identifier.of("maseffects", "shield_wave"), SHIELD_WAVE);
      Registry.register(Registries.PARTICLE_TYPE, Identifier.of("maseffects", "death_spark"), DEATH_SPARK);
      Registry.register(Registries.PARTICLE_TYPE, Identifier.of("maseffects", "death_skull"), DEATH_SKULL);
      Registry.register(Registries.PARTICLE_TYPE, Identifier.of("maseffects", "pearl_trail"), ENDER_PEARL_TRAIL);
      ParticleFactoryRegistry.getInstance().register(SHOCKWAVE, SmashParticle.Factory::new);
      ParticleFactoryRegistry.getInstance().register(WINDWAVE, WindParticle.Factory::new);
      ParticleFactoryRegistry.getInstance().register(FLICK, FlickParticle.Factory::new);
      ParticleFactoryRegistry.getInstance().register(FLASH, ExplosionFactory::new);
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
