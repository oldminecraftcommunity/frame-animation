package met.freehij.fa.mixin.client;

import met.freehij.fa.FrameAnimationClient;
import met.freehij.fa.util.client.AnimationUtilsClient;
import met.freehij.fa.util.client.NetworkUtilsClient;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "runTick", at = @At("HEAD"))
    public void runTick(CallbackInfo ci) {
        AnimationUtilsClient.handleTick();
    }
}
