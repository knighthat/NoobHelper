package me.knighthat.plugin.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.knighthat.plugin.NoobHelper;

public class DeleteChest extends CommandAbstact
{
	final String PATH = "death_chest.delete_chest.";

	public DeleteChest(NoobHelper plugin) { super(plugin); }

	@Override
	public String getName() { return "deletechest"; }

	@Override
	public String getPermission() { return "deletechest"; }

	@Override
	public boolean requiredPlayer() { return false; }

	@Override
	public void onCommand( CommandSender sender, String[] args ) {

		if ( args.length < 2 ) {

			sender.sendMessage(config.getString(PATH.concat("missing_argument"), true, null, null));
			return;
		}
		switch ( args[1] ) {
			case "confirm" :
				performConfirmation(sender);
			break;
			default :
				for ( String section : deathChests.get().getConfigurationSection("").getKeys(true) )
					if ( section.endsWith(args[1]) ) {

						Double countdown = config.get().getDouble(PATH.concat("countdown")) * 1000;

						List<Object> data = new ArrayList<>();
						data.add(System.currentTimeMillis() + countdown);
						data.add(section);

						Manager.confirmCountdown.put(sender, data);

						String msg = config.getString(PATH.concat("confirm_message"), true, null, null);
						msg = msg.replace("%sec%", String.valueOf(countdown / 1000));
						sender.sendMessage(msg);
						break;
					}
			break;
		}
	}

	void performConfirmation( CommandSender sender ) {

		if ( !Manager.confirmCountdown.containsKey(sender) ) { return; }

		if ( (long) Manager.confirmCountdown.get(sender).get(0) < System.currentTimeMillis() ) {

			String message = config.getString(PATH.concat("timeout_message"), true, null, null);
			sender.sendMessage(message);
			return;
		}

		String pathToChest = (String) Manager.confirmCountdown.get(sender).get(1);
		Double x = deathChests.get().getDouble(pathToChest.concat(".X"));
		Double y = deathChests.get().getDouble(pathToChest.concat(".Y"));
		Double z = deathChests.get().getDouble(pathToChest.concat(".Z"));
		String worldName = pathToChest.split("\\.")[1];

		Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
		location.getBlock().setType(Material.AIR);

		String[] coord = { String.valueOf(x), String.valueOf(y), String.valueOf(z) };
		String msg = config.getString(PATH.concat("chest_deleted"), true, null, coord);
		sender.sendMessage(msg);

		Manager.confirmCountdown.remove(sender);

		deathChests.get().set(pathToChest, null);
		deathChests.save();
	}

	@Override
	public List<String> onTabComplete( CommandSender sender, String[] args ) {
		List<String> results = new ArrayList<>();

		if ( Manager.confirmCountdown.containsKey(sender) )
			return Arrays.asList("confirm");

		for ( String section : deathChests.getSections("", false) )
			if ( sender instanceof Player ) {
				for ( String id : deathChests.getSections(section + "." + ((Player) sender).getWorld().getName(), false) )
					results.add(id);
			} else
				for ( World world : Bukkit.getWorlds() )
					for ( String id : deathChests.getSections(section + "." + world.getName(), null) )
						results.add(id);

		return results;
	}

}
