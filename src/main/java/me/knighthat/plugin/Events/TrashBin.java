package me.knighthat.plugin.Events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import me.knighthat.plugin.Misc;
import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.Files.BlockData;
import me.knighthat.plugin.Files.Config;

public class TrashBin
{

	Config config;
	BlockData blockData;

	private Player player;
	private final String path = "noobhelper.trash_bin.";

	public TrashBin(NoobHelper plugin, SignChangeEvent e) {

		register(plugin, e.getPlayer());

		if ( !checkPermission("place") )
			return;

		if ( !e.getLine(0).equalsIgnoreCase("[Trash Bin]") )
			return;

		for ( int line = 0 ; line < 4 ; line++ )
			e.setLine(line, config.getStringList("trash_bin.lines").get(line));

		addData(e.getBlock().getLocation());
		player.sendMessage(config.getString("trash_bin.message", true));
	}

	public TrashBin(NoobHelper plugin, BlockBreakEvent e) {

		register(plugin, e.getPlayer());

		final String id = getID(e.getBlock().getLocation());

		if ( blockData.contains(id) )
			if ( checkPermission("remove") ) {
				blockData.set(id, null);
				blockData.save();
				return;
			} else
				e.setCancelled(true);
	}

	public TrashBin(NoobHelper plugin, Player player, Location signLocation) {

		register(plugin, player);

		if ( !blockData.contains(getID(signLocation)) )
			return;

		if ( !checkPermission("use") )
			return;

		final String title = config.getString("trash_bin.title", false);

		Inventory gui = Bukkit.createInventory(player, InventoryType.CHEST, title);

		player.openInventory(gui);
	}

	void register( NoobHelper plugin, Player player ) {
		this.player = player;
		this.config = plugin.config;
		this.blockData = plugin.blockdata;
	}

	String getID( Location loc ) {

		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();

		return loc.getWorld().getName() + ".sign_" + x + y + z;

	}

	void setData( String path, Object value ) {
		blockData.get().set(path, value);
	}

	void addData( Location loc ) {

		String id = getID(loc);
		setData(id + ".X", loc.getBlockX());
		setData(id + ".Y", loc.getBlockY());
		setData(id + ".Z", loc.getBlockZ());
		setData(id + ".Owner", player.getName());
		blockData.save();

	}

	Boolean checkPermission( String permission ) {
		return Misc.checkPermission(player, config, path + permission, true);
	}
}
