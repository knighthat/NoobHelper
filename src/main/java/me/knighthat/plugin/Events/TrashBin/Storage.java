package me.knighthat.plugin.Events.TrashBin;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.knighthat.plugin.Misc;
import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.Files.BlockDataFile;
import me.knighthat.plugin.Files.ConfigFile;

public abstract class Storage implements me.knighthat.plugin.Events.Storage
{
	ConfigFile config;
	BlockDataFile blockData;

	Player player;
	Block block;
	Location location;

	final String path = "trash_bin.";

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

	String generateID() { return generateID(location.getWorld().getName(), location); }

	Boolean checkPermission( String permission ) {
		return Misc.checkPermission(player, config, path.concat(permission), true);
	}

	void addData() {

		setData("X", location.getBlockX());
		setData("Y", location.getBlockY());
		setData("Z", location.getBlockZ());
		setData("Owner", player.getName());
		blockData.save();
	}

	void setData( String path, Object value ) {

		String id = getID(block.getLocation()).concat(".");
		blockData.get().set(id + path, value);
	}

}
