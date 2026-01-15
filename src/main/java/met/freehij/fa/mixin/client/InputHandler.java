package met.freehij.fa.mixin.client;

import met.freehij.fa.FrameAnimationClient;
import met.freehij.fa.screen.AnimationSelectionScreen;
import met.freehij.fa.screen.ModScreen;
import met.freehij.fa.screen.SettingsScreen;
import met.freehij.fa.util.client.AnimationUtilsClient;
import met.freehij.fa.util.client.NetworkUtilsClient;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MovementInputFromOptions.class)
public class InputHandler extends MovementInput {
    @Unique
    private final static Minecraft mc = FrameAnimationClient.mc;
    @Shadow
    private boolean[] movementKeyStates;

    @Inject(method = "checkKeyForMovementInput", at = @At("HEAD"))
    public void checkKeyForMovementInput(int key, boolean keyDown, CallbackInfo ci) {
        if (keyDown) {
            if (key == (int) FrameAnimationClient.settings.get("keybind-open-menu")) {
                Minecraft mc = ((Minecraft) FabricLoaderImpl.INSTANCE.getGameInstance());
                mc.displayGuiScreen(new AnimationSelectionScreen());
            }
            if (key == (int) FrameAnimationClient.settings.get("keybind-drop-animation")) {
                AnimationUtilsClient.cleanupPlayer(FrameAnimationClient.mc.thePlayer.entityId);
                NetworkUtilsClient.sendDropAnimation();
            }
            if (key == (int) FrameAnimationClient.settings.get("keybind-open-settings")) {
                mc.displayGuiScreen(new SettingsScreen(null));
            }
        }
    }

    @Inject(method = "updatePlayerMoveState", at = @At("HEAD"))
    public void updatePlayerMoveState(EntityPlayer player, CallbackInfo ci) {
        if (mc.currentScreen instanceof ModScreen) {
            if ((boolean) FrameAnimationClient.settings.get("walk-in-gui") && !FrameAnimationClient.stopWalk) {
                GameSettings gameSettings = mc.gameSettings;
                movementKeyStates[0] = Keyboard.isKeyDown(gameSettings.keyBindForward.keyCode);
                movementKeyStates[1] = Keyboard.isKeyDown(gameSettings.keyBindBack.keyCode);
                movementKeyStates[2] = Keyboard.isKeyDown(gameSettings.keyBindLeft.keyCode);
                movementKeyStates[3] = Keyboard.isKeyDown(gameSettings.keyBindRight.keyCode);
                movementKeyStates[4] = Keyboard.isKeyDown(gameSettings.keyBindJump.keyCode);
                movementKeyStates[5] = Keyboard.isKeyDown(gameSettings.keyBindSneak.keyCode);
            } else {
                for (int i = 0; i < 6; i++) movementKeyStates[i] = false;
            }
        } else {
            AnimationUtilsClient.PlayerAnimationState state = AnimationUtilsClient.playerAnimations.get(player.entityId);
            if (state == null) return;
            if (state.interruptible &&
                    (this.moveForward != 0.0F || this.moveStrafe != 0.0F || this.jump || this.sneak)) {
                AnimationUtilsClient.cleanupPlayer(player.entityId);
                NetworkUtilsClient.sendDropAnimation();
            }
        }
    }
}
