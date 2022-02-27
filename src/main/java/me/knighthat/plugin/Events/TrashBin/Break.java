package me.knighthat.plugin.Events.TrashBin;

import org.bukkit.event.block.BlockBreakEvent;

import me.knighthat.plugin.NoobHelper;

public class Break extends Storage
{

	public Break(NoobHelper plugin, BlockBreakEvent e) {

		register(plugin, e.getPlayer(), e.getBlock());

		final String id = getID(e.getBlock().getLocation());

		if ( trashBins.get().contains(id) )
			if ( checkPermission("remove") ) {
				trashBins.get().set(id, null);
				trashBins.save();
			} else
				e.setCancelled(true);
	}

}
