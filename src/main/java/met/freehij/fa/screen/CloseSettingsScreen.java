package met.freehij.fa.screen;

import met.freehij.fa.screen.button.StyledButtonWithText;
import met.freehij.fa.util.client.RenderUtils;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;

public class CloseSettingsScreen extends GuiScreen {
    final SettingsScreen parent;

    public CloseSettingsScreen(SettingsScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        this.controlList.add(new StyledButtonWithText(1, this.width / 2 - 155, this.height / 2 + 10, 150, 16,
                "No, undo the changes"));
        this.controlList.add(new StyledButtonWithText(2, this.width / 2 + 5, this.height / 2 + 10, 150, 16,
                "Yes, save all the changes"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        RenderUtils.drawCenteredString(
                "You some have unsaved changes. Do you wish to save them?",
                this.width / 2, this.height / 2 - 10, 0xffffff
        );
    }

    @Override
    protected void keyTyped(char c, int i) { }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 2) this.parent.saveSettingsFromButtons();
        mc.displayGuiScreen(this.parent.parent);
    }
}
