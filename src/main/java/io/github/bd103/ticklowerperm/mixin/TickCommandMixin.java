package io.github.bd103.ticklowerperm.mixin;

import net.minecraft.server.commands.TickCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TickCommand.class)
public abstract class TickCommandMixin {
    // Replace the argument representing the permission level from 3 to 2, allowing command blocks to run it.
    @ModifyArg(
        method = "register(Lcom/mojang/brigadier/CommandDispatcher;)V",
        at = @At(value = "INVOKE", target = "hasPermission(I)Lnet/minecraft/server/commands/PermissionCheck;"),
        index = 0
    )
    private static int permissionLevel(int _value) {
        return 2;
    }
}
