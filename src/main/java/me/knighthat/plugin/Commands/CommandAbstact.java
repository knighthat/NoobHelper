package me.knighthat.plugin.Commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.Files.Config;
import me.knighthat.plugin.Files.DeathChests;
import me.knighthat.plugin.Files.TrashBins;

public abstract class CommandAbstact
{

	NoobHelper plugin;
	Config config;
	DeathChests deathChests;
	TrashBins trashBins;

	public CommandAbstact(NoobHelper plugin) {

		this.plugin = plugin;
		this.deathChests = plugin.deathChests;
		this.config = plugin.config;
		this.trashBins = plugin.trashBins;

	}

	public abstract String getName();

	public abstract String getPermission();

	public abstract boolean requiredPlayer();

	public abstract void onCommand( CommandSender sender, String[] args );

	public abstract List<String> onTabComplete( CommandSender sender, String[] args );

}
