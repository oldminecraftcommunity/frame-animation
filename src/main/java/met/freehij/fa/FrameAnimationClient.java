package met.freehij.fa;

import met.freehij.fa.animation.Animation;
import met.freehij.fa.util.Commons;
import met.freehij.fa.util.FileUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FrameAnimationClient implements ClientModInitializer {
    public static final Path ANIMATIONS_FOLDER = Path.of("animations");
    public static boolean serverSupport = false;
    public static final Minecraft mc = (Minecraft) FabricLoaderImpl.INSTANCE.getGameInstance();
    public static boolean stopWalk = false;
    public static Map<String, Object> clientSettings = new HashMap<>();

    @Override
    public void onInitializeClient() {
        clientSettings.put("toggle-thirdperson", false);
        clientSettings.put("walk-in-gui", true);
        clientSettings.put("keybind-open-menu", Keyboard.KEY_G);
        clientSettings.put("keybind-drop-animation", Keyboard.KEY_Z);
        clientSettings.put("keybind-open-settings", Keyboard.KEY_X);
        clientSettings.put("filename-slot0", "");
        clientSettings.put("filename-slot1", "");
        clientSettings.put("filename-slot2", "");
        clientSettings.put("filename-slot3", "");
        clientSettings.put("filename-slot4", "");
        clientSettings.put("keybind-edit-mode", Keyboard.KEY_R);
        loadSettings();
        saveSettings();
        try {
            Files.createDirectories(ANIMATIONS_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadSettings() {
        FileUtils.loadSettingsFromFile(Commons.CONFIG_FILE, clientSettings);
    }

    public static void saveSettings() {
        FileUtils.saveSettingsToFile(Commons.CONFIG_FILE, clientSettings);
    }

    public static void setAnimationFileInSlot(int slot, String fileName) {
        clientSettings.put("filename-slot" + slot, fileName);
        saveSettings();
    }

    public static Animation getAnimationFileForSlot(int slot) {
        try {
            String fileName = (String) clientSettings.get("filename-slot" + slot);
            if (!fileName.trim().isBlank())
                return FileUtils.loadFromFile(Paths.get(ANIMATIONS_FOLDER.toString(), fileName));
        } catch (Exception e) {
            setAnimationFileInSlot(slot, "");
            saveSettings();
        }
        return null;
    }
}
