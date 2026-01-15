package met.freehij.fa.animation;

import java.util.Map;

public record Animation(String name, boolean interruptible, boolean resetArms, boolean interpolate, Frame[] frames) {
    public record Frame(int duration, Map<BodyPart, Float> data) { }
}
