package met.freehij.fa.util;

import net.fabricmc.loader.impl.FabricLoaderImpl;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Commons {
    public static final String MOD_VERSION =
            FabricLoaderImpl.INSTANCE.getModContainer("fa").get().getMetadata().getVersion().toString();
    public static final int PROTOCOL_VERSION = 3;
    public static final Path CONFIG_FILE = Paths.get("config/animations.properties");
}
