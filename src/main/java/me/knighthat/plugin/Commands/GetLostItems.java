package me.knighthat.plugin.Commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.knighthat.NoobHelper;
import me.knighthat.plugin.Files.Config;
import me.knighthat.plugin.Files.DeathChests;

public class GetLostItems implements InventoryHolder
{

	Config config;
	DeathChests deathChests;

	Player player;
	Location location;

	String playerPath;
	Boolean hasID;
	String path;
	Set<String> sections;

	@Override
	public Inventory getInventory() { return null; }

	public GetLostItems(Config config, DeathChests deathChests, Player player, Location location, String id) {

		this.config = config;
		this.deathChests = deathChests;
		this.player = player;
		this.location = location;
		this.hasID = !id.isEmpty();
		this.playerPath = player.getName().concat("_" + player.getUniqueId() + "." + location.getWorld().getName());

		if ( hasID ) {
			this.path = playerPath + "." + id;
		} else
			this.sections = deathChests.get().getConfigurationSection(playerPath).getKeys(false);
	}

	String configGet( String path ) { return config.getString(path, true); }

	public GetLostItems(NoobHelper plugin, Player player, String id) {

		this(plugin.config, plugin.deathChests, player, player.getLocation(), id);

		if ( !deathChests.get().contains(path) ) {

			player.sendMessage(configGet("death_chest.container_not_exist"));
			return;
		}

		new ContainerAbstract(player) {

			@Override
			public String getTitle() {

				String title = config.getString("death_chest.container_title"),
						x = deathChests.getString(path.concat(".X")), y = deathChests.getString(path.concat(".Y")),
						z = deathChests.getString(path.concat(".Z"));
				title = title.replace("%x%", x).replace("%y%", y).replace("%z%", z);
				title = title.replace("%player%", player.getName()).replace("%display_name%", player.getDisplayName());
				return title;
			}

			@Override
			public void setContent() {
				for ( String stringSlot : deathChests.get().getConfigurationSection(path.concat(".items"))
						.getKeys(false) ) {
					int slot = Integer.parseInt(stringSlot);
					if ( slot >= 36 ) { slot += 2; }
					getInventory().setItem(slot, deathChests.get().getItemStack(path.concat(".items." + stringSlot)));
				}
				ItemStack exp = new ItemStack(Material.EXPERIENCE_BOTTLE);
				ItemMeta expMeta = exp.getItemMeta();
				String expName = config.getString("death_chest.exp_bottle_name");
				expName = expName.replace("%exp%", deathChests.getString(path.concat(".Exp")));
				expMeta.setDisplayName(expName);
				exp.setItemMeta(expMeta);
				getInventory().setItem(49, exp);
			}
		}.openInventory();
	}

	public GetLostItems(NoobHelper plugin, Player player) {

		this(plugin.config, plugin.deathChests, player, player.getLocation(), new String());

		if ( sections == null | sections.isEmpty() ) {

			player.sendMessage(configGet("death_chest.no_previous_dead_location"));
			return;
		}

		Map<Double, String> distances = new HashMap<>();

		for ( String section : sections ) {
			String path = playerPath.concat("." + section);
			int x = deathChests.get().getInt(path.concat(".X")), y = deathChests.get().getInt(path.concat(".Y")),
					z = deathChests.get().getInt(path.concat(".Z"));
			Location location = new Location(player.getWorld(), x, y, z);
			distances.put(location.distance(player.getLocation()), section);
		}
		SortedMap<Double, String> reorder = new TreeMap<>(distances);
		new GetLostItems(plugin, player, distances.get(reorder.firstKey()));
	}

}
