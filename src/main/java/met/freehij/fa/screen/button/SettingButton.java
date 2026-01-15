package met.freehij.fa.screen.button;

import met.freehij.fa.screen.SettingsScreen;
import met.freehij.fa.util.client.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;

public abstract class SettingButton extends StyledButtonWithText {
    public final String settingName;
    final String description;

    public SettingButton(int id, int x, int y, int width, int height, String text, String settingName,
                         String description) {
        super(id, x, y, width, height, text);
        this.settingName = settingName;
        this.description = description;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        super.drawButton(minecraft, mouseX, mouseY);
        FontRenderer fr = minecraft.fontRenderer;
        RenderUtils.drawCenteredString(this.description,
                this.xPosition - (fr.getStringWidth(this.description) / 2) - 5, this.yPosition + this.height / 2 + 1,
                0xffffff
        );
    }

    public void onClick() {
        SettingsScreen.unsaved = true;
    }

    public abstract Object getValue();
}
