package met.freehij.fa.screen.button;

import met.freehij.fa.FrameAnimationClient;

public class BooleanSettingButton extends SettingButton {
    boolean toggled;

    public BooleanSettingButton(int id, int x, int y, int width, int height, String settingName, String description) {
        super(id, x, y, width, height, "", settingName, description);
        this.toggled = (boolean) FrameAnimationClient.settings.get(settingName);
        this.updateString();
    }

    @Override
    public void onClick() {
        super.onClick();
        this.toggled = !this.toggled;
        updateString();
    }

    public void updateString() {
        this.displayString = this.toggled ? "ON" : "OFF";
    }

    @Override
    public Object getValue() {
        return this.toggled;
    }
}
