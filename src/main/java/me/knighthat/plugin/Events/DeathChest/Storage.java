package me.knighthat.plugin.Events.DeathChest;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.Files.BlockDataFile;
import me.knighthat.plugin.Files.ConfigFile;

public abstract class Storage implements me.knighthat.plugin.Events.Storage
{

	NoobHelper plugin;
	ConfigFile config;
	BlockDataFile blockData;

	Player player;
	Chest chest;
	Location location;

	Map<Integer, ItemStack> contents = new HashMap<>();

	final String path = "death_chest.";

	void initialization( NoobHelper plugin, Player player, @Nullable Block block ) {

		this.plugin = plugin;
		this.config = plugin.config;
		this.blockData = plugin.blockdata;
		this.player = player;
		this.location = block == null ? player.getLocation() : block.getLocation();
	}

	boolean playerContents() {

		PlayerInventory pInv = player.getInventory();

		for ( int slot = 0 ; slot < pInv.getSize() ; slot++ )
			if ( pInv.getItem(slot) != null )
				contents.put(slot, pInv.getItem(slot));

		return !contents.isEmpty();
	}

	void registerContents() {

		set("Name", player.getName());
		set("UUID", player.getUniqueId().toString());
		set("X", location.getBlockX());
		set("Y", location.getBlockY());
		set("Z", location.getBlockZ());
		for ( int slot : contents.keySet() ) { set("items." + slot, contents.get(slot)); }
		blockData.save();
	}

	void set( String path, Object value ) { blockData.get().set(generateID().concat("." + path), value); }

	public String generateID() { return generateID(path, location); }

	void sendMessage() {

		int x = location.getBlockX(), y = location.getBlockY(), z = location.getBlockZ();
		String X = String.valueOf(x), Y = String.valueOf(y), Z = String.valueOf(z);

		String msg = config.getString(path + "message", true);
		msg = msg.replace("%x%", X).replace("%y%", Y).replace("%z%", Z);

		player.sendMessage(msg);
	}

}
