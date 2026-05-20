package net.masuno.config;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class MasConfig implements Serializable {

    //Mace
    public boolean MaceShockwave = true;
    public float MaceShockwaveSize = 1.0F;
    public float MaceShockwaveOpacity = 1.0F;
    public boolean MaceSpark = true;
    public boolean MaceFlash = true;

    //General
    public boolean ShieldEffect = true;
    public boolean ArmorParticles = true;
    public boolean WindParticles = true;
    public boolean CustomTotemEffect = true;
    public float TotemEffectOpacity = 1.0F;
    public boolean PlayerDeathEffect = true;
    public boolean GlideIcon = true;

    // Pearls
    public boolean PearlTrailParticles = false;
    public float PearlTrailOpacity = 0.5F;
    public List<String> PearlWhiteList = new ArrayList<>();
    public int SelfPearlColor = 0xB3ffff00;
    public int AllyPearlColor = 0xB30000ff;
    public int OtherPearlColor = 0xB3ff0000;

    //Hitboxes
    public boolean CustomHitbox = true;
    public boolean PearlHitboxColors= true;
    public float MobPearlHitboxOpacity = 0.3F;
    public float PlayerPearlHitboxOpacity = 0.8F;
    public float HitboxFadeDistance = 15F;
    public float HitboxProjectileFadeDistance = 5F;
}
