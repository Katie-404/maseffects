package net.masuno.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.MasEffects;
import net.masuno.MathUtility;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.RandomSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.util.ARGB;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ReviveParticle extends SingleQuadParticle {
   private final SpriteSet spriteProv;
   private final double scaler;
   private final double rotX;
   private final double rotZ;
   private double rotY;
   private LivingEntity target = null;
   private Quaternionf QUATERNION = new Quaternionf(0.0F, -0.7F, 0.7F, 0.0F);

   public ReviveParticle(ClientLevel clientWorld, double x, double y, double z, SpriteSet spriteProvider, double xSpeed, double ySpeed, double zSpeed) {
      super(clientWorld, x, y, z, xSpeed, ySpeed, zSpeed, spriteProvider.first());
      this.lifetime = 40;
      this.alpha = 0.0F;
      this.quadSize = 0.2F;
      this.scaler = xSpeed;
      this.friction = 0.0F;
      this.rotX = this.random.nextIntBetweenInclusive(-180, 180);
      this.rotY = this.random.nextIntBetweenInclusive(-180, 180);
      this.rotZ = this.random.nextIntBetweenInclusive(-180, 180);
      Entity ent = clientWorld.getEntity((int)ySpeed);
      if (ent instanceof LivingEntity) {
         this.target = (LivingEntity)ent;
      }

      if (this.random.nextBoolean()) {
         this.setColor(1.0F, 1.0F, 0.0F);
      } else {
         this.setColor(0.0F, 1.0F, 0.0F);
      }

      this.spriteProv = spriteProvider;
      this.setSprite(this.spriteProv.get(this.age, this.lifetime));
   }

   public void extractRotatedQuad(QuadParticleRenderState submittable, @NonNull Quaternionf rotation, float x, float y, float z, float tickProgress) {
      submittable.add(
              this.getLayer(),
              x,
              y,
              z,
              this.QUATERNION.x,
              this.QUATERNION.y,
              this.QUATERNION.z,
              this.QUATERNION.w,
              this.getQuadSize(tickProgress),
              this.getU0(),
              this.getU1(),
              this.getV0(),
              this.getV1(),
              ARGB.colorFromFloat(this.alpha, this.rCol, this.gCol, this.bCol),
              this.getLightCoords(tickProgress)
      );
      Quaternionf INVERT = this.QUATERNION.invert();
      submittable.add(
              this.getLayer(),
              x,
              y,
              z,
              INVERT.x,
              INVERT.y,
              INVERT.z,
              INVERT.w,
              this.getQuadSize(tickProgress),
              this.getU0(),
              this.getU1(),
              this.getV0(),
              this.getV1(),
              ARGB.colorFromFloat(this.alpha, this.rCol, this.gCol, this.bCol),
              this.getLightCoords(tickProgress)
      );
   }

   protected @NonNull Layer getLayer() {
      return Layer.TRANSLUCENT;
   }

   public void tick() {
      super.tick();
      this.setSprite(this.spriteProv.get(this.age, this.lifetime));
      if (this.target != null) {
         this.setPos(
                 this.target.getX(),
                 this.target.getY() + this.target.getDimensions(this.target.getPose()).height() / 2.0F,
                 this.target.getZ()
         );
      }

      if (!(MasEffects.manager.getConfig().TotemEffectOpacity <= 0.0F)) {
         this.rotY += 20.0;
         this.QUATERNION = MathUtility.euler(0.0F, 0.0F, (float)this.rotY);
         this.QUATERNION = MathUtility.euler((float)this.rotZ, (float)this.rotX, (float)(-this.rotZ)).mul(this.QUATERNION);
         this.quadSize = this.alpha / MasEffects.manager.getConfig().TotemEffectOpacity * (float)this.scaler;
         this.alpha = Math.clamp((float)Math.sqrt(Math.sin((double)this.age / this.lifetime * Math.PI)) / 1.2F, 0.0F, 1.0F)
                 * MasEffects.manager.getConfig().TotemEffectOpacity;
      }
   }

   @Environment(EnvType.CLIENT)
   public static class Factory implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteProvider;

      public Factory(SpriteSet spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      @Nullable
      public Particle createParticle(
              @NonNull SimpleParticleType parameters, @NonNull ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, @NonNull RandomSource random
      ) {
         return new ReviveParticle(world, x, y, z, this.spriteProvider, velocityX, velocityY, velocityZ);
      }
   }
}
