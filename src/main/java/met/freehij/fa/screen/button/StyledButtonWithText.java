package met.freehij.fa.screen.button;

import met.freehij.fa.util.client.RenderUtils;
import net.minecraft.client.Minecraft;

public class StyledButtonWithText extends StyledButton {
    public StyledButtonWithText(int id, int x, int y, int width, int height, String text) {
        super(id, x, y, width, height, text);
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        super.drawButton(minecraft, mouseX, mouseY);
        RenderUtils.drawCenteredString(
                this.displayString,
                this.xPosition + this.width / 2,
                this.yPosition + this.height / 2 + 1,
                this.enabled ? this.hovered(mouseX, mouseY) ? 0xffffa0 : 0xffffff : 0x888888
        );
    }
}
