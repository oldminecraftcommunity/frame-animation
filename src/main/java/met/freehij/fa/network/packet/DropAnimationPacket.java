package met.freehij.fa.network.packet;

import met.freehij.fa.mixin.acessor.server.NetServerHandlerAccessor;
import met.freehij.fa.util.client.AnimationUtilsClient;
import met.freehij.fa.util.server.NetworkUtilsServer;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DropAnimationPacket extends Packet {
    int eid;

    public DropAnimationPacket() {}

    public DropAnimationPacket(int eid) {
        this.eid = eid;
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream) {
        try {
            this.eid = dataInputStream.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeInt(this.eid);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void processPacket(NetHandler netHandler) {
        if (netHandler.isServerHandler()) {
            NetworkUtilsServer.broadcastPacket(netHandler,
                    new DropAnimationPacket(((NetServerHandlerAccessor) netHandler).getPlayerEntity().entityId));
        } else {
            AnimationUtilsClient.cleanupPlayer(this.eid);
        }
    }

    @Override
    public int getPacketSize() {
        return 4;
    }
}
