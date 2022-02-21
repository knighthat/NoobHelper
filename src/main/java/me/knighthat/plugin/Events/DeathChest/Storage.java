package me.knighthat.plugin.Events.DeathChest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.Files.Config;
import me.knighthat.plugin.Files.DeathChests;

public abstract class Storage implements me.knighthat.plugin.Events.Storage
{

	NoobHelper plugin;
	Config config;
	DeathChests deathChests;

	Player player;
	String playerName;
	UUID uuid;
	String pSection;

	Chest chest;
	Location location;

	Map<Integer, ItemStack> contents = new HashMap<>();

	final String path = "death_chest.";

	void initialization( NoobHelper plugin, Player player, @Nullable Block block ) {

		this.plugin = plugin;
		this.config = plugin.config;
		this.deathChests = plugin.deathChests;
		this.player = player;
		this.playerName = player.getName();
		this.uuid = player.getUniqueId();
		this.pSection = playerName.concat("_" + uuid);
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

		set("X", location.getBlockX());
		set("Y", location.getBlockY());
		set("Z", location.getBlockZ());
		set("Exp", player.getTotalExperience());
		for ( int slot : contents.keySet() ) { set("items." + slot, contents.get(slot)); }
		deathChests.save();
	}

	void set( String path, Object value ) {

		String header = pSection.concat("." + location.getWorld().getName());
		deathChests.get().set(header.concat("." + generateID() + "." + path), value);
	}

	public String generateID() { return generateID("", location); }
}
