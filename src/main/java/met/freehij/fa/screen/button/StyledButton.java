package met.freehij.fa.screen.button;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;

public abstract class StyledButton extends GuiButton {
    public StyledButton(int id, int x, int y, int width, int height, String text) {
        super(id, x, y, width, height, text);
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        boolean enabled1 = this.hovered(mouseX, mouseY) && this.enabled;
        int offset = enabled1 ? 1 : 0;
        this.drawRect(
                this.xPosition - offset,
                this.yPosition - offset,
                this.xPosition + this.width + offset,
                this.yPosition + this.height + offset,
                enabled1 ? 0x70000000 : Integer.MIN_VALUE
        );
    }

    public boolean hovered(int mouseX, int mouseY) {
        return mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    public int getHeight() {
        return this.height;
    }
}
