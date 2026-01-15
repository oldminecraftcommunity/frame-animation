package met.freehij.fa.screen;

import met.freehij.fa.FrameAnimationClient;
import met.freehij.fa.screen.button.BindButton;
import met.freehij.fa.screen.button.BooleanSettingButton;
import met.freehij.fa.screen.button.SettingButton;
import met.freehij.fa.screen.button.StyledButtonWithText;
import met.freehij.fa.util.client.RenderUtils;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import org.lwjgl.input.Keyboard;

public class SettingsScreen extends ModScreen {
    public static BindButton listeningKeyBind = null;
    public final GuiScreen parent;
    public static boolean unsaved = false;

    public SettingsScreen(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {
        this.controlList.add(new StyledButtonWithText(1, 2, 2, 50, 10, this.parent == null ? "Close" : "Back"));
        this.controlList.add(new StyledButtonWithText(2, 54, 2, 50, 10, "Save"));
        this.controlList.add(new BooleanSettingButton(10, this.width / 2 + 5, 17, 75, 14, "toggle-thirdperson",
                "Toggle third person on animation end"));
        this.controlList.add(new BooleanSettingButton(10, this.width / 2 + 5, 33, 75, 14, "walk-in-gui",
                "Walk in mod in GUI"));
        this.controlList.add(new BindButton(11, this.width / 2 + 5, 76, 75, 14, "keybind-open-menu",
                "Open animations menu"));
        this.controlList.add(new BindButton(12, this.width / 2 + 5, 92, 75, 14, "keybind-drop-animation",
                "Stop animation"));
        this.controlList.add(new BindButton(12, this.width / 2 + 5, 108, 75, 14, "keybind-edit-mode",
                "Toggle edit mode (only in GUI)"));
        this.controlList.add(new BindButton(12, this.width / 2 + 5, 124, 75, 14, "keybind-open-settings",
                "Open settings"));
    }

    @Override
    protected void keyTyped(char c, int i) {
        super.keyTyped(c, i);
        if (listeningKeyBind != null) {
            if (i != Keyboard.KEY_ESCAPE) {
                listeningKeyBind.key = Keyboard.getKeyName(i);
            }
            listeningKeyBind.displayString = listeningKeyBind.key;
            listeningKeyBind = null;
        } else {
            if (i == Keyboard.KEY_ESCAPE) {
                if (unsaved) {
                    mc.displayGuiScreen(new CloseSettingsScreen(this));
                } else {
                    mc.displayGuiScreen(this.parent);
                }
            } else if (i == Keyboard.KEY_F5) mc.gameSettings.thirdPersonView = !mc.gameSettings.thirdPersonView;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 1:
                if (unsaved) {
                    unsaved = false;
                    mc.displayGuiScreen(new CloseSettingsScreen(this));
                } else {
                    mc.displayGuiScreen(this.parent);
                }
                break;
            case 2:
                this.saveSettingsFromButtons();
                FrameAnimationClient.saveSettings();
                unsaved = false;
                break;
            default:
                SettingButton button1 = (SettingButton) button;
                button1.onClick();
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        RenderUtils.drawCenteredString("General", this.width / 2, 7, 0xffffff);
        RenderUtils.drawCenteredString("Keybindings", this.width / 2, 66, 0xffffff);
    }

    public void saveSettingsFromButtons() {
        for (Object button : this.controlList) {
            if (button instanceof SettingButton button1) {
                FrameAnimationClient.settings.put(button1.settingName, button1.getValue());
            }
        }
        FrameAnimationClient.saveSettings();
    }
}
