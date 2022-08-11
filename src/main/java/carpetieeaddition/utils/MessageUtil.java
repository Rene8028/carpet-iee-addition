package carpetieeaddition.utils;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import carpetieeaddition.IEECarpetServer;

public class MessageUtil {
    public static void sendMessage(CommandSourceStack source, String message) {
        if (source != null)
            source.sendSuccess(new TextComponent(message), source.getServer() != null && source.getServer().getLevel(Level.OVERWORLD) != null);
    }

    public static void sendMessage(CommandSourceStack source, Component component) {
        if (source != null)
            source.sendSuccess(component, source.getServer() != null && source.getServer().getLevel(Level.OVERWORLD) != null);
    }

    public static void sendServerMessage(MinecraftServer server, String message) {
        if (server != null) {
            IEECarpetServer.getLogger().info(message);
            for (Player player : server.getPlayerList().getPlayers()) {
                player.sendMessage(new TextComponent(message),player.getUUID());
            }
        } else {
            IEECarpetServer.getLogger().error("Message not delivered: " + message);
        }
    }

    public static void sendServerMessage(MinecraftServer server, Component component) {
        if (server != null) {
            IEECarpetServer.getLogger().info(component.getString());
            for (Player player : server.getPlayerList().getPlayers()) {
                player.sendMessage(component,player.getUUID());
            }
        } else {
            IEECarpetServer.getLogger().error("Message not delivered: " + component.getString());
        }
    }
    public static void sendServerDebugLog(String message){
        MinecraftServer server = IEECarpetServer.getServer();
        String finalmessage = "[Debug]" + IEECarpetServer.prefix + message;
        if (server != null) {
            IEECarpetServer.getLogger().info(finalmessage);
            for (Player player : server.getPlayerList().getPlayers()) {
                player.sendMessage(new TextComponent(finalmessage),player.getUUID());
            }
        } else {
            IEECarpetServer.getLogger().error("Message not delivered: " + finalmessage);
        }
    }

    public static void sendServerinfoLog(String message){
        MinecraftServer server = IEECarpetServer.getServer();
        String finalmessage = "[info]" + IEECarpetServer.prefix + message;
        if (server != null) {
            IEECarpetServer.getLogger().info(finalmessage);
            for (Player player : server.getPlayerList().getPlayers()) {
                player.sendMessage(new TextComponent(finalmessage),player.getUUID());
            }
        } else {
            IEECarpetServer.getLogger().error("Message not delivered: " + finalmessage);
        }
    }

}
