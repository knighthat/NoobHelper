package me.knighthat.plugin.Events;

import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.player.PlayerInteractEvent;

public class SmartHarvesting
{

	public SmartHarvesting(PlayerInteractEvent e, boolean playSound) {

		final Block block = e.getClickedBlock();
		final Ageable crop = (Ageable) block.getBlockData();

		if ( crop.getAge() != crop.getMaximumAge() )
			return;

		block.breakNaturally();
		block.setType(crop.getMaterial());
		if ( playSound )
			block.getWorld().playSound(block.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
	}

}
