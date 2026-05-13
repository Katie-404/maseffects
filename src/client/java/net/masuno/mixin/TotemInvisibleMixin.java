package net.masuno.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.MasEffects;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TotemParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(TotemParticle.class)
public abstract class TotemInvisibleMixin extends SimpleAnimatedParticle {
    protected TotemInvisibleMixin(ClientLevel world, double x, double y, double z, SpriteSet spriteProvider, float upwardsAcceleration){
        super(world,x,y,z,spriteProvider,upwardsAcceleration);
    }
    @Inject(at = @At("TAIL"), method = "<init>")
    private void injectAlpha(ClientLevel level, double x, double y, double z, double xa, double ya, double za, SpriteSet sprites, CallbackInfo ci
    ) {if (MasEffects.manager.getConfig().CustomTotemEffect) {
        this.alpha = 0.0F;
    }}
}