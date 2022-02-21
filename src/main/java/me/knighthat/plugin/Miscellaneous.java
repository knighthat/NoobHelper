package me.knighthat.plugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.knighthat.plugin.Files.Config;
import me.knighthat.plugin.Files.Files;

public interface Miscellaneous extends ConsoleSender
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

	default <T extends Files> Map<Integer, ItemStack> getItemList( T file, String path ) {

		Map<Integer, ItemStack> result = new HashMap<>();

		Iterator<String> slots = file.getSections(path, false).iterator();
		while ( slots.hasNext() ) {
			String slot = slots.next();
			result.put(Integer.parseInt(slot), file.get().getItemStack(path.concat("." + slot)));
		}

		return result;
	}

}
