package met.freehij.fa.network.packet;

import met.freehij.fa.animation.Animation;
import met.freehij.fa.animation.BodyPart;
import met.freehij.fa.util.client.AnimationUtilsClient;
import met.freehij.fa.util.server.NetworkUtilsServer;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

public class AnimationDataPacket extends Packet {
    Animation animation;
    int eid;

    public AnimationDataPacket() {}

    public AnimationDataPacket(AnimationDataPacket packet, int eid) {
        this.animation = packet.animation;
        this.eid = eid;
    }

    public AnimationDataPacket(Animation animation, int eid) {
        this.animation = animation;
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream) {
        try {
            this.eid = dataInputStream.readInt();
            String name = readString(dataInputStream, 128);
            boolean interruptible = dataInputStream.readBoolean();
            boolean resetArms = dataInputStream.readBoolean();
            boolean interpolate = dataInputStream.readBoolean();
            List<Animation.Frame> frames = new ArrayList<>();
            while (dataInputStream.readByte() != -2) {
                Map<BodyPart, Float> data = new HashMap<>();
                int dur = dataInputStream.readInt();
                for (byte b = dataInputStream.readByte(); b != -1; b = dataInputStream.readByte())
                    data.put(BodyPart.values()[b], dataInputStream.readFloat());
                frames.add(new Animation.Frame(dur, data));
            }
            this.animation = new Animation(name, interruptible, resetArms, interpolate, frames.toArray(new Animation.Frame[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeInt(this.eid);
            String name = this.animation.name().length() > 128 ? this.animation.name().substring(0, 128) : this.animation.name();
            writeString(name, dataOutputStream);
            dataOutputStream.writeBoolean(this.animation.interruptible());
            dataOutputStream.writeBoolean(this.animation.resetArms());
            dataOutputStream.writeBoolean(this.animation.interpolate());
            for (Animation.Frame frame : this.animation.frames()) {
                dataOutputStream.writeByte(1);
                dataOutputStream.writeInt(frame.duration());
                for (BodyPart key : frame.data().keySet()) {
                    dataOutputStream.writeByte(key.ordinal());
                    dataOutputStream.writeFloat(frame.data().get(key));
                }
                dataOutputStream.writeByte(-1);
            }
            dataOutputStream.writeByte(-2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void processPacket(NetHandler netHandler) {
        if (netHandler.isServerHandler()) {
            NetworkUtilsServer.broadcastAnimationPacket(netHandler, this);
        } else {
            AnimationUtilsClient.handleAnimation(this.animation, this.eid);
        }
    }



    @Override
    public int getPacketSize() {
        return 4 + 2 + Math.min(128, animation.name().length()) + 3 +
                Arrays.stream(animation.frames()).mapToInt(frame -> 1 + 4 + 5 * frame.data().size() + 1).sum() + 1;
    }
}
