package carpetieeaddition.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

import carpet.settings.SettingsManager;
import carpetieeaddition.IEECarpetSettings;
import carpetieeaddition.utils.FreeCameraUtil;
import net.minecraft.world.level.GameType;

import static net.minecraft.commands.Commands.literal;

import java.util.Map;
import java.util.UUID;


public class FreecamCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> camera = literal("freecam")
                .requires(commandSourceStack -> SettingsManager.canUseCommand(commandSourceStack, IEECarpetSettings.commandFreecam))
                .executes(context -> executeFreeCamera(context.getSource().getPlayerOrException(), context.getSource().getPlayerOrException()));
        dispatcher.register(camera);
    }
    public static int executeFreeCamera(CommandSource source, ServerPlayer serverPlayer) {
        Map<UUID, FreeCameraUtil.FreeCameraData> freeCameraUtilMap = carpetieeaddition.IEECarpetServer.getInstance().getCameraData();
        FreeCameraUtil.FreeCameraData cameraData = freeCameraUtilMap.get(serverPlayer.getUUID());
        boolean isCameraMode;
        FreeCameraUtil.FreeCameraData cameraDataNew = new FreeCameraUtil.FreeCameraData(serverPlayer, true);
        if (cameraData == null || !cameraData.isFreecam) {
            isCameraMode = true;
            serverPlayer.setGameMode(GameType.SPECTATOR);
        } else {
            if (serverPlayer.isAlive()) {
//                serverPlayer.teleportTo(serverPlayer.server.getLevel(cameraData.dimension), cameraData.vec3.x, cameraData.vec3.y, cameraData.vec3.z, cameraData.yRot, cameraData.xRot);
            }
            isCameraMode = false;
            serverPlayer.setGameMode(cameraData.gameType);
        }
        cameraDataNew.isFreecam = isCameraMode;
        freeCameraUtilMap.put(serverPlayer.getUUID(), cameraDataNew);
        return 1;
    }
}
