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
public class DeathSpark extends SingleQuadParticle {
   private final SpriteSet spriteProv;

   public DeathSpark(ClientLevel clientWorld, double x, double y, double z, SpriteSet spriteProvider, double xSpeed, double ySpeed, double zSpeed) {
      super(clientWorld, x, y, z, xSpeed, ySpeed, zSpeed, spriteProvider.first());
      this.gravity = -0.1F;
      this.hasPhysics = false;
      this.friction = 1.0F;
      this.yd = 0.0;
      this.quadSize = this.random.nextIntBetweenInclusive(5, 10) * 0.06F;
      this.xd = this.random.nextIntBetweenInclusive(-10, 10) * 0.02F;
      this.zd = this.random.nextIntBetweenInclusive(-10, 10) * 0.02F;
      this.lifetime = 20;
      this.alpha = 1.0F;
      this.spriteProv = spriteProvider;
      this.setSprite(this.spriteProv.get(this.age, this.lifetime));
   }

   public void tick() {
      super.tick();
      this.setSprite(this.spriteProv.get(this.age, this.lifetime));
      this.alpha = (1.0F - (float)this.age / this.lifetime) * 0.45F;
      this.xd *= 0.9F;
      this.zd *= 0.9F;
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
         return new DeathSpark(world, x, y, z, this.spriteProvider, velocityX, velocityY, velocityZ);
      }
   }
}
