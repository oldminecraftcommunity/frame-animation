package met.freehij.fa;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.src.NetHandler;

import java.util.ArrayList;
import java.util.List;

public class FrameAnimationServer implements DedicatedServerModInitializer {
    public static List<NetHandler> uncompatClients = new ArrayList<>();

    @Override
    public void onInitializeServer() {

    }
}
