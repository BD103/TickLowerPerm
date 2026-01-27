package dev.bd103.ticklowerperm.mixin;

import net.minecraft.commands.Commands;
import net.minecraft.server.commands.TickCommand;
import net.minecraft.server.permissions.PermissionCheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TickCommand.class)
public abstract class TickCommandMixin {
    // Replace the argument representing the permission level from 3 to 2, allowing command blocks to run it.
    @ModifyArg(
        method = "register(Lcom/mojang/brigadier/CommandDispatcher;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/commands/Commands;hasPermission(Lnet/minecraft/server/permissions/PermissionCheck;)Lnet/minecraft/server/permissions/PermissionProviderCheck;"),
        index = 0
    )
    private static PermissionCheck permissionLevel(PermissionCheck value) {
        if (value != Commands.LEVEL_ADMINS) {
            Logger logger = LoggerFactory.getLogger("ticklowerperm");
            logger.warn("Expected /tick permission check to be `LEVEL_ADMINS`, but it was {} instead. Continuing to override it to `LEVEL_GAMEMASTERS`.", value);
        }

        return Commands.LEVEL_GAMEMASTERS;
    }
}
