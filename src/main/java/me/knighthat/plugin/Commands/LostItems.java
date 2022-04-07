package me.knighthat.plugin.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.StringJoiner;
import java.util.TreeMap;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.menus.ShowLostItems;
import me.knighthat.plugin.utils.PathGenerator;
import me.knighthat.plugin.utils.TextModification;

public class LostItems extends CommandAbstact implements PathGenerator, TextModification
{

	Player player;

	ShowLostItems inventory;

	public LostItems(NoobHelper plugin) { super(plugin); }

	@Override
	public String getName() { return "lostitems"; }

	@Override
	public String getPermission() { return "lostitems"; }

	@Override
	public boolean requiredPlayer() { return true; }

	@Override
	public Player getPlayer() { return this.player; }

	@Override
	public void onCommand( CommandSender sender, String[] args ) {

		this.player = (Player) sender;

		if ( args.length > 1 ) {
			if ( !getInventory(getFullPath(args[1])) ) {
				String message = config.getString("death_chest.container_not_exist", true, getPlayer(), null);
				getPlayer().sendMessage(message);
				return;
			}
		} else if ( !getInventory() ) {
			String message = config.getString("death_chest.no_previous_dead_location", true, player, null);
			getPlayer().sendMessage(message);
			return;
		}
		inventory.open();
	}

	boolean getInventory() {

		StringJoiner joiner = new StringJoiner(".");
		joiner = joiner.add(getPlayerPath()).add(getWorldName(null));

		if ( deathChests.getSections(joiner.toString(), true).isEmpty() ) { return false; }

		SortedMap<Double, String> distances = new TreeMap<>();

		deathChests.getSections(joiner.toString(), false).forEach(section -> {

			String path = getFullPath(section);

			double x = deathChests.get().getDouble(path + ".X");
			double y = deathChests.get().getDouble(path + ".Y");
			double z = deathChests.get().getDouble(path + ".Z");
			World world = getPlayer().getWorld();
			Location loc = new Location(world, x, y, z);

			distances.put(player.getLocation().distance(loc), path);
		});

		String closestPath = distances.get(distances.firstKey());
		this.inventory = new ShowLostItems(plugin, player, closestPath);

		return true;
	}

	boolean getInventory( String path ) {

		if ( !deathChests.get().contains(path) ) { return false; }

		this.inventory = new ShowLostItems(plugin, player, path);

		return true;
	}

	@Override
	public List<String> onTabComplete( CommandSender sender, String[] args ) {
		List<String> results = new ArrayList<>();

		if ( sender instanceof Player )
			if ( args.length <= 3 ) {

				this.player = (Player) sender;

				StringJoiner joiner = new StringJoiner(".");
				joiner = joiner.add(getPlayerPath()).add(getWorldName(null));

				results = deathChests.getSections(joiner.toString(), false);
			}
		return results;
	}
}
