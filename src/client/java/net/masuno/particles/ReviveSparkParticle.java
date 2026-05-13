package net.masuno.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.MasEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.RandomSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ReviveSparkParticle extends SingleQuadParticle {
   private LivingEntity target = null;
   private Vec3 targetDir;

   public ReviveSparkParticle(
           ClientLevel clientWorld, double x, double y, double z, SpriteSet spriteProvider, double xSpeed, double ySpeed, double zSpeed, RandomSource random
   ) {
      super(clientWorld, x, y, z, xSpeed, ySpeed, zSpeed, spriteProvider.first());
      this.lifetime = this.random.nextIntBetweenInclusive(30, 48);
      this.alpha = MasEffects.manager.getConfig().TotemEffectOpacity;
      this.quadSize = 0.1F;
      this.xd = this.random.nextIntBetweenInclusive(-10, 10) / 25.0F;
      this.yd = this.random.nextIntBetweenInclusive(-10, 10) / 25.0F;
      this.zd = this.random.nextIntBetweenInclusive(-10, 10) / 25.0F;
      this.friction = 1.0F;
      this.hasPhysics = true;
      this.setSprite(spriteProvider.get(random));
      Entity ent = clientWorld.getEntity((int)xSpeed);
      if (ent != null && ent instanceof LivingEntity) {
         this.target = (LivingEntity)ent;
      }

      if (this.random.nextBoolean()) {
         this.setColor(0.0F, 1.0F, 0.0F);
      } else {
         this.setColor(1.0F, 1.0F, 0.0F);
      }
   }

   public void tick() {
      super.tick();
      if (this.target != null) {
         this.targetDir = new Vec3(
                 this.target.getX() - this.x,
                 this.target.getY() + this.target.getDimensions(this.target.getPose()).height() / 2.0F - this.y,
                 this.target.getZ() - this.z
         );
         this.targetDir = this.targetDir.normalize();
      }

      if (this.age >= this.lifetime / 5.0F && this.age < this.lifetime / 4.0F) {
         this.friction = 0.0F;
      }

      if (this.target != null
              && this.age >= this.lifetime / 4.0F
              && this.target.position().distanceTo(new Vec3(this.x, this.y, this.z)) < 20.0) {
         this.alpha = Math.clamp(1.0F - (this.age - this.lifetime / 1.5F) / 20.0F, 0.0F, 1.0F) * MasEffects.manager.getConfig().TotemEffectOpacity;
         this.friction = 1.0F;
         this.xd = this.targetDir.x * ((this.age - 15) * 0.05F);
         this.yd = this.targetDir.y * ((this.age - 15) * 0.05F);
         this.zd = this.targetDir.z * ((this.age - 15) * 0.05F);
         if (this.target.position().distanceTo(new Vec3(this.x, this.y, this.z)) < 2.0) {
            this.alpha = 0.0F;
            this.removed = true;
         }
      }
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
         return new ReviveSparkParticle(world, x, y, z, this.spriteProvider, velocityX, velocityY, velocityZ, random);
      }
   }
}
