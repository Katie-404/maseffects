package net.masuno.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.masuno.config.MasConfig;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl;
import net.minecraft.resources.Identifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;

@Environment(EnvType.CLIENT)
public class EventManager {
   public static void registerEvents() {
      AttackEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
         PlayerAttackManager.clientAttack(playerEntity, entity, world);
         return InteractionResult.PASS;
      });
      HudElementRegistry.attachElementBefore(
              VanillaHudElements.CROSSHAIR,
              Identifier.fromNamespaceAndPath("maseffects", "elytra_hud"),
              (drawContext, renderTickCounter) -> {
                 if (Minecraft.getInstance().player != null && MasConfig.INSTANCE.GlideIcon && Minecraft.getInstance().player.isFallFlying()) {
                    drawContext.blit(
                            RenderPipelines.GUI_TEXTURED,
                            Identifier.fromNamespaceAndPath("maseffects", "textures/hud/elytra_icon.png"),
                            drawContext.guiWidth() / 2 - 5,
                            drawContext.guiHeight() / 2 + 7,
                            0.0F,
                            0.0F,
                            9,
                            9,
                            9,
                            9
                    );
                 }
              }
      );
      ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
         if (minecraftClient.level != null) {
             for (AbstractClientPlayer player : minecraftClient.level.players()) {
                 if (player.deathTime == 1) {
                   DeathEvent.runDeath(player);
                 }
             }

             for (Entity entity : minecraftClient.level.entitiesForRendering()) {
                if (entity instanceof ThrownEnderpearl end) {
                   EnderPearlTickEvent.tick(end);
                }
             }
         }
      });
   }
}
