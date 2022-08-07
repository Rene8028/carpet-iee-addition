package carpetieeaddition;

import net.fabricmc.api.ModInitializer;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import carpetieeaddition.commands.*;

public class IEECarpetMod implements ModInitializer {
    @Override
    public void onInitialize() {
        IEECarpetServer.noop();
    }
//    @Override
//    public void registerCommands(CommandDispatcher<CommandSource> dispatcher){
//        FreecamCommand.register(dispatcher);
//    }
}
