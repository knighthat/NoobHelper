package me.knighthat.plugin.Events.DeathChest;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.knighthat.plugin.NoobHelper;

public class Retrieval extends Storage
{

	String id;

	public Retrieval(NoobHelper plugin, @Nullable PlayerInteractEvent PIE, @Nullable BlockBreakEvent BBE) {

		boolean isPIE = PIE != null;

		if ( isPIE ) {
			initialization(plugin, PIE.getPlayer(), PIE.getClickedBlock());
		} else
			initialization(plugin, BBE.getPlayer(), BBE.getBlock());

		id = "." + generateID();

		for ( String section : deathChests.getSections("", false) )
			for ( World world : Bukkit.getWorlds() )
				if ( deathChests.get().contains(section.concat("." + world.getName() + id)) ) {

					if ( isPIE ) {
						PIE.setCancelled(true);
					} else
						BBE.setCancelled(true);

					boolean isOwner = pSection.equals(section);

					pSection = pSection.concat("." + world.getName() + id);

					if ( isOwner ) {

						returnItems();

						magic();

						removeData();
					}

					final String path = this.path + (isOwner ? "retrieved" : "not_your_chest");
					player.sendMessage(config.getString(path, true));
				}

	}

	void returnItems() {

		PlayerInventory pInv = player.getInventory();

		player.giveExp(deathChests.get().getInt(pSection.concat(".Exp")));

		for ( String path : deathChests.getSections(pSection.concat(".items"), false) ) {

			int slot = Integer.parseInt(path);
			ItemStack item = deathChests.get().getItemStack(pSection.concat(".items." + path));

			if ( pInv.firstEmpty() >= 0 ) {

				if ( pInv.getItem(slot) != null ) {
					pInv.addItem(item);
				} else
					pInv.setItem(slot, item);
			} else
				location.getWorld().dropItem(location, item);
		}
		player.updateInventory();
	}

	void removeData() {

		deathChests.get().set(pSection, null);
		deathChests.save();
	}

	void magic() {

		location.getBlock().setType(Material.AIR);

		if ( config.get().getBoolean("death_chest.retrieval_sound") )
			location.getWorld().playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
	}

	ItemStack getItemStack( String path ) {
		return deathChests.get().getItemStack(generateID().concat(".items." + path));
	}
}
