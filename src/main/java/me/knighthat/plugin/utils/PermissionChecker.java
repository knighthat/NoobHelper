package me.knighthat.plugin.utils;

import org.bukkit.entity.Player;

import me.knighthat.plugin.files.Config;

public interface PermissionChecker
{

	default boolean checkPermission( Player player, Config config, String permission ) { return checkPermission(player, config, permission, false); }

	default boolean checkPermission( Player player, Config config, String permission, boolean sendNoPermMsg ) {

		if ( !permission.startsWith("noobhelper.") ) { permission = "noobhelper." + permission; }

		final String message = config.getString("no_permission", true, player, null).replace("%perm%", permission);

		if ( !player.hasPermission(permission) ) {

			if ( sendNoPermMsg ) { player.sendMessage(message); }

			return false;
		}
		return true;
	}

}
