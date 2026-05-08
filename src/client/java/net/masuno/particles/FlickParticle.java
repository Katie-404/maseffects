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
public class FlickParticle extends SingleQuadParticle {
   public float time;
   public float scaler;

   public FlickParticle(ClientLevel clientWorld, double x, double y, double z, SpriteSet spriteProvider, double xSpeed, double ySpeed, double zSpeed) {
      super(clientWorld, x, y, z, xSpeed, ySpeed, zSpeed, spriteProvider.first());
      this.alpha = 1.0F;
      this.scaler = (float)xSpeed;
      this.lifetime = 20;
      this.time = 0.0F;
      this.friction = 0.0F;
      this.setSprite(spriteProvider.first());
   }

   public void tick() {
      super.tick();
      this.alpha = Math.clamp((1.0F - this.time) * 1.5F, 0.2F, 1.0F) - 0.2F;
      this.time = this.time + 1.0F / this.lifetime;
      this.quadSize = this.alpha * this.scaler;
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
         return new FlickParticle(world, x, y, z, this.spriteProvider, velocityX, velocityY, velocityZ);
      }
   }
}
