package carpetieeaddition;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;

public class IEECarpetServer implements CarpetExtension {
	public static void noop() { }

	static {
		CarpetServer.manageExtension(new IEECarpetServer());
	}

	@Override
	public void onGameStarted() {
		CarpetServer.settingsManager.parseSettingsClass(IEECarpetSettings.class);
	}



	@Override
	public String version() {
		return "Carpet IEE Addition";
	}

}
