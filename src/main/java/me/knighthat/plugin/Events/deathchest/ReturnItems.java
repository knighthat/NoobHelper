package me.knighthat.plugin.Events.deathchest;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.files.DeathChests;
import me.knighthat.plugin.utils.PathGenerator;

public class ReturnItems implements PathGenerator
{

	Player player;

	public ReturnItems(NoobHelper plugin, Player player, Location bLocation, Cancellable event) {

		this.player = player;

		DeathChests deathChets = plugin.deathChests;

		if ( !isExist(bLocation, deathChets) ) { return; }

		event.setCancelled(true);

		boolean isOwner = deathChets.get().contains(getFullPath(bLocation));

		String msgPath = "death_chest." + (isOwner ? "retrieved" : "not_your_chest");
		String x = String.valueOf(bLocation.getBlockX());
		String y = String.valueOf(bLocation.getBlockY());
		String z = String.valueOf(bLocation.getBlockZ());

		if ( isOwner ) {

			returnItems(bLocation, deathChets.get().getConfigurationSection(getFullPath(bLocation)));

			bLocation.getBlock().setType(Material.AIR);

			if ( plugin.config.get().getBoolean("death_chest.retrieval_sound") )
				bLocation.getWorld().playSound(bLocation, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

			deathChets.get().set(getFullPath(bLocation), null);
			deathChets.save();
		}

		player.sendMessage(plugin.config.getString(msgPath, true, player, new String[] { x, y, z }));

	}

	@Override
	public Player getPlayer() { return this.player; }

	boolean isExist( Location location, DeathChests storage ) {

		for ( String section : storage.getSections("", true) )
			if ( section.endsWith(getId(location)) )
				return true;

		return false;
	}

	void returnItems( Location bLcation, ConfigurationSection section ) {

		player.giveExp(section.getInt("Exp"));

		section.getConfigurationSection("items").getKeys(false).forEach(slotString -> {

			ItemStack item = section.getItemStack("items." + slotString);

			PlayerInventory inv = player.getInventory();

			if ( inv.firstEmpty() > -1 ) {

				int slot = Integer.parseInt(slotString);

				if ( inv.getItem(slot) != null )
					slot = inv.firstEmpty();

				inv.setItem(slot, item);

			} else
				bLcation.getWorld().dropItem(bLcation, item);

		});

		player.updateInventory();
	}
}
