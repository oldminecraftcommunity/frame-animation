package met.freehij.fa.mixin;

import met.freehij.fa.FrameAnimationServer;
import met.freehij.fa.util.Commons;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet1Login;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.DataOutputStream;

@Mixin(Packet1Login.class)
public class CompatChecker {
    @Shadow
    public byte dimension;

    @Environment(EnvType.CLIENT)
    @Inject(method = "writePacketData", at = @At("HEAD"))
    public void writePacketData(DataOutputStream dataOutputStream, CallbackInfo ci) {
        this.dimension = Commons.PROTOCOL_VERSION;
    }

    @Environment(EnvType.SERVER)
    @Inject(method = "processPacket", at = @At("HEAD"))
    public void readPacketData(NetHandler netHandler, CallbackInfo ci) {
        if (this.dimension != Commons.PROTOCOL_VERSION) FrameAnimationServer.uncompatClients.add(netHandler);
    }
}
