package met.freehij.fa.screen;

import met.freehij.fa.FrameAnimationClient;
import met.freehij.fa.screen.button.FolderButton;
import met.freehij.fa.screen.button.StyledButtonWithText;
import net.minecraft.src.GuiButton;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class FileSelectionScreen extends ModScreen {
    final AnimationSelectionScreen parent;
    final int slotId;

    public FileSelectionScreen(AnimationSelectionScreen parent, int slotId) {
        this.parent = parent;
        this.slotId = slotId;
    }

    @Override
    public void initGui() {
        this.controlList.add(new FolderButton(1, 106, 2));
        this.controlList.add(new StyledButtonWithText(2, 2, 2, 50, 10, "Back"));
        this.controlList.add(new StyledButtonWithText(3, 54, 2, 50, 10, "Remove"));
        AtomicInteger i = new AtomicInteger(0);
        try (Stream<Path> stream = Files.list(FrameAnimationClient.ANIMATIONS_FOLDER)) {
            stream.filter(Files::isRegularFile).forEach(path -> {
                String filename = path.getFileName().toString();
                if (filename.endsWith(".fa")) {
                    this.controlList.add(new StyledButtonWithText(i.get() + 10, this.width / 4, i.get() * 16 + 14,
                            this.width / 2, 14, filename));
                }
                i.getAndIncrement();
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void keyTyped(char c, int i) {
        super.keyTyped(c, i);
        if (i == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(this.parent);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id >= 10) {
            FrameAnimationClient.setAnimationFileInSlot(this.slotId, button.displayString);
        } else if (button.id == 3) {
            FrameAnimationClient.setAnimationFileInSlot(this.slotId, "");
        }
        if (button.id == 1) Sys.openURL(String.valueOf(Path.of("animations")));
        else mc.displayGuiScreen(this.parent);
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        int dwheel = Mouse.getEventDWheel();
        if (dwheel != 0) {
            boolean first = isFirstOffScreen();
            boolean last = isLastOffScreen();
            for (Object obj : this.controlList) {
                if (obj instanceof FolderButton) continue;
                StyledButtonWithText button = (StyledButtonWithText) obj;
                if (button.id >= 10) {
                    if (dwheel > 0 && first) {
                        button.yPosition += 10;
                    } else if (dwheel < 0 && last) {
                        button.yPosition -= 10;
                    }
                }
            }
        }
    }

    boolean isFirstOffScreen() {
        StyledButtonWithText button = (StyledButtonWithText) this.controlList.get(3);
        return button != null && button.yPosition - 14 < 0;
    }

    boolean isLastOffScreen() {
        StyledButtonWithText button = (StyledButtonWithText) this.controlList.get(this.controlList.size() - 1);
        return button != null && button.yPosition + button.getHeight() > this.height - 14;
    }
}
