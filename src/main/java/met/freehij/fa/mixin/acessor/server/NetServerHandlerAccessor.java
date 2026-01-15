package met.freehij.fa.mixin.acessor.server;

import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(NetServerHandler.class)
public interface NetServerHandlerAccessor {
    @Accessor("playerEntity")
    EntityPlayerMP getPlayerEntity();
}
