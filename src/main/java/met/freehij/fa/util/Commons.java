package met.freehij.fa.util;

import net.fabricmc.loader.impl.FabricLoaderImpl;

public class Commons {
    public static final String MOD_VERSION =
            FabricLoaderImpl.INSTANCE.getModContainer("fa").get().getMetadata().getVersion().toString();
    public static final int PROTOCOL_VERSION = 3;
}
