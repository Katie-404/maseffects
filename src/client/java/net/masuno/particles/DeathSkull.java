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

@Environment(EnvType.CLIENT)
public class DeathSkull extends SingleQuadParticle {
   private final SpriteSet spriteProv;

   public DeathSkull(ClientLevel clientWorld, double x, double y, double z, SpriteSet spriteProvider, double xSpeed, double ySpeed, double zSpeed) {
      super(clientWorld, x, y, z, xSpeed, ySpeed, zSpeed, spriteProvider.first());
      this.gravity = 0.0F;
      this.hasPhysics = false;
      this.yd = 0.2;
      this.quadSize = 1.0F;
      this.xd = 0.0;
      this.zd = 0.0;
      this.lifetime = 58;
      this.alpha = 1.0F;
      this.spriteProv = spriteProvider;
      this.setSprite(this.spriteProv.get(this.age, this.lifetime));
   }

   public void tick() {
      super.tick();
      this.setSprite(this.spriteProv.get(Math.clamp(this.age, 0, 30), 30));
      if (this.age > 30) {
         this.alpha -= 0.033333335F;
      }

      this.yd *= 0.95F;
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

      public Particle createParticle(
              SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random
      ) {
         return new DeathSkull(world, x, y, z, this.spriteProvider, velocityX, velocityY, velocityZ);
      }
   }
}
