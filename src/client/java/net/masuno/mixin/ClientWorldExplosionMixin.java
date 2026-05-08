package net.masuno.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.config.MasConfig;
import net.masuno.particles.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.GustSeedParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(GustSeedParticle.class)
public class ClientWorldExplosionMixin extends NoRenderParticle {
   protected ClientWorldExplosionMixin(ClientLevel clientWorld, double d, double e, double f) {
      super(clientWorld, d, e, f);
   }

   @Inject(method = "tick", at = @At("HEAD"))
   private void ExplodeInject(CallbackInfo ci) {
      if (this.age == 0 && MasConfig.INSTANCE.WindParticles) {
         assert Minecraft.getInstance().level != null;
         Minecraft.getInstance().level.addParticle(ModParticles.WINDWAVE, this.x, this.y, this.z, 0.3F, 1.0, 5.0);
      }
   }
}
