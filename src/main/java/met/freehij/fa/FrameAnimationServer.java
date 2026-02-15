package met.freehij.fa;

import met.freehij.fa.util.Commons;
import met.freehij.fa.util.FileUtils;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.src.NetHandler;
import net.minecraft.src.NetworkManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrameAnimationServer implements DedicatedServerModInitializer {
    public static List<NetworkManager> uncompatClients = new ArrayList<>();
    public static Map<String, Object> serverSettings = new HashMap<>();

    @Override
    public void onInitializeServer() {
        serverSettings.put("allow-vanilla-clients", false);
        loadSettings();
        saveSettings();
    }

    public static void loadSettings() {
        FileUtils.loadSettingsFromFile(Commons.CONFIG_FILE, serverSettings);
    }

    public static void saveSettings() {
        FileUtils.saveSettingsToFile(Commons.CONFIG_FILE, serverSettings);
    }
}
