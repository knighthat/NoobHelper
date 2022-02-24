package me.knighthat.plugin.Events.TrashBin;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import me.knighthat.NoobHelper;

public class Use extends Storage
{

	public Use(NoobHelper plugin, PlayerInteractEvent e) {

		register(plugin, e.getPlayer(), e.getClickedBlock());

		if ( !trashBins.get().contains(getID(location)) ) { return; }

		if ( !checkPermission("use") ) { return; }

		final String title = config.getString(path.concat("title"), false);

		@SuppressWarnings("deprecation")
		Inventory gui = Bukkit.createInventory(player, InventoryType.CHEST, title);

		player.openInventory(gui);
	}
}
