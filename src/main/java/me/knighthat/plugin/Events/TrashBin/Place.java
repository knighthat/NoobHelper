package me.knighthat.plugin.Events.TrashBin;

import org.bukkit.event.block.SignChangeEvent;

import me.knighthat.NoobHelper;

public class Place extends Storage
{

	public Place(NoobHelper plugin, SignChangeEvent e) {

		register(plugin, e.getPlayer(), e.getBlock());

		if ( !checkPermission("place") ) { return; }

		if ( !e.getLine(0).equalsIgnoreCase("[Trash Bin]") ) { return; }

		for ( int line = 0 ; line < 4 ; line++ )
			e.setLine(line, config.get().getStringList(path.concat("lines")).get(line));

		addData();
		player.sendMessage(config.getString(path.concat("message"), true));
	}

}
