package met.freehij.fa.screen;

import met.freehij.fa.util.Commons;
import net.minecraft.src.GuiScreen;
import org.lwjgl.input.Keyboard;

public class ModScreen extends GuiScreen {
    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
        if (keyCode == Keyboard.KEY_F5) mc.gameSettings.thirdPersonView = !mc.gameSettings.thirdPersonView;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.fontRenderer.drawStringWithShadow(
                "Frame Animation " + Commons.MOD_VERSION + " (Protocol: " + Commons.PROTOCOL_VERSION + ")",
                2, this.height - 12, 0xaaaaaa);
    }
}
