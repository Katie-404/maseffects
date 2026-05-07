package net.masuno.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Category;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Excluded;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Config(name = "mas_config")
@Environment(EnvType.CLIENT)
public class MasConfig implements ConfigData {
   @Excluded
   public static MasConfig INSTANCE;
   @Tooltip
   @Category("mace")
   @Comment("If enabled, creates a shockwave effect when slamming with the mace")
   public boolean MaceShockwave = true;
   @Tooltip
   @Category("mace")
   @Comment("Increases the size of the mace shockwave")
   public float MaceShockwaveSize = 1.0F;
   @Tooltip
   @Category("mace")
   @Comment("Sets the opacity of the mace shockwave")
   public float MaceShockwaveOpacity = 1.0F;
   @Tooltip
   @Category("mace")
   @Comment("Creates spark particles when slamming with the mace")
   public boolean MaceSpark = true;
   @Tooltip
   @Category("mace")
   @Comment("Creates flash upon slamming with the mace")
   public boolean MaceFlash = true;
   @Tooltip
   @Category("mace")
   @Comment("Creates a small shockwave on a player´s shield when blocking mace attacks")
   public boolean ShieldEffect = true;
   @Tooltip
   @Category("mace")
   @Comment("If enabled spawns armor breaking particles when using the breach enchantment")
   public boolean ArmorParticles = true;
   @Tooltip
   @Category("default")
   @Comment("Spawns particles when a wind charge explodes")
   public boolean WindParticles = true;
   @Tooltip
   @Category("default")
   @Comment("If enabled changes the totem particle effect completely")
   public boolean CustomTotemEffect = true;
   @Tooltip
   @Category("default")
   @Comment("Changes the opacity of the totem effect")
   public float TotemEffectOpacity = 1.0F;
   @Tooltip
   @Category("default")
   @Comment("Spawns custom death particles when a player dies")
   public boolean PlayerDeathEffect = true;
   @Tooltip
   @Category("default")
   @Comment("Makes ender pearls spawn a trail of particles")
   public boolean PearlTrailParticles = false;
   @Tooltip
   @Category("default")
   @Comment("Changes the opacity of the pearl trail")
   public float PearlTrailOpacity = 0.5F;
   @Tooltip
   @Category("default")
   @Comment("Enables an icon below to the crosshair that shows when you are gliding")//for some reason this text doesn't show in game
   public boolean GlideIcon = true;

   /*@Tooltip
   @Category("default")
   @Comment("Enables custom hitboxes")
   public boolean CustomHitbox = true;
   @Tooltip
   @Category("default")
   @Comment("Colors the hitboxes of Ender Pearls")
   public boolean PearlHitboxColors= true;*/


   public static void init() {
      AutoConfig.register(MasConfig.class, JanksonConfigSerializer::new);
      INSTANCE = (MasConfig)AutoConfig.getConfigHolder(MasConfig.class).getConfig();
   }
}
