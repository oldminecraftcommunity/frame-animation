package met.freehij.fa.mixin.server;

import met.freehij.fa.FrameAnimationServer;
import net.minecraft.src.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class NetworkManagerMixin {
    @Inject(method = "networkShutdown", at = @At("HEAD"))
    private void networkShutdown(CallbackInfo ci) {
        FrameAnimationServer.uncompatClients.remove(this);
    }
}
