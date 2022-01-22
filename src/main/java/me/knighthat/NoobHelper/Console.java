package me.knighthat.NoobHelper;

import org.bukkit.Bukkit;

import net.md_5.bungee.api.ChatColor;

public class Console
{
	protected NoobHelper plugin;

	public Console(NoobHelper plugin) {
		this.plugin = plugin;
	}

	public String stripColor( String text ) {
		return ChatColor.stripColor(addColor(text));
	}

	public String addColor( String text ) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}

	public void sendMsg( String message ) {
		plugin.getServer().getConsoleSender().sendMessage(addColor(message));
	}

	public static void printErr( String error ) {
		Bukkit.getServer().getLogger().finer(error);
		Bukkit.getServer().getLogger().finer("Please report this problem to my issue page!");
	}

	public static void printWarn( String warning ) {
		Bukkit.getServer().getLogger().warning(warning);
	}
}
