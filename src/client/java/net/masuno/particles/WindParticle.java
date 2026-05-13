package net.masuno.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.MasEffects;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
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

@Environment(EnvType.CLIENT)
public class WindParticle extends SingleQuadParticle {
   private static final Quaternionf QUATERNION = new Quaternionf(0.0, 0.7, 0.7F, 0.0);
   private static final Quaternionf ROTATED_QUATERNION = new Quaternionf(-0.2705981, 0.6532815, 0.6532815, 0.2705981);
   private final SpriteSet provider;
   private float scaler = 1.0F;
   private float sizer = 1.0F;
   private float alpha_ctrl = 1.0F;

   public WindParticle(ClientLevel clientWorld, double x, double y, double z, SpriteSet spriteProvider, double xSpeed, double ySpeed, double zSpeed) {
      super(clientWorld, x, y, z, xSpeed, ySpeed, zSpeed, spriteProvider.first());
      this.scaler = (float)xSpeed;
      this.provider = spriteProvider;
      this.alpha_ctrl = (float)ySpeed;
      this.alpha = Math.clamp(this.alpha_ctrl * MasEffects.manager.getConfig().MaceShockwaveOpacity, 0.0F, 1.0F);
      this.quadSize = 0.5F;
      this.lifetime = 40;
      this.friction = 0.0F;
      this.sizer = (float)zSpeed * 0.1F;
      this.setSprite(spriteProvider.first());
   }

   public void extractRotatedQuad(QuadParticleRenderState submittable, Quaternionf rotation, float x, float y, float z, float tickProgress) {
      submittable.add(
              this.getLayer(),
              x,
              y,
              z,
              QUATERNION.x,
              QUATERNION.y,
              QUATERNION.z,
              QUATERNION.w,
              this.getQuadSize(tickProgress),
              this.getU0(),
              this.getU1(),
              this.getV0(),
              this.getV1(),
              ARGB.colorFromFloat(this.alpha, this.rCol, this.gCol, this.bCol),
              this.getLightCoords(tickProgress)
      );
      submittable.add(
              this.getLayer(),
              x,
              y,
              z,
              ROTATED_QUATERNION.x,
              ROTATED_QUATERNION.y,
              ROTATED_QUATERNION.z,
              ROTATED_QUATERNION.w,
              this.getQuadSize(tickProgress),
              this.getU0(),
              this.getU1(),
              this.getV0(),
              this.getV1(),
              ARGB.colorFromFloat(this.alpha, this.rCol, this.gCol, this.bCol),
              this.getLightCoords(tickProgress)
      );
   }

   protected Layer getLayer() {
      return Layer.TRANSLUCENT;
   }

   public void tick() {
      super.tick();
      this.quadSize = this.quadSize + this.scaler * this.sizer;
      this.scaler = this.scaler - 1.0F / this.lifetime;
      this.alpha_ctrl = this.alpha_ctrl - 1.0F / this.lifetime;
      this.scaler = Math.clamp(this.scaler, 0.0F, 1.0F);
      this.alpha = Math.clamp(this.alpha_ctrl * MasEffects.manager.getConfig().MaceShockwaveOpacity, 0.0F, 1.0F);
      this.setSprite(this.provider.get(Math.min(this.age, this.lifetime / 2), this.lifetime / 2));
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
         return new WindParticle(world, x, y, z, this.spriteProvider, velocityX, velocityY, velocityZ);
      }
   }
}
