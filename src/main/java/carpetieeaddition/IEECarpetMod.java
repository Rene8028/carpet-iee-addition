package carpetieeaddition;

//import carpetieeaddition.utils.FreeCameraUtil;
import carpet.CarpetExtension;
import net.fabricmc.api.ModInitializer;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import carpetieeaddition.commands.*;
import net.minecraft.server.MinecraftServer;

public class IEECarpetMod implements CarpetExtension , ModInitializer {
    @Override
    public void onInitialize() {
        IEECarpetServer.noop();
    }

    @Override
    public void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher){
        FreecamCommand.register(dispatcher);
    }
}
