package carpetieeaddition;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IEECarpetServer implements CarpetExtension {
	public static final String fancyName = "Carpet IEE Addition";
	public static final Logger LOGGER = LogManager.getLogger(fancyName);
	public static MinecraftServer minecraft_server;

	public static MinecraftServer getServer() {
		return minecraft_server;
	}

	public static void noop() { }

	static {
		CarpetServer.manageExtension(new IEECarpetServer());
	}

	@Override
	public void onGameStarted() {
		CarpetServer.settingsManager.parseSettingsClass(IEECarpetSettings.class);
	}

	@Override
	public void onServerLoaded(MinecraftServer server) {
		minecraft_server = server;
	}

	@Override
	public String version() {
		return "Carpet IEE Addition";
	}

}
