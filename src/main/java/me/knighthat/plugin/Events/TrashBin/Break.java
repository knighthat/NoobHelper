package me.knighthat.plugin.Events.TrashBin;

import org.bukkit.event.block.BlockBreakEvent;

import me.knighthat.plugin.NoobHelper;

public class Break extends TrashBinStorage
{

	public Break(NoobHelper plugin, BlockBreakEvent e) {

		super(plugin, e.getPlayer(), e.getBlock());

		if ( trashBins.get().contains(getId()) )
			if ( checkPermission(player, plugin.config, "trashbin.remove", true) ) {

				trashBins.get().set(getId(), null);
				trashBins.save();

			} else
				e.setCancelled(true);
	}
}
