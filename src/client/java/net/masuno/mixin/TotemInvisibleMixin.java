package net.masuno.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.config.MasConfig;
import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.TotemParticle;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(TotemParticle.class)
public abstract class TotemInvisibleMixin extends AnimatedParticle {
    protected TotemInvisibleMixin(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider, float upwardsAcceleration){
        super(world,x,y,z,spriteProvider,upwardsAcceleration);
    }
    @Inject(at = @At("TAIL"), method = "<init>")
    private void injectAlpha(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider, CallbackInfo ci
    ) {if (MasConfig.INSTANCE.CustomTotemEffect) {
        this.alpha = 0.0F;
    }}
}