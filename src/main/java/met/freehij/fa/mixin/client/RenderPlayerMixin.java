package met.freehij.fa.mixin.client;

import met.freehij.fa.util.client.AnimationUtilsClient;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderPlayer.class)
public class RenderPlayerMixin {
    @Inject(method = "doRender(Lnet/minecraft/src/EntityPlayer;DDDFF)V", at = @At("HEAD"))
    public void doRender(EntityPlayer player, double e, double f, double g, float h, float k, CallbackInfo ci) {
        AnimationUtilsClient.currentRenderingEid = player.entityId;
    }
}
