package me.knighthat.utils;

import org.bukkit.entity.Player;

import me.knighthat.plugin.Files.Config;

public interface PermissionChecker extends TextModification
{

	default boolean checkPermission( Player player, Config config, String permission ) {
		return checkPermission(player, config, permission, false);
	}

	default boolean checkPermission( Player player, Config config, String permission, boolean sendNoPermMsg ) {

		if ( !permission.startsWith("noobhelper.") ) { permission = "noobhelper." + permission; }

		final String message = config.getString("no_permission", true).replace("%perm%", permission);

		if ( !player.hasPermission(permission) ) {

			if ( sendNoPermMsg ) { player.sendMessage(message); }

			return false;
		}
		return true;
	}

}
