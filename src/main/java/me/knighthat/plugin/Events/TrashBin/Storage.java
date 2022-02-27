package me.knighthat.plugin.Events.TrashBin;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.Files.Config;
import me.knighthat.plugin.Files.TrashBins;
import me.knighthat.plugin.utils.PermissionChecker;

public abstract class Storage implements PermissionChecker
{
	NoobHelper plugin;
	Config config;
	TrashBins trashBins;

	Player player;
	Block block;
	Location location;

	final String path = "trash_bin.";

	void register( NoobHelper plugin, Player player, Block block ) {
		this.player = player;
		this.plugin = plugin;
		this.config = plugin.config;
		this.trashBins = plugin.trashBins;
		this.block = block;
		this.location = block.getLocation();
	}

	String getID( Location loc ) {

		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();

		return loc.getWorld().getName().concat("." + x + y + z);
	}

	String generateID() {

		int x = location.getBlockX(), y = location.getBlockY(), z = location.getBlockZ();

		return location.getWorld().getName().concat("" + x + y + z);
	}

	Boolean checkPermission( String permission ) {

		if ( player.hasPermission("noobhelper.trash_bin.*") ) { return true; }

		return checkPermission(player, config, path.concat(permission), true);
	}

	void addData() {

		setData("X", location.getBlockX());
		setData("Y", location.getBlockY());
		setData("Z", location.getBlockZ());
		setData("Owner", player.getName());
		trashBins.save();
	}

	void setData( String path, Object value ) {

		String id = getID(block.getLocation()).concat(".");
		trashBins.get().set(id + path, value);
	}

}
