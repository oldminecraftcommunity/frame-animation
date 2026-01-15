package met.freehij.fa.mixin.client;

import met.freehij.fa.animation.Animation;
import met.freehij.fa.animation.BodyPart;
import met.freehij.fa.util.client.AnimationUtilsClient;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.ModelRenderer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBiped.class)
public class ModelBipedMixin {
    @Shadow
    public ModelRenderer bipedHead;
    @Shadow
    public ModelRenderer bipedHeadwear;
    @Shadow
    public ModelRenderer bipedBody;
    @Shadow
    public ModelRenderer bipedRightArm;
    @Shadow
    public ModelRenderer bipedLeftArm;
    @Shadow
    public ModelRenderer bipedRightLeg;
    @Shadow
    public ModelRenderer bipedLeftLeg;
    @Shadow
    public ModelRenderer bipedCloak;

    @Inject(method = "setRotationAngles", at = @At("TAIL"))
    public void setRotationAngles(float f, float g, float partialTicks, float i, float j, float k, CallbackInfo ci) {
        bipedHead.rotateAngleZ = 0.0F;
        bipedHeadwear.rotateAngleZ = 0.0F;
        bipedBody.rotateAngleZ = 0.0F;
        bipedLeftLeg.rotateAngleZ = 0.0F;
        bipedRightLeg.rotateAngleZ = 0.0F;
        bipedCloak.rotateAngleZ = 0.0F;

        int eid = AnimationUtilsClient.currentRenderingEid;
        Animation.Frame frame = AnimationUtilsClient.getCurrentFrame(eid);
        Animation.Frame nextFrame = AnimationUtilsClient.getNextFrame(eid);
        float progress = AnimationUtilsClient.getFrameProgress(eid);
        boolean interpolate = AnimationUtilsClient.shouldInterpolate(eid);

        if (frame != null) {
            AnimationUtilsClient.PlayerAnimationState state = AnimationUtilsClient.playerAnimations.get(eid);
            if (state != null && state.resetArms) {
                bipedRightArm.rotateAngleZ = 0.0F;
                bipedRightArm.rotateAngleX = 0.0F;
                bipedLeftArm.rotateAngleZ = 0.0F;
                bipedLeftArm.rotateAngleX = 0.0F;
            }
            for (BodyPart bodyPart : frame.data().keySet()) {
                float value = frame.data().get(bodyPart);
                if (interpolate && nextFrame != null && nextFrame.data().containsKey(bodyPart)) {
                    float nextValue = nextFrame.data().get(bodyPart);
                    value = value + (nextValue - value) * progress;
                }
                switch (bodyPart) {
                    case HEAD_X -> bipedHead.rotateAngleX = value;
                    case HEAD_Y -> bipedHead.rotateAngleY = value;
                    case HEAD_Z -> bipedHead.rotateAngleZ = value;
                    case HAT_X -> bipedHeadwear.rotateAngleX = value;
                    case HAT_Y -> bipedHeadwear.rotateAngleY = value;
                    case HAT_Z -> bipedHeadwear.rotateAngleZ = value;
                    case BODY_X -> bipedBody.rotateAngleX = value;
                    case BODY_Y -> bipedBody.rotateAngleY = value;
                    case BODY_Z -> bipedBody.rotateAngleZ = value;
                    case RIGHTARM_X -> bipedRightArm.rotateAngleX = value;
                    case LEFTARM_X -> bipedLeftArm.rotateAngleX = value;
                    case RIGHTARM_Y -> bipedRightArm.rotateAngleY = value;
                    case LEFTARM_Y -> bipedLeftArm.rotateAngleY = value;
                    case RIGHTARM_Z -> bipedRightArm.rotateAngleZ = value;
                    case LEFTARM_Z -> bipedLeftArm.rotateAngleZ = value;
                    case RIGHTLEG_X -> bipedRightLeg.rotateAngleX = value;
                    case LEFTLEG_X -> bipedLeftLeg.rotateAngleX = value;
                    case RIGHTLEG_Y -> bipedRightLeg.rotateAngleY = value;
                    case LEFTLEG_Y -> bipedLeftLeg.rotateAngleY = value;
                    case RIGHTLEG_Z -> bipedRightLeg.rotateAngleZ = value;
                    case LEFTLEG_Z -> bipedLeftLeg.rotateAngleZ = value;
                    case CAPE_X -> bipedCloak.rotateAngleX = value;
                    case CAPE_Y -> bipedCloak.rotateAngleY = value;
                    case CAPE_Z -> bipedCloak.rotateAngleZ = value;
                }
            }
        }
        AnimationUtilsClient.currentRenderingEid = -1;
    }
}