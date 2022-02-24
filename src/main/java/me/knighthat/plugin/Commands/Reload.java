package me.knighthat.plugin.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.knighthat.NoobHelper;
import me.knighthat.utils.ConsoleSender;
import me.knighthat.utils.PermissionChecker;

public class Reload implements PermissionChecker, ConsoleSender
{

	public Reload(NoobHelper plugin, CommandSender sender, Boolean isPlayer) {

		if ( isPlayer )
			if ( !checkPermission((Player) sender, plugin.config, "command.reload", true) ) { return; }

		plugin.config.reload();
		plugin.trashBins.reload();
		plugin.deathChests.reload();

		sender.sendMessage(plugin.config.getString("reload", true));

		if ( isPlayer ) { sendConsoleMessage(plugin, (Player) sender); }

		return;
	}

	private void sendConsoleMessage( NoobHelper plugin, Player player ) {
		String message = plugin.config.getString("player_reload").replaceAll("%player%", player.getName());
		sendWarning(plugin, message);
	}
}
