package net.masuno.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.config.MasConfig;
import net.masuno.particles.ModParticles;
import net.minecraft.world.entity.Entity;

@Environment(EnvType.CLIENT)
public class TotemEvent {
   public static void run(Entity popper) {
      if (MasConfig.INSTANCE.CustomTotemEffect) {
         for (int i = 1; i < 18; i++) {
            double scaler = 2.5;
            popper.level()
                    .addParticle(
                            ModParticles.REVIVE,
                            popper.getX(),
                            popper.getY() + popper.getDimensions(popper.getPose()).height() / 2.0F,
                            popper.getZ(),
                            scaler,
                            popper.getId(),
                            0.0
                    );
         }

         for (int i = 1; i < 100; i++) {
            popper.level()
                    .addParticle(
                            ModParticles.REVIVE_SPARK,
                            popper.getX(),
                            popper.getY() + popper.getDimensions(popper.getPose()).height() / 2.0F,
                            popper.getZ(),
                            popper.getId(),
                            0.0,
                            0.0
                    );
         }
      }
   }
}
