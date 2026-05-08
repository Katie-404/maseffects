package net.masuno.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.RandomSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ShieldWave extends SingleQuadParticle {
   private float scaler = 1.0F;

   public ShieldWave(ClientLevel clientWorld, double x, double y, double z, SpriteSet spriteProvider, double xSpeed, double ySpeed, double zSpeed) {
      super(clientWorld, x, y, z, xSpeed, ySpeed, zSpeed, spriteProvider.first());
      this.friction = 0.0F;
      this.lifetime = 30;
      this.quadSize = 0.3F;
      this.setSprite(spriteProvider.first());
   }

   public void tick() {
      super.tick();
      this.quadSize = this.quadSize + this.scaler * 0.2F;
      this.scaler = this.scaler - 1.0F / this.lifetime;
      this.alpha = this.alpha - 1.0F / this.lifetime;
      this.scaler = Math.clamp(this.scaler, 0.0F, 1.0F);
      this.alpha = Math.clamp(this.alpha, 0.0F, 1.0F);
   }

   protected Layer getLayer() {
      return Layer.TRANSLUCENT;
   }

   @Environment(EnvType.CLIENT)
   public static class Factory implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteProvider;

      public Factory(SpriteSet spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      @Nullable
      public Particle createParticle(
              SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random
      ) {
         return new ShieldWave(world, x, y, z, this.spriteProvider, velocityX, velocityY, velocityZ);
      }
   }
}
