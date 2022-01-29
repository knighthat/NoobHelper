package me.knighthat.plugin;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.knighthat.plugin.Files.Config;
import net.md_5.bungee.api.ChatColor;

public class Misc
{

	public static String addColor( String text ) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}

	public static String stripColor( String text ) {
		return ChatColor.stripColor(addColor(text));
	}

	public static void sendMessage( String message ) {
		Bukkit.getServer().getConsoleSender().sendMessage(addColor(message));
	}

	public static void sendError( NoobHelper plugin, String error ) {
		plugin.getLogger().log(Level.FINE, error);
	}

	public static void sendWarning( NoobHelper plugin, String warning ) {
		plugin.getLogger().log(Level.WARNING, warning);
	}

	public static boolean checkPermission( Player player, Config config, String permission ) {

		if ( !permission.startsWith("noobhelper.") )
			permission = "noobhelper." + permission;

		if ( !player.hasPermission(permission) ) {
			player.sendMessage(noPerm(config, permission));
			return false;
		}
		return true;
	}

	private static String noPerm( Config config, String permission ) {
		return config.getString("no_permission").replace("%perm%", permission);
	}

}