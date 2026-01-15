package met.freehij.fa;

import met.freehij.fa.animation.Animation;
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
    static final Path CONFIG_FILE = Paths.get("config/animations.properties");
    public static boolean serverSupport = false;
    public static final Minecraft mc = (Minecraft) FabricLoaderImpl.INSTANCE.getGameInstance();
    public static boolean stopWalk = false;
    public static Map<String, Object> settings = new HashMap<>();

    @Override
    public void onInitializeClient() {
        settings.put("toggle-thirdperson", false);
        settings.put("walk-in-gui", true);
        settings.put("keybind-open-menu", Keyboard.KEY_G);
        settings.put("keybind-drop-animation", Keyboard.KEY_Z);
        settings.put("keybind-open-settings", Keyboard.KEY_X);
        settings.put("filename-slot1", "");
        settings.put("filename-slot2", "");
        settings.put("filename-slot3", "");
        settings.put("filename-slot4", "");
        settings.put("filename-slot5", "");
        settings.put("keybind-edit-mode", Keyboard.KEY_R);
        loadSettings();
        saveSettings();
        try {
            Files.createDirectories(ANIMATIONS_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadSettings() {
        FileUtils.loadSettings(CONFIG_FILE, settings);
    }

    public static void saveSettings() {
        FileUtils.saveSettings(CONFIG_FILE, settings);
    }

    public static void setAnimationFileInSlot(int slot, String fileName) {
        settings.put("filename-slot" + slot, fileName);
        saveSettings();
    }

    public static Animation getAnimationFileForSlot(int slot) {
        try {
            String fileName = (String) settings.get("filename-slot" + slot);
            if (!fileName.trim().isBlank())
                return FileUtils.loadFromFile(Paths.get(ANIMATIONS_FOLDER.toString(), fileName));
        } catch (Exception e) {
            setAnimationFileInSlot(slot, "");
            saveSettings();
        }
        return null;
    }
}
