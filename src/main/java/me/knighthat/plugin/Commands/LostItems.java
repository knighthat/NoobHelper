package me.knighthat.plugin.Commands;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import me.knighthat.plugin.Miscellaneous;
import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.Files.Config;
import me.knighthat.plugin.Files.DeathChests;

public class LostItems implements InventoryHolder, Miscellaneous
{
	Config config;
	DeathChests deathChests;

	Player player;

	SortedSet<Double> sortedDistances;
	Map<Double, String> distances = new HashMap<>();
	Map<Double, Integer> address = new HashMap<>();

	Map<Integer, ItemStack> itemList = new HashMap<>();

	String arg = new String();

	@Override
	public Inventory getInventory() {

		String title = config.getString("death_chest.container_title");
		title = title.replace("%player%", player.getName()).replace("%display_name%", player.getDisplayName());
		title = title.replace("%x%", getString("X")).replace("%y%", getString("Y")).replace("%z%", getString("Z"));

		Inventory inventory = Bukkit.createInventory(this, 54, title);

		for ( int slot : itemList.keySet() )
			inventory.setItem(slot, itemList.get(slot));

		return inventory;
	}

	String getString( String path ) {
		path = "." + path;
		return deathChests.getString(arg.isBlank() ? getPath(path) : arg.concat(path));
	}

	public LostItems(NoobHelper plugin, Player player, String id) {

		this.config = plugin.config;
		this.deathChests = plugin.deathChests;
		this.player = player;
		this.arg = id;

		if ( arg.isBlank() )
			pathFinder();

		adjustMap(getItemList(deathChests, arg.isBlank() ? getPath(".items") : id.concat(".items")));

		player.openInventory(getInventory());

	}

	String getPath( String concat ) { return distances.get(sortedDistances.first()).concat(concat); }

	Boolean sortedEmpty( String path ) {

		if ( !sortedDistances.isEmpty() )
			return false;

		playerIsImmortal();
		deathChests.get().set(path, null);
		deathChests.save();

		return true;
	}

	void adjustMap( Map<Integer, ItemStack> input ) {

		for ( int slot : input.keySet() )
			switch ( slot ) {
				case 36 :
				case 37 :
				case 38 :
				case 39 :
				case 40 :
					itemList.put(slot + 2, input.get(slot));
				break;

				default :
					itemList.put(slot, input.get(slot));
				break;
			}
	}

	void playerIsImmortal() { player.sendMessage(config.getString("death_chest.no_previous_dead_location", true)); }

	void pathFinder() {
		String name = player.getName(), uuid = player.getUniqueId().toString(), worldName = player.getWorld().getName(),
				path = name.concat("_" + uuid + "." + worldName);

		if ( !deathChests.get().isConfigurationSection(path) ) {
			playerIsImmortal();
			return;
		}

		deathChests.getSections(path, false).forEach(section -> {

			String newPath = path + "." + section;

			double x = deathChests.get().getDouble(newPath + ".X"), y = deathChests.get().getDouble(newPath + ".Y"),
					z = deathChests.get().getDouble(newPath + ".Z");

			Location loc = new Location(Bukkit.getWorld(worldName), x, y, z);

			Double distance = player.getLocation().distance(loc);
			distances.put(distance, newPath);
		});

		sortedDistances = new TreeSet<>(distances.keySet());

		if ( sortedEmpty(name.concat("_" + uuid)) )
			return;
	}
}
