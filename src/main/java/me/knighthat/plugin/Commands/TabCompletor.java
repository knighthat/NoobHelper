package me.knighthat.plugin.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.Files.Config;
import me.knighthat.plugin.Files.DeathChests;

public class TabCompletor implements TabCompleter
{

	NoobHelper plugin;
	Config config;
	DeathChests deathChests;

	List<String> defaultCommands = Arrays.asList("reload", "lostitems", "deletechest");

	public TabCompletor(NoobHelper plugin) {
		this.plugin = plugin;
		this.config = plugin.config;
		this.deathChests = plugin.deathChests;
	}

	@Override
	public List<String> onTabComplete( CommandSender sender, Command command, String alias, String[] args ) {

		List<String> result = new ArrayList<>();

		if ( args.length < 2 )
			for ( String cmd : defaultCommands )
				if ( cmd.startsWith(args[0].toLowerCase()) )
					result.add(cmd);

		if ( args.length == 2 ) {
			List<String> suggestions = new ArrayList<>();
			switch ( args[0] ) {
				case "lostitems" :
					if ( !(sender instanceof Player) ) { return null; }

					Player player = (Player) sender;
					String playerPath = player.getName() + "_" + player.getUniqueId().toString();
					for ( String world : deathChests.get().getConfigurationSection(playerPath).getKeys(false) )
						for ( String id : deathChests.get().getConfigurationSection(playerPath.concat("." + world)).getKeys(false) )
							suggestions.add(id);
				break;
				case "deletechest" :

					for ( String section : deathChests.get().getConfigurationSection("").getKeys(false) )
						for ( World world : Bukkit.getWorlds() )
							for ( String id : deathChests.get().getConfigurationSection(section.concat("." + world.getName())).getKeys(false) )
								suggestions.add(id);
				break;
			}
			for ( String cmd : suggestions )
				if ( cmd.startsWith(args[1].toLowerCase()) )
					result.add(cmd);
		}
		return result;
	}

}
