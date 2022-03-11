package me.knighthat.plugin.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.utils.PermissionChecker;

public class Guis implements CommandExecutor, PermissionChecker
{

	NoobHelper plugin;

	public Guis(NoobHelper plugin) {

		this.plugin = plugin;
	}

	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {

		if ( !(sender instanceof Player) )
			return true;

		Player player = (Player) sender;
		Location loc = player.getLocation();

		if ( !checkPermission(player, plugin.config, command.getName(), true) )
			return true;

		switch ( command.getName() ) {

			case "workbench" :
				player.openWorkbench(loc, true);
			break;

			case "anvil" :
				player.openAnvil(loc, true);
			break;

			case "cartography" :
				player.openCartographyTable(loc, true);
			break;

			case "grindstone" :
				player.openGrindstone(loc, true);
			break;

			case "loom" :
				player.openLoom(loc, true);
			break;

			case "smithingtable" :
				player.openSmithingTable(loc, true);
			break;

			case "stonecutter" :
				player.openStonecutter(loc, true);
			break;
		}

		return true;
	}

}
