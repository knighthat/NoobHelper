package me.knighthat.plugin.Events;

import org.bukkit.block.data.Ageable;
import org.bukkit.event.player.PlayerInteractEvent;

public class SmartHarvesting
{

	public SmartHarvesting(PlayerInteractEvent e) {

		final Ageable crop = (Ageable) e.getClickedBlock().getBlockData();

		if ( crop.getAge() != crop.getMaximumAge() )
			return;

		e.getClickedBlock().breakNaturally();
		e.getClickedBlock().setType(crop.getMaterial());
	}

}
