package carpetieeaddition;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import carpetieeaddition.utils.FreeCameraUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IEECarpetServer implements CarpetExtension {
	public static final String fancyName = "Carpet IEE Addition";
	public static final Logger LOGGER = LogManager.getLogger(fancyName);
	public static MinecraftServer minecraft_server;
	private static IEECarpetServer instance;
	private Map<UUID, FreeCameraUtil.FreeCameraData> cameraData = new HashMap<>();

	public static MinecraftServer getServer() {
		return minecraft_server;
	}
	public static Logger getLogger() {
		return LOGGER;
	}
	public static IEECarpetServer getInstance() {
		return instance;
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
		instance = this;
	}

//	@Override
//	public String version() {
//		return "Carpet IEE Addition";
//	}

	public Map<UUID, FreeCameraUtil.FreeCameraData> getCameraData() {
		return this.cameraData;
	}

}
