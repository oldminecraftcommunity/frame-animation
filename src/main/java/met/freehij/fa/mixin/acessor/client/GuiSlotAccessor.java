package met.freehij.fa.mixin.acessor.client;

import net.minecraft.src.GuiSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiSlot.class)
public interface GuiSlotAccessor {
    @Accessor("selectedElement")
    int getSelectedElement();

    @Accessor("selectedElement")
    void setSelectedElement(int selectedElement);
}
