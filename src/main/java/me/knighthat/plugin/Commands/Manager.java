package me.knighthat.plugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.knighthat.plugin.Miscellaneous;
import me.knighthat.plugin.NoobHelper;

public class Manager implements CommandExecutor, Miscellaneous
{

	NoobHelper plugin;

	public Manager(NoobHelper plugin) { this.plugin = plugin; }

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
				new LostItems(plugin, (Player) sender, args.length < 2 ? "" : args[1]);
			break;
		}

		return true;
	}

}
