package me.knighthat.plugin.Events.TrashBin;

import org.bukkit.event.player.PlayerInteractEvent;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.Menus.TrashBinMenu;

public class Use extends TrashBinStorage
{

	public Use(NoobHelper plugin, PlayerInteractEvent e) {

		super(plugin, e.getPlayer(), e.getClickedBlock());

		if ( !plugin.trashBins.get().contains(getId()) ) { return; }

		if ( !checkPermission(player, plugin.config, "use", true) ) { return; }

		new TrashBinMenu(plugin, player, PATH).open();
	}
}