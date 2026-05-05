package net.masuno.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.config.MasConfig;
import net.masuno.particles.ModParticles;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.network.AbstractClientPlayerEntity;

@Environment(EnvType.CLIENT)
public class DeathEvent {
   public static void runDeath(AbstractClientPlayerEntity player) {
      if (MasConfig.INSTANCE.PlayerDeathEffect) {
         Vec3d pos = player.getEntityPos();
         if (player.getEntityWorld() instanceof ClientWorld world) {
            world.addParticleClient(ModParticles.DEATH_SKULL, pos.getX(), pos.getY(), pos.getZ(), 0.0, 0.0, 0.0);

            for (int i = 0; i < 30; i++) {
               world.addParticleClient(ModParticles.DEATH_SPARK, pos.getX(), pos.getY(), pos.getZ(), 0.0, 0.0, 0.0);
            }
         }
      }
   }
}
