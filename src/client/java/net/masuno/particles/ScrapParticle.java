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
public class ScrapParticle extends SingleQuadParticle {
   public ScrapParticle(ClientLevel clientWorld, double x, double y, double z, SpriteSet spriteProvider, double xSpeed, double ySpeed, double zSpeed) {
      super(clientWorld, x, y, z, xSpeed, ySpeed, zSpeed, spriteProvider.first());
      this.gravity = 1.0F;
      this.hasPhysics = true;
      this.friction = 1.0F;
      this.lifetime = 20;
      this.setSprite(spriteProvider.first());
   }

   public void tick() {
      super.tick();
      this.quadSize = (1.0F - (float)this.age / this.lifetime) * 0.35F;
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
         return new ScrapParticle(world, x, y, z, this.spriteProvider, velocityX, velocityY, velocityZ);
      }
   }
}
