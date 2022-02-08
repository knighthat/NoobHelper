package me.knighthat.plugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.knighthat.plugin.Misc;
import me.knighthat.plugin.NoobHelper;

public class Reload implements CommandExecutor
{

	NoobHelper plugin;

	public Reload(NoobHelper plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {

		if ( !args[0].equalsIgnoreCase("reload") )
			return true;

		final boolean isPlayer = sender instanceof Player;

		if ( isPlayer )
			if ( !Misc.checkPermission((Player) sender, plugin.config, "command.reload") )
				return true;

		plugin.config.reload();
		plugin.blockdata.reload();

		sender.sendMessage(plugin.config.getString("reload"));

		if ( isPlayer )
			sendConsoleMessage((Player) sender);

		return true;
	}

	private void sendConsoleMessage( Player player ) {
		String message = plugin.config.getString("player_reload").replaceAll("%player%", player.getName());
		Misc.sendWarning(plugin, message);
	}

}
