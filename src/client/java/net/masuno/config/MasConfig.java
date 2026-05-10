package net.masuno.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Category;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Excluded;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.List;

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

   @Tooltip
   @Category("hitbox")
   @Comment("Only players and ender pearls will show their hitboxes, player hitboxes will fade from afar and pearls can have different colors!")
   public boolean CustomHitbox = true;
   @Tooltip
   @Category("hitbox")
   @Comment("Colors the hitboxes of Ender Pearls")
   public boolean PearlHitboxColors= true;
   @Tooltip
   @Category("hitbox")
   @Comment("Pearls thrown by these players will be colored green instead of red")
   public List<String> PearlWhiteList = new ArrayList<>();
   @Tooltip
   @Category("hitbox")
   @Comment("Color of your own pearl hitbox")
   @ConfigEntry.ColorPicker(allowAlpha = true)
   public int SelfPearlColor = 0xB3ffff00;
   @Tooltip
   @Category("hitbox")
   @Comment("Color of your allies pearl hitboxes")
   @ConfigEntry.ColorPicker(allowAlpha = true)
   public int AllyPearlColor = 0xB30000ff;
   @Tooltip
   @Category("hitbox")
   @Comment("Color of other player's pearl hitboxes")
   @ConfigEntry.ColorPicker(allowAlpha = true)
   public int OtherPearlColor = 0xB3ff0000;
   @Tooltip
   @Category("hitbox")
   @Comment("Opacity of other mob's hitboxes")
   public float MobPearlHitboxOpacity = 0.3F;
   @Tooltip
   @Category("hitbox")
   @Comment("Opacity of player's hitboxes")
   public float PlayerPearlHitboxOpacity = 0.8F;
   @Tooltip
   @Category("hitbox")
   @Comment("Distance in blocks where hitbox starts to fade for mobs and players")
   public float HitboxFadeDistance = 15F;
   @Tooltip
   @Category("hitbox")
   @Comment("Distance in blocks where the hitbox starts to appear for pearls and wind charges")
   public float HitboxProjectileFadeDistance = 5F;


   public static void init() {
      AutoConfig.register(MasConfig.class, JanksonConfigSerializer::new);
      INSTANCE = (MasConfig)AutoConfig.getConfigHolder(MasConfig.class).getConfig();
   }
}
