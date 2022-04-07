package me.knighthat.plugin.Commands;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.knighthat.plugin.files.Config;
import me.knighthat.plugin.utils.PermissionChecker;

public class Guis extends Command implements PermissionChecker
{

	Config config;

	public Guis(Config config, String name, List<String> aliases) {
		super(name, null, null, aliases);
		this.config = config;
	}

	@Override
	public boolean execute( CommandSender sender, String commandLabel, String[] args ) {

		if ( !(sender instanceof Player) )
			return true;

		Player player = (Player) sender;
		Location loc = player.getLocation();

		if ( !checkPermission(player, config, "command." + getName(), true) )
			return true;

		switch ( getName() ) {

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
			default :
				return true;
		}

		return true;
	}
}
