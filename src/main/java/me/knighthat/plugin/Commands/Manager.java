package me.knighthat.plugin.Commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.Files.Config;
import me.knighthat.plugin.Files.DeathChests;

public class Manager implements CommandExecutor
{

	NoobHelper plugin;
	Config config;
	DeathChests deathChests;

	public static Map<CommandSender, Long> countdown = new HashMap<>();
	public static Map<CommandSender, String> pathToDelete = new HashMap<>();

	public Manager(NoobHelper plugin) {
		this.plugin = plugin;
		this.config = plugin.config;
		this.deathChests = plugin.deathChests;
	}

	@Override
	public boolean onCommand( CommandSender sender, Command command, String cmd, String[] args ) {

		boolean isPlayer = sender instanceof Player;

		switch ( args[0] ) {
			case "reload" :
				new Reload(plugin, sender, isPlayer);
			break;
			case "lostitems" :
				if ( !isPlayer )
					return true;
				if ( args.length < 2 ) {
					new GetLostItems(plugin, (Player) sender);
				} else
					new GetLostItems(plugin, (Player) sender, args[1]);
			break;
			case "deletechest" :
				new DeleteChest(plugin, sender, args);
			break;
		}

		return true;
	}

}
