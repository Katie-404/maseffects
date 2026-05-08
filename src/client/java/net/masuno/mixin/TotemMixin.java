package net.masuno.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.masuno.events.TotemEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.TickablePacketListener;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.client.multiplayer.CommonListenerCookie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPacketListener.class)
public abstract class TotemMixin extends ClientCommonPacketListenerImpl implements ClientGamePacketListener, TickablePacketListener {
   @Shadow
   private ClientLevel level;

   protected TotemMixin(Minecraft client, Connection connection, CommonListenerCookie connectionState) {
      super(client, connection, connectionState);
   }

   @Inject(method = "handleEntityEvent", at = @At("HEAD"))
   private void onEntityTotem(ClientboundEntityEventPacket packet, CallbackInfo ci) {
      PacketUtils.ensureRunningOnSameThread(packet, this, this.minecraft.packetProcessor());
      Entity entity = packet.getEntity(this.level);
      if (entity != null && packet.getEventId() == 35) {
         TotemEvent.run(entity);
      }
   }
}
