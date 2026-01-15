package met.freehij.fa.util.client;

import met.freehij.fa.FrameAnimationClient;
import met.freehij.fa.network.packet.DropAnimationPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.Packet;

@Environment(EnvType.CLIENT)
public class NetworkUtilsClient {
    public static void sendDropAnimation() {
        sendPacket(new DropAnimationPacket(FrameAnimationClient.mc.thePlayer.entityId));
    }

    public static void sendPacket(Packet packet) {
        if (!FrameAnimationClient.serverSupport) return;
        NetClientHandler netClientHandler = FrameAnimationClient.mc.getSendQueue();
        if (netClientHandler == null) return;
        netClientHandler.addToSendQueue(packet);
    }
}
