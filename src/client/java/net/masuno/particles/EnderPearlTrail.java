package net.masuno.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.MasEffects;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.util.RandomSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class EnderPearlTrail extends SingleQuadParticle {
   private final SpriteSet spriteProv;
   private float alpha_ctrl = 1.0F;

   public EnderPearlTrail(ClientLevel clientWorld, double x, double y, double z, SpriteSet spriteProvider, double xSpeed, double ySpeed, double zSpeed) {
      super(clientWorld, x, y, z, xSpeed, ySpeed, zSpeed, spriteProvider.first());
      this.gravity = 0.0F;
      this.xd = xSpeed + this.random.nextIntBetweenInclusive(-10, 10) * 0.005F;
      this.yd = ySpeed + this.random.nextIntBetweenInclusive(-10, 10) * 0.005F;
      this.zd = zSpeed + this.random.nextIntBetweenInclusive(-10, 10) * 0.005F;
      this.lifetime = 10;
      this.spriteProv = spriteProvider;
      this.setSprite(this.spriteProv.get(this.age, this.lifetime));
      assert Minecraft.getInstance().player != null;
      if (Minecraft.getInstance().player.position().distanceTo(new Vec3(x, y, z)) < 6.0) {
         this.alpha_ctrl -= 0.8F;
      }

      this.alpha = this.alpha_ctrl * MasEffects.manager.getConfig().PearlTrailOpacity;
   }

   public void tick() {
      super.tick();
      this.setSprite(this.spriteProv.get(this.age, this.lifetime));
      this.quadSize = (1.0F - (float)this.age / this.lifetime) * 0.15F;
      this.alpha_ctrl -= 0.1F;
      assert Minecraft.getInstance().player != null;
      if (Minecraft.getInstance().player.position().distanceTo(new Vec3(this.x, this.y, this.z)) < 6.0) {
         this.alpha_ctrl -= 0.8F;
      }

      this.alpha = this.alpha_ctrl * MasEffects.manager.getConfig().PearlTrailOpacity;
   }

   protected @NonNull Layer getLayer() {
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
              @NonNull SimpleParticleType parameters, @NonNull ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, @NonNull RandomSource random
      ) {
         return new EnderPearlTrail(world, x, y, z, this.spriteProvider, velocityX, velocityY, velocityZ);
      }
   }
}
