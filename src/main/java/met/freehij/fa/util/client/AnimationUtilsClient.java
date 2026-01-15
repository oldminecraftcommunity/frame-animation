package met.freehij.fa.util.client;

import met.freehij.fa.FrameAnimationClient;
import met.freehij.fa.animation.Animation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.*;

@Environment(EnvType.CLIENT)
public class AnimationUtilsClient {
    public static final Map<Integer, PlayerAnimationState> playerAnimations = new HashMap<>();
    public static int currentRenderingEid = -1;

    public static class PlayerAnimationState {
        public boolean interruptible = false;
        public boolean resetArms = false;
        public boolean interpolate = false;
        public long ticks = 0;
        public List<Animation.Frame> framesToRender = new ArrayList<>();
        public boolean active = false;
    }

    public static void handleAnimation(Animation animation, int eid) {
        PlayerAnimationState state = playerAnimations.computeIfAbsent(eid, k -> new PlayerAnimationState());
        state.ticks = 0;
        state.framesToRender.clear();
        state.framesToRender.addAll(List.of(animation.frames()));
        state.active = true;
        state.interruptible = animation.interruptible();
        state.resetArms = animation.resetArms();
        state.interpolate = animation.interpolate();
    }

    public static void handleTick() {
        Iterator<Map.Entry<Integer, PlayerAnimationState>> iterator = playerAnimations.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, PlayerAnimationState> entry = iterator.next();
            PlayerAnimationState state = entry.getValue();
            if (!state.active || state.framesToRender.isEmpty()) {
                iterator.remove();
                continue;
            }
            state.ticks++;
            while (!state.framesToRender.isEmpty() && state.ticks > state.framesToRender.get(0).duration()) {
                state.ticks = 0;
                state.framesToRender.remove(0);
            }
            if (state.framesToRender.isEmpty()) {
                state.active = false;
                FrameAnimationClient.stopWalk = false;
                if ((boolean) FrameAnimationClient.settings.get("toggle-thirdperson"))
                    FrameAnimationClient.mc.gameSettings.thirdPersonView = false;
                iterator.remove();
            }
        }
    }

    public static Animation.Frame getCurrentFrame(int eid) {
        PlayerAnimationState state = playerAnimations.get(eid);
        if (state != null && state.active && !state.framesToRender.isEmpty()) {
            return state.framesToRender.get(0);
        }
        return null;
    }

    public static Animation.Frame getNextFrame(int eid) {
        PlayerAnimationState state = playerAnimations.get(eid);
        if (state != null && state.active && state.framesToRender.size() > 1) {
            return state.framesToRender.get(1);
        }
        return null;
    }

    public static float getFrameProgress(int eid) {
        PlayerAnimationState state = playerAnimations.get(eid);
        if (state != null && state.active && !state.framesToRender.isEmpty()) {
            Animation.Frame currentFrame = state.framesToRender.get(0);
            if (currentFrame.duration() > 0) {
                return (float) state.ticks / (float) currentFrame.duration();
            }
        }
        return 0.0F;
    }

    public static boolean shouldInterpolate(int eid) {
        PlayerAnimationState state = playerAnimations.get(eid);
        return state != null && state.interpolate;
    }

    public static void cleanupPlayer(int eid) {
        playerAnimations.remove(eid);
        if (eid == FrameAnimationClient.mc.thePlayer.entityId) {
            if ((boolean) FrameAnimationClient.settings.get("toggle-thirdperson"))
                FrameAnimationClient.mc.gameSettings.thirdPersonView = false;
            FrameAnimationClient.stopWalk = false;
        }
    }
}