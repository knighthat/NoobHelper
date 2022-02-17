package me.knighthat.plugin.Events.DeathChest;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.knighthat.plugin.NoobHelper;

public class Retrieval extends Storage
{

	String id;

	public Retrieval(NoobHelper plugin, PlayerInteractEvent e) {

		initialization(plugin, e.getPlayer(), e.getClickedBlock());

		if ( !blockData.get().contains(generateID()) ) { return; }

		id = generateID();

		boolean isOwner = blockData.getString(id.concat(".UUID")).equals(player.getUniqueId().toString());

		if ( isOwner ) {

			returnItems();

			magic();

			removeData();
		}

		final String path = this.path + (isOwner ? "retrieved" : "not_your_chest");
		player.sendMessage(config.getString(path, true));

		e.setCancelled(true);

	}

	void returnItems() {

		for ( String path : blockData.getSections(id.concat(".items"), false) )
			player.getInventory().setItem(Integer.parseInt(path), getItemStack(path));
		player.updateInventory();
	}

	void removeData() {

		blockData.get().set(id, null);
		blockData.save();
	}

	void magic() {

		location.getBlock().setType(Material.AIR);

		if ( config.get().getBoolean("death_chest.play_sound") )
			location.getWorld().playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
	}

	ItemStack getItemStack( String path ) {
		return blockData.get().getItemStack(generateID().concat(".items." + path));
	}
}
