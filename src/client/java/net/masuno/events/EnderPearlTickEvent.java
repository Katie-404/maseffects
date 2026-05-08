package net.masuno.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.config.MasConfig;
import net.masuno.particles.ModParticles;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class EnderPearlTickEvent {
   public static void tick(ThrownEnderpearl entity) {
      if (MasConfig.INSTANCE.PearlTrailParticles) {
         Vec3 motion = entity.getKnownMovement().normalize().scale(0.05F);

         for (int i = 0; i < 3; i++) {
            entity.level()
                    .addParticle(
                            ModParticles.ENDER_PEARL_TRAIL,
                            entity.getX(),
                            entity.getY(),
                            entity.getZ(),
                            motion.x(),
                            motion.y(),
                            motion.z()
                    );
         }
      }
   }
}
