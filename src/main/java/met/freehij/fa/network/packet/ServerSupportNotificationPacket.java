package met.freehij.fa.network.packet;

import met.freehij.fa.FrameAnimationClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ServerSupportNotificationPacket extends Packet {
    public ServerSupportNotificationPacket() {
    }

    @Environment(EnvType.CLIENT)
    public void processPacket(NetHandler arg) {
        FrameAnimationClient.serverSupport = true;
    }

    public void readPacketData(DataInputStream dataInputStream) {
    }

    public void writePacketData(DataOutputStream dataOutputStream) {
    }

    public int getPacketSize() {
        return 0;
    }
}
