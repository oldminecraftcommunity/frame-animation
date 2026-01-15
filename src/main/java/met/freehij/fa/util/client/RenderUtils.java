package met.freehij.fa.util.client;

import met.freehij.fa.FrameAnimationClient;
import net.minecraft.src.FontRenderer;

public class RenderUtils {
    public static void drawCenteredString(String text, int x, int y, int color) {
        FontRenderer fontRenderer = FrameAnimationClient.mc.fontRenderer;
        fontRenderer.drawStringWithShadow(
                text,
                x - fontRenderer.getStringWidth(text) / 2,
                y - 5,
                color
        );
    }
}
