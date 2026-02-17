package met.freehij.fa.mixin.server;

import met.freehij.fa.FrameAnimationServer;
import met.freehij.fa.network.packet.AnimationDataPacket;
import met.freehij.fa.network.packet.DropAnimationPacket;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetServerHandler.class)
public class NetServerHandlerMixin {
    @Shadow
    public NetworkManager netManager;

    @Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true)
    private void sendPacket(Packet packet, CallbackInfo ci) {
        if ((packet instanceof DropAnimationPacket || packet instanceof AnimationDataPacket)
                && FrameAnimationServer.uncompatClients.contains(netManager)) {
            ci.cancel();
        }
    }
}
