package me.knighthat.plugin.Events.TrashBin;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import me.knighthat.plugin.NoobHelper;

public class Use extends Storage implements Listener
{

	public Use(NoobHelper plugin, PlayerInteractEvent e) {

		register(plugin, e.getPlayer(), e.getClickedBlock());

		if ( !trashBins.get().contains(getID(location)) ) { return; }

		if ( !checkPermission("use") ) { return; }

		String inventoryName = config.getString(path.concat("title"));
		Inventory inventory = Bukkit.createInventory(player, InventoryType.CHEST, inventoryName);
		player.openInventory(inventory);
	}
}
