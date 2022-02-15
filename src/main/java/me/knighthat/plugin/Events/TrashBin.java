package me.knighthat.plugin.Events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import me.knighthat.plugin.Misc;
import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.Files.BlockDataFile;
import me.knighthat.plugin.Files.ConfigFile;

public class TrashBin
{

	ConfigFile config;
	BlockDataFile blockData;

	Player player;
	Block block;
	Location location;

	private final String path = "trash_bin.";

	public TrashBin(NoobHelper plugin, SignChangeEvent e) {

		register(plugin, e.getPlayer(), e.getBlock());

		if ( !checkPermission("place") )
			return;

		if ( !e.getLine(0).equalsIgnoreCase("[Trash Bin]") )
			return;

		for ( int line = 0 ; line < 4 ; line++ )
			e.setLine(line, config.get().getStringList(path.concat("lines")).get(line));

		addData();
		player.sendMessage(config.getString(path.concat("message"), true));
	}

	public TrashBin(NoobHelper plugin, BlockBreakEvent e) {

		register(plugin, e.getPlayer(), e.getBlock());

		final String id = getID(e.getBlock().getLocation());

		if ( blockData.get().contains(id) )
			if ( checkPermission("remove") ) {
				blockData.get().set(id, null);
				blockData.save();
			} else
				e.setCancelled(true);
	}

	public TrashBin(NoobHelper plugin, PlayerInteractEvent e) {

		register(plugin, e.getPlayer(), e.getClickedBlock());

		if ( !blockData.get().contains(getID(location)) )
			return;

		if ( !checkPermission("use") )
			return;

		final String title = config.getString(path.concat("title"), false);

		Inventory gui = Bukkit.createInventory(player, InventoryType.CHEST, title);

		player.openInventory(gui);
	}

	void register( NoobHelper plugin, Player player, Block block ) {
		this.player = player;
		this.config = plugin.config;
		this.blockData = plugin.blockdata;
		this.block = block;
		this.location = block.getLocation();
	}

	String getID( Location loc ) {

		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();

		return loc.getWorld().getName().concat("." + x + y + z);
	}

	void setData( String path, Object value ) {

		String id = getID(block.getLocation()).concat(".");
		blockData.get().set(id + path, value);
	}

	void addData() {

		setData("X", location.getBlockX());
		setData("Y", location.getBlockY());
		setData("Z", location.getBlockZ());
		setData("Owner", player.getName());
		blockData.save();
	}

	Boolean checkPermission( String permission ) {
		return Misc.checkPermission(player, config, path.concat(permission), true);
	}
}
