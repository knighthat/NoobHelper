package me.knighthat.plugin.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.Files.Config;
import me.knighthat.plugin.Files.DeathChests;
import me.knighthat.plugin.utils.PermissionChecker;

public class DeleteChest implements PermissionChecker
{

	Config config;
	DeathChests deathChests;

	public DeleteChest(NoobHelper plugin, CommandSender sender, String[] args) {

		this.config = plugin.config;
		this.deathChests = plugin.deathChests;

		if ( sender instanceof Player )
			if ( !checkPermission((Player) sender, config, "command.delete_chest", true) ) { return; }

		String path = "death_chest.delete_chest.";
		if ( args.length < 2 ) {

			sender.sendMessage(config.getString(path.concat("missing_argument"), true, null, null));
			return;
		}

		if ( args[1].equalsIgnoreCase("confirm") ) {

			if ( !Manager.countdown.containsKey(sender) ) { return; }

			if ( Manager.countdown.get(sender) < System.currentTimeMillis() ) {

				sender.sendMessage(config.getString(path.concat("timeout_message"), true, null, null));
				return;
			}
			String pathToChest = Manager.pathToDelete.get(sender);
			Double x = deathChests.get().getDouble(pathToChest.concat(".X"));
			Double y = deathChests.get().getDouble(pathToChest.concat(".Y"));
			Double z = deathChests.get().getDouble(pathToChest.concat(".Z"));
			String worldName = pathToChest.split("\\.")[1];

			Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
			location.getBlock().setType(Material.AIR);

			String[] coord = { String.valueOf(x), String.valueOf(y), String.valueOf(z) };
			String msg = config.getString(path.concat("chest_deleted"), true, null, coord);
			sender.sendMessage(msg);

			deathChests.get().set(Manager.pathToDelete.get(sender), null);
			deathChests.save();
			return;
		}
		for ( String section : deathChests.get().getConfigurationSection("").getKeys(true) )
			if ( section.endsWith(args[1]) ) {

				Manager.countdown.put(sender, (long) (System.currentTimeMillis() + (config.get().getDouble(path.concat("countdown")) * 1000)));
				Manager.pathToDelete.put(sender, section);

				String waitTime = config.getString(path.concat("countdown"));
				String msg = config.getString(path.concat("confirm_message"), true, null, null);
				msg = msg.replace("%sec%", waitTime);
				sender.sendMessage(msg);
				break;
			}

	}
}
