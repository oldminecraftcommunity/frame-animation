package met.freehij.fa.screen.button;

import met.freehij.fa.animation.Animation;

public class AnimationButton extends StyledButtonWithText {
    public Animation animation;

    public AnimationButton(int id, int x, int y, Animation animation) {
        super(id, x, y, 50, 50, animation == null ? "None" : animation.name());
        if (animation == null) this.enabled = false;
        this.animation = animation;
    }
}
