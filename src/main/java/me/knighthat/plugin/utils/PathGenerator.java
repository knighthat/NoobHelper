package me.knighthat.plugin.utils;

import java.util.StringJoiner;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface PathGenerator
{
	public Player getPlayer();

	public default String getPlayerPath() { return getPlayer().getName() + "_" + getPlayer().getUniqueId().toString(); }

	public default String getWorldName( Location location ) { return location != null ? location.getWorld().getName() : getPlayer().getWorld().getName(); }

	public default String getId( Location location ) {
		Validate.notNull(location, "location cannot be null");
		return "" + location.getBlockX() + location.getBlockY() + location.getBlockZ();
	}

	public default String getFullPath( String id ) {
		Validate.notNull(id, "id of path cannot be null");
		StringJoiner joiner = new StringJoiner(".");
		joiner = joiner.add(getPlayerPath()).add(getWorldName(null)).add(id);
		return joiner.toString();
	}

	public default String getFullPath( Location location ) {
		StringJoiner joiner = new StringJoiner(".");
		joiner = joiner.add(getPlayerPath()).add(getWorldName(location)).add(getId(location));
		return joiner.toString();
	}

}
