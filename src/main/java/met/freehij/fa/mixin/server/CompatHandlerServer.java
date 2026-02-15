package met.freehij.fa.mixin.server;

import met.freehij.fa.FrameAnimationServer;
import met.freehij.fa.network.packet.ServerSupportNotificationPacket;
import net.minecraft.src.NetLoginHandler;
import net.minecraft.src.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetLoginHandler.class)
public class CompatHandlerServer {
    @Shadow
    public NetworkManager netManager;
    @Shadow
    public void method_417(String reason) { }

    @Inject(method = "handleLogin", at = @At("HEAD"))
    private void handleLogin(CallbackInfo ci) {
        if (FrameAnimationServer.uncompatClients.contains(this.netManager)) {
            if ((boolean) FrameAnimationServer.serverSettings.get("allow-vanilla-clients")) return;
            method_417("You need to install/update Frame Animation mod to join this server.");
            FrameAnimationServer.uncompatClients.remove(this.netManager);
        } else {
            this.netManager.addToSendQueue(new ServerSupportNotificationPacket());
        }
    }
}

