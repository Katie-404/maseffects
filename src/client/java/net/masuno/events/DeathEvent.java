package net.masuno.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.config.MasConfig;
import net.masuno.particles.ModParticles;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;

@Environment(EnvType.CLIENT)
public class DeathEvent {
   public static void runDeath(AbstractClientPlayer player) {
      if (MasConfig.INSTANCE.PlayerDeathEffect) {
         Vec3 pos = player.position();
         if (player.level() instanceof ClientLevel world) {
            world.addParticle(ModParticles.DEATH_SKULL, pos.x(), pos.y(), pos.z(), 0.0, 0.0, 0.0);

            for (int i = 0; i < 30; i++) {
               world.addParticle(ModParticles.DEATH_SPARK, pos.x(), pos.y(), pos.z(), 0.0, 0.0, 0.0);
            }
         }
      }
   }
}
