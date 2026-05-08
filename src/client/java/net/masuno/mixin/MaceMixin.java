package net.masuno.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.events.PlayerAttackManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientLevel.class)
public abstract class MaceMixin {
   @Inject(method = "levelEvent", at = @At("HEAD"))
   private void MaceAttack(Entity source, int eventId, BlockPos pos, int data, CallbackInfo ci) {
      if (eventId == 2013) {
         Vec3 vec = pos.getCenter().add(0.0, 0.5, 0.0);
         PlayerAttackManager.SlamEffect(vec.x(), vec.y(), vec.z(), 5.0);
      }
   }
}
