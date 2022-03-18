package me.knighthat.plugin.Events.TrashBin;

import org.bukkit.event.block.SignChangeEvent;

import me.knighthat.plugin.NoobHelper;

public class Place extends TrashBinStorage
{

	public Place(NoobHelper plugin, SignChangeEvent e) {

		super(plugin, e.getPlayer(), e.getBlock());

		if ( !checkPermission("place") ) { return; }

		if ( !e.getLine(0).equalsIgnoreCase("[Trash Bin]") ) { return; }

		for ( int line = 0 ; line < 4 ; line++ )
			e.setLine(line, plugin.config.get().getStringList(PATH.concat("lines")).get(line));

		addData();
		player.sendMessage(plugin.config.getString(PATH.concat("message"), true, player, null));
	}

	void addData() {

		setData("X", location.getBlockX());
		setData("Y", location.getBlockY());
		setData("Z", location.getBlockZ());
		setData("Owner", player.getName());
		trashBins.save();

	}

	void setData( String path, Object value ) { trashBins.get().set(getId().concat("." + path), value); }

}
