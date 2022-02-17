package me.knighthat.plugin;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.knighthat.plugin.Files.ConfigFile;
import net.md_5.bungee.api.ChatColor;

public class Misc
{

	public static String addColor( String text ) { return ChatColor.translateAlternateColorCodes('&', text); }

	public static String stripColor( String text ) { return ChatColor.stripColor(addColor(text)); }

	public static void sendMessage( String message ) {
		Bukkit.getServer().getConsoleSender().sendMessage(addColor(message));
	}

	public static void sendError( NoobHelper plugin, String error ) { plugin.getLogger().log(Level.FINE, error); }

	public static void sendWarning( NoobHelper plugin, String warning ) {
		plugin.getLogger().log(Level.WARNING, warning);
	}

	public static boolean checkPermission( Player player, ConfigFile config, String permission ) {
		return checkPermission(player, config, permission, false);
	}

	public static boolean checkPermission( Player player, ConfigFile config, String permission,
			boolean sendNoPermMsg ) {

		if ( !permission.startsWith("noobhelper.") ) { permission = "noobhelper." + permission; }

		final String message = config.getString("no_permission", true).replace("%perm%", permission);

		if ( !player.hasPermission(permission) ) {

			if ( sendNoPermMsg ) { player.sendMessage(message); }

			return false;
		}
		return true;
	}

}
