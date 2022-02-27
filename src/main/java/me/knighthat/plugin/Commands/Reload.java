package me.knighthat.plugin.Commands;

import java.util.logging.Level;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.utils.PermissionChecker;

public class Reload implements PermissionChecker
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
		plugin.getLogger().log(Level.WARNING, message);
	}
}
