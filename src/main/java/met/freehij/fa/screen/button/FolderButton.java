package met.freehij.fa.screen.button;

import met.freehij.fa.FrameAnimationClient;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class FolderButton extends StyledButton {
    public FolderButton(int id, int x, int y) {
        super(id, x, y, 10, 10, "");
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        super.drawButton(minecraft, mouseX, mouseY);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, FrameAnimationClient.mc.renderEngine.getTexture("/assets/fa/textures/gui/folder.png"));
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glScalef(1f/32, 1f/32, 1);
        if (this.hovered(mouseX, mouseY)) {
            GL11.glColor3f(0.24705882352941178F, 0.24705882352941178F, 0.1568627450980392F);
        } else {
            GL11.glColor3f(0.24705882352941178F, 0.24705882352941178F, 0.24705882352941178F);
        }
        this.drawTexturedModalRect((this.xPosition + 2) * 32, (this.yPosition + 2) * 32, 0, 0, 256, 256);
        if (this.hovered(mouseX, mouseY)) {
            GL11.glColor3f(1.0F, 1.0F, 0.6275F);
        } else {
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
        }
        this.drawTexturedModalRect((this.xPosition + 1) * 32, (this.yPosition + 1) * 32, 0, 0, 256, 256);
        GL11.glScalef(32, 32, 1);
        GL11.glDisable(GL11.GL_BLEND);
    }
}
