package net.masuno.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.MasEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.debug.EntityHitboxDebugRenderer;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.windcharge.WindCharge;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Environment(EnvType.CLIENT)
@Mixin(EntityHitboxDebugRenderer.class)
public class HitboxRenderMixin {
    @Shadow
    @Final
    private Minecraft minecraft;
    @Inject(method = "showHitboxes",at = @At("HEAD"),cancellable = true)
    private void renderCustomHitbox(Entity entity, float partialTicks, boolean isServerEntity, CallbackInfo ci){
        boolean isMob = true;
        if (!MasEffects.manager.getConfig().CustomHitbox) return;
        //Hitbox is from player
        if(this.minecraft.player == null) return;
        if (entity instanceof Player) {
            isMob = false;
            float distance = (float) Math.clamp((this.minecraft.player.position().distanceTo(entity.position()) - MasEffects.manager.getConfig().getHitboxFadeDistance()) / 20F, 0D, 1D);
            int c = ARGB.colorFromFloat((1.0F - distance) * MasEffects.manager.getConfig().getPlayerHitboxOpacity(), 1.0F, 1.0F, 1.0F);
            boxdrawer(entity, partialTicks, c);
        }
        //Hitbox is from pearl
        if (entity instanceof ThrownEnderpearl pe){
            isMob = false;
            float distance = (float)Math.clamp((this.minecraft.player.position().distanceTo(entity.position()) - MasEffects.manager.getConfig().getHitboxProjectileFadeDistance()) / 20F,0D,1D);
            if (pe.getOwner() != null && MasEffects.manager.getConfig().PearlHitboxColors){
                int pearlcolor = getColor(pe);
                int c = ARGB.colorFromFloat(distance, ARGB.red(pearlcolor) /225.0F , ARGB.green(pearlcolor)/255.0F, ARGB.blue(pearlcolor)/255.0F);
                boxdrawer(entity, partialTicks, c);
            }else {
                int c = ARGB.colorFromFloat(distance, 1.0F, 1.0F, 0.0F);
                boxdrawer(entity, partialTicks, c);
            }
        }
        //Hitbox is from wind charge
        if (entity instanceof WindCharge){
            isMob = false;
            float distance = (float)Math.clamp((this.minecraft.player.position().distanceTo(entity.position()) - MasEffects.manager.getConfig().getHitboxProjectileFadeDistance()) / 20F,0D,1D);
            int c = ARGB.colorFromFloat(distance, 0.9F, 0.9F, 1.0F);
            boxdrawer(entity, partialTicks, c);
        }
        //Hitbox from other mobs
        if (entity instanceof LivingEntity && isMob){
            float distance = (float)Math.clamp((this.minecraft.player.position().distanceTo(entity.position()) - MasEffects.manager.getConfig().getHitboxFadeDistance()) / 20F,0D,1D);
            int c = ARGB.colorFromFloat((1.0F - distance) * MasEffects.manager.getConfig().getMobHitboxOpacity(), 1.0F, 1.0F, 1.0F);
            boxdrawer(entity, partialTicks, c);
        }
        ci.cancel();
    }
    @Unique
    private void boxdrawer(Entity entity, float partialTicks, int c){
        Gizmos.cuboid(entity.getBoundingBox().move(entity.getPosition(partialTicks).subtract(entity.position())), GizmoStyle.stroke(c));
    }
    @Unique
    private int getColor(ThrownEnderpearl pe) {
        int color;
        assert this.minecraft.player != null;
        if (Objects.requireNonNull(pe.getOwner()).getUUID() == this.minecraft.player.getUUID()){
            //Pearl is mine
            color = MasEffects.manager.getConfig().getSelfPearlColor();
        }else if(MasEffects.manager.getConfig().getPearlWhiteList().contains(pe.getOwner().getScoreboardName())){
            //Pearl is from whitelisted player
            color = MasEffects.manager.getConfig().getAllyPearlColor();
        }
        else {
            //Pearl is from non-whitelisted
            color = MasEffects.manager.getConfig().getOtherPearlColor();
        }
        return color;
    }
}