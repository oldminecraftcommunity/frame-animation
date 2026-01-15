package met.freehij.fa.util;

import met.freehij.fa.animation.Animation;
import met.freehij.fa.animation.BodyPart;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileUtils {
    static int l = 0;

    public static Animation loadFromFile(Path filePath) throws FrameAnimationSyntaxException {
        try {
            if (!Files.exists(filePath)) throw new FileNotFoundException(filePath.toString());
            List<Animation.Frame> frames = new ArrayList<>();
            Map<BodyPart, Float> frameData = null;
            int duration = 0;
            boolean interruptible = false;
            boolean resetArms = false;
            boolean interpolate = false;
            String name = "Unnamed";
            for (String line : Files.readAllLines(filePath)) {
                l++;
                line = line.trim();
                if (line.isEmpty()) continue;
                if (line.toLowerCase().startsWith("name=")) {
                    name = line.substring(5);
                } else if (line.toLowerCase().startsWith("frame ")) {
                    if (frameData != null) frames.add(new Animation.Frame(duration, frameData));
                    duration = Integer.parseInt(line.substring(6).strip());
                    frameData = new HashMap<>();
                } else if (line.toLowerCase().startsWith("interruptible")) {
                    interruptible = true;
                } else if (line.toLowerCase().startsWith("resetarms")) {
                    resetArms = true;
                } else if (line.toLowerCase().startsWith("interpolate")) {
                    interpolate = true;
                } else {
                    String[] parts = line.split("=");
                    BodyPart part = BodyPart.valueOf(parts[0].replace('.', '_').toUpperCase());
                    frameData.put(part, Float.parseFloat(parts[1]));
                }
            }
            if (frameData != null) frames.add(new Animation.Frame(duration, frameData));
            return new Animation(name, interruptible, resetArms, interpolate, frames.toArray(new Animation.Frame[0]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new FrameAnimationSyntaxException(e.getMessage(), filePath, l);
        } finally {
            l = 0;
        }
    }

    public static class FrameAnimationSyntaxException extends Exception {
        final Path filePath;
        final int line;

        public FrameAnimationSyntaxException(String message, Path filePath, int line) {
            super(message);
            this.filePath = filePath;
            this.line = line;
        }

        @Override
        public String toString() {
            return "Invalid syntax at line " + this.line + " in file " + filePath.toString();
        }
    }

    public static void loadSettings(Path filePath, Map<String, Object> settings) {
        Properties props = new Properties();
        try {
            props.load(Files.newInputStream(filePath));
            for (String key : props.stringPropertyNames()) {
                settings.put(key, parseValue(props.getProperty(key)));
            }
        } catch (IOException e) {
            System.err.println("Could not load settings from " + filePath + "!");
            e.printStackTrace();
        }
    }

    public static void saveSettings(Path filePath, Map<String, Object> settings) {
        Properties props = new Properties();
        for (Map.Entry<String, Object> entry : settings.entrySet()) {
            props.setProperty(entry.getKey(), entry.getValue().toString());
        }
        try {
            Files.createDirectories(filePath.getParent());
            props.store(Files.newOutputStream(filePath), null);
        } catch (IOException e) {
            System.err.println("Could not save settings to " + filePath + "!");
            e.printStackTrace();
        }
    }

    private static Object parseValue(String value) {
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return Boolean.parseBoolean(value);
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return value;
        }
    }
}
