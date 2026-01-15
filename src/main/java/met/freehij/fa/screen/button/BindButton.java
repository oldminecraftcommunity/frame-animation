package met.freehij.fa.screen.button;

import met.freehij.fa.FrameAnimationClient;
import met.freehij.fa.screen.SettingsScreen;
import org.lwjgl.input.Keyboard;

public class BindButton extends SettingButton {
    public String key;

    public BindButton(int id, int x, int y, int width, int height, String settingName,
                      String description) {
        super(id, x, y, width, height, Keyboard.getKeyName((int) FrameAnimationClient.settings.get(settingName)), settingName,
                description);
        this.key = this.displayString;
    }

    @Override
    public Object getValue() {
        return Keyboard.getKeyIndex(this.key);
    }

    @Override
    public void onClick() {
        super.onClick();
        SettingsScreen.listeningKeyBind = this;
        this.displayString = "...";
    }
}
