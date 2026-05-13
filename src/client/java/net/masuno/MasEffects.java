package net.masuno;

import net.fabricmc.api.ModInitializer;
import net.masuno.config.MasConfig;
import net.uku3lig.ukulib.config.ConfigManager;

public class MasEffects implements ModInitializer {
   public static final ConfigManager<MasConfig> manager = ConfigManager.createDefault(MasConfig.class, "maseffects");
   public void onInitialize() {
   }
}
