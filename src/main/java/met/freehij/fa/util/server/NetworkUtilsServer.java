package met.freehij.fa.util.server;

import met.freehij.fa.mixin.acessor.server.NetServerHandlerAccessor;
import met.freehij.fa.network.packet.AnimationDataPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;

@Environment(EnvType.SERVER)
public class NetworkUtilsServer {
    public static void broadcastAnimationPacket(NetHandler netHandler, AnimationDataPacket packet) {
        broadcastPacket(netHandler, new AnimationDataPacket(packet,
                ((NetServerHandlerAccessor) netHandler).getPlayerEntity().entityId));
    }

    public static void broadcastPacket(NetHandler netHandler, Packet packet) {
        EntityPlayerMP player = ((NetServerHandlerAccessor) netHandler).getPlayerEntity();
        ((MinecraftServer) FabricLoaderImpl.INSTANCE.getGameInstance()).method_2165(player.dimension)
                .method_1668(player, packet);
    }
}
