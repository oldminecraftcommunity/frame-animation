package met.freehij.fa.mixin;

import met.freehij.fa.network.packet.AnimationDataPacket;
import met.freehij.fa.network.packet.DropAnimationPacket;
import met.freehij.fa.network.packet.ServerSupportNotificationPacket;
import net.minecraft.src.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Packet.class)
public abstract class PacketInitializer {
    @Shadow
    static void addIdClassMapping(int id, boolean s2c, boolean c2s, Class<?> PacketClass) {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void clinit(CallbackInfo ci) {
        addIdClassMapping(97, true, false, ServerSupportNotificationPacket.class);
        addIdClassMapping(98, true, true, AnimationDataPacket.class);
        addIdClassMapping(99, true, true, DropAnimationPacket.class);
    }
}
