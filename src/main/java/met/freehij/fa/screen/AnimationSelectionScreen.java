package met.freehij.fa.screen;

import met.freehij.fa.FrameAnimationClient;
import met.freehij.fa.animation.Animation;
import met.freehij.fa.network.packet.AnimationDataPacket;
import met.freehij.fa.screen.button.AnimationButton;
import met.freehij.fa.screen.button.StyledButtonWithText;
import met.freehij.fa.util.client.AnimationUtilsClient;
import met.freehij.fa.util.client.NetworkUtilsClient;
import met.freehij.fa.util.client.RenderUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;
import org.lwjgl.input.Keyboard;

@Environment(EnvType.CLIENT)
public class AnimationSelectionScreen extends ModScreen {
    boolean selectionMode = false;

    @Override
    public void initGui() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int offset = 60;
        for (int i = 0; i < 5; i++) {
            int x = centerX - 25;
            int y = centerY - 25;
            if (i == 0) y -= offset;
            else if (i == 1) x -= offset;
            else if (i == 2) x += offset;
            else if (i == 3) { x -= offset/2; y += offset; }
            else { x += offset/2; y += offset; }
            Animation animation = FrameAnimationClient.getAnimationFileForSlot(i);
            this.controlList.add(new AnimationButton(i, x, y, animation));
        }
        this.controlList.add(new StyledButtonWithText(11, 2, 2, 50, 10, "Edit"));
        this.controlList.add(new StyledButtonWithText(12, 54, 2, 50, 10, "Exit"));
        this.controlList.add(new StyledButtonWithText(13, 106, 2, 50, 10, "Stop"));
        this.controlList.add(new StyledButtonWithText(14, 158, 2, 50, 10, "Settings"));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void actionPerformed(GuiButton button1) {
        if (button1.id < 6) {
            AnimationButton button = (AnimationButton) button1;
            if (this.selectionMode) {
                mc.displayGuiScreen(new FileSelectionScreen(this, button1.id));
            } else {
                if (button.animation != null) {
                    mc.gameSettings.thirdPersonView = true;
                    FrameAnimationClient.stopWalk = button.animation.interruptible();
                    AnimationUtilsClient.handleAnimation(button.animation, mc.thePlayer.entityId);
                    NetworkUtilsClient.sendPacket(new AnimationDataPacket(button.animation, 0));
                }
            }
        } else {
            switch (button1.id) {
                case 11:
                    this.toggleSelectionMode();
                    break;
                case 12:
                    mc.displayGuiScreen(null);
                    break;
                case 13:
                    AnimationUtilsClient.cleanupPlayer(mc.thePlayer.entityId);
                    NetworkUtilsClient.sendDropAnimation();
                    break;
                case 14:
                    mc.displayGuiScreen(new SettingsScreen(this));
                    break;
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (mc.isMultiplayerWorld() && !FrameAnimationClient.serverSupport) RenderUtils.drawCenteredString(
                "§cThis server does not support animations!", this.width / 2, this.height / 2 + 102, 0xFFFFFFFF);
        if (this.selectionMode) RenderUtils.drawCenteredString(
                "You are now in edit mode! Click on selection slot to change it's contents.",
                this.width / 2,this.height / 2 - 102, 0xFFFFFFFF);
    }

    @Override
    protected void keyTyped(char c, int i) {
        if (i == (int) FrameAnimationClient.settings.get("keybind-drop-animation")) {
            AnimationUtilsClient.cleanupPlayer(FrameAnimationClient.mc.thePlayer.entityId);
            NetworkUtilsClient.sendDropAnimation();
        } else if (i == (int) FrameAnimationClient.settings.get("keybind-open-settings")) {
            mc.displayGuiScreen(new SettingsScreen(this));
        } else if (i == Keyboard.KEY_ESCAPE) {
            if (this.selectionMode) this.selectionMode = false;
            else mc.displayGuiScreen(null);
        } else if (i == Keyboard.KEY_F5) mc.gameSettings.thirdPersonView = !mc.gameSettings.thirdPersonView;
        else if (i == (int) FrameAnimationClient.settings.get("keybind-edit-mode"))
            this.toggleSelectionMode();
    }

    @Override
    public void onGuiClosed() {
        this.selectionMode = false;
    }

    public void toggleSelectionMode() {
        this.selectionMode = !this.selectionMode;
        for (Object obj : this.controlList) {
            if (obj instanceof AnimationButton animationButton) {
                if (animationButton.animation == null) animationButton.enabled = this.selectionMode;
            }
        }
    }
}
