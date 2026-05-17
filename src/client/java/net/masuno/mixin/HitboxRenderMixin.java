package net.masuno.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.config.MasConfig;
import net.minecraft.client.renderer.debug.EntityHitboxDebugRenderer;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.projectile.hurtingprojectile.windcharge.WindCharge;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(EntityHitboxDebugRenderer.class)
public class HitboxRenderMixin {
    @Shadow
    @Final
    Minecraft minecraft;

    @Inject(method = "showHitboxes", at = @At("HEAD"), cancellable = true)
    private void showCustomHitboxes(Entity entity, float tickDelta, boolean showServerEntity, CallbackInfo ci) {
        if (MasConfig.INSTANCE.CustomHitbox) {
            if (this.minecraft.player != null) {
                boolean isMob = true;
                Vec3 renderPos = entity.getPosition(tickDelta);
                Vec3 interpolationOffset = renderPos.subtract(entity.position());
                AABB box = entity.getBoundingBox().move(interpolationOffset);
                if (entity instanceof Player) {
                    isMob = false;
                    float distance = (float)Math.clamp(
                            (this.minecraft.player.position().distanceTo(entity.position()) - MasConfig.INSTANCE.HitboxFadeDistance) / 20.0, 0.0, 1.0
                    );
                    int color = ARGB.colorFromFloat((1.0F - distance) * MasConfig.INSTANCE.PlayerPearlHitboxOpacity, 1.0F, 1.0F, 1.0F);
                    Gizmos.cuboid(box, GizmoStyle.stroke(color));
                }

                if (entity instanceof ThrownEnderpearl pearl) {
                    isMob = false;
                    float distance = (float) Math.clamp(
                            (this.minecraft.player.position().distanceTo(entity.position()) - MasConfig.INSTANCE.HitboxProjectileFadeDistance) / 20.0,
                            0.0,
                            1.0
                    );
                    if (pearl.getOwner() != null && MasConfig.INSTANCE.PearlHitboxColors) {
                        int baseColor;
                        if (pearl.getOwner().getUUID().equals(this.minecraft.player.getUUID())) {
                            baseColor = MasConfig.INSTANCE.SelfPearlColor;
                        } else if (MasConfig.INSTANCE.PearlWhiteList.contains(pearl.getOwner().getScoreboardName())) {
                            baseColor = MasConfig.INSTANCE.AllyPearlColor;
                        } else {
                            baseColor = MasConfig.INSTANCE.OtherPearlColor;
                        }

                        int argb = ARGB.colorFromFloat(
                                distance,
                                ARGB.red(baseColor) / 255.0F,
                                ARGB.green(baseColor) / 255.0F,
                                ARGB.blue(baseColor) / 255.0F
                        );
                        Gizmos.cuboid(box, GizmoStyle.stroke(argb));
                    } else {
                        int argb = ARGB.colorFromFloat(distance, 1.0F, 1.0F, 0.0F);
                        Gizmos.cuboid(box, GizmoStyle.stroke(argb));
                    }
                }

                if (entity instanceof WindCharge) {
                    isMob = false;
                    float distance = (float)Math.clamp(
                            (this.minecraft.player.position().distanceTo(entity.position()) - MasConfig.INSTANCE.HitboxProjectileFadeDistance) / 20.0,
                            0.0,
                            1.0
                    );
                    int color = ARGB.colorFromFloat(distance, 0.9F, 0.9F, 1.0F);
                    Gizmos.cuboid(box, GizmoStyle.stroke(color));
                }

                if (entity instanceof LivingEntity && isMob) {
                    float distance = (float)Math.clamp(
                            (this.minecraft.player.position().distanceTo(entity.position()) - MasConfig.INSTANCE.HitboxFadeDistance) / 20.0, 0.0, 1.0
                    );
                    int color = ARGB.colorFromFloat((1.0F - distance) * MasConfig.INSTANCE.MobPearlHitboxOpacity, 1.0F, 1.0F, 1.0F);
                    Gizmos.cuboid(box, GizmoStyle.stroke(color));
                }

                ci.cancel();
            }
        }
    }
}
