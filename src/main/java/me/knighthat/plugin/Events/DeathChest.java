package me.knighthat.plugin.Events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.Files.BlockDataFile;
import me.knighthat.plugin.Files.ConfigFile;

public class DeathChest
{

	NoobHelper plugin;
	ConfigFile config;
	BlockDataFile blockData;

	Player player;
	Chest chest;
	Location location;

	Map<Integer, ItemStack> contents = new HashMap<>();

	final String path = "death_chest.";

	public DeathChest(NoobHelper plugin, Player player) {

		initialization(plugin, player);

		if ( !playerContents() )
			return;

		location.getBlock().setType(Material.CHEST);

		registerContents();

		sendMessage();
	}

	public DeathChest(NoobHelper plugin, PlayerInteractEvent e) {

		initialization(plugin, e.getPlayer());

		location = e.getClickedBlock().getLocation();

		if ( !blockData.get().contains(generateID()) )
			return;

		String id = generateID();

		boolean isOwner = blockData.getString(id.concat(".UUID")).equals(player.getUniqueId().toString());

		if ( isOwner ) {
			for ( String path : blockData.getSections(id.concat(".items"), false) )
				player.getInventory().addItem(blockData.get().getItemStack(id.concat(".items." + path)));
			player.updateInventory();

			location.getBlock().setType(Material.AIR);

			if ( config.get().getBoolean("death_chest.play_sound") )
				location.getWorld().playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

			blockData.get().set(id, null);
			blockData.save();
		}

		String path = this.path + (isOwner ? "retrieved" : "not_your_chest");

		player.sendMessage(config.getString(path, true));

		e.setCancelled(true);

	}

	String generateID() {
		final String serialNumber = "" + location.getBlockX() + location.getBlockY() + location.getBlockZ();
		return path.concat(serialNumber);
	}

	void registerContents() {

		set("Name", player.getName());
		set("UUID", player.getUniqueId().toString());
		set("X", location.getBlockX());
		set("Y", location.getBlockY());
		set("Z", location.getBlockZ());
		for ( int slot : contents.keySet() )
			set("items." + slot, contents.get(slot));
		blockData.save();
	}

	void set( String path, Object value ) {
		blockData.get().set(generateID().concat(".") + path, value);
	}

	void initialization( NoobHelper plugin, Player player ) {

		this.plugin = plugin;
		this.config = plugin.config;
		this.blockData = plugin.blockdata;
		this.player = player;
		this.location = player.getLocation();
	}

	boolean playerContents() {
		PlayerInventory pInv = player.getInventory();
		for ( int slot = 0 ; slot < pInv.getSize() ; slot++ )
			if ( pInv.getItem(slot) != null )
				contents.put(slot, pInv.getItem(slot));
		return !contents.isEmpty();
	}

	void sendMessage() {

		int x = location.getBlockX(), y = location.getBlockY(), z = location.getBlockZ();
		String X = String.valueOf(x), Y = String.valueOf(y), Z = String.valueOf(z);

		String msg = config.getString(path + "message", true);
		msg = msg.replace("%x%", X).replace("%y%", Y).replace("%z%", Z);

		player.sendMessage(msg);
	}
}
