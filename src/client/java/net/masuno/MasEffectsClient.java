package net.masuno;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.events.EventManager;
import net.masuno.particles.ModParticles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class MasEffectsClient implements ClientModInitializer {
   public static final String MOD_ID = "maseffects";
   public static final Logger logger = LoggerFactory.getLogger("maseffects");

   public void onInitializeClient() {
      logger.debug("Mas Effects is loaded!");
      ModParticles.Register();
      EventManager.registerEvents();
   }
}
