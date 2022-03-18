package me.knighthat.plugin.Commands;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import javax.annotation.Nullable;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.knighthat.plugin.NoobHelper;

public class Reload extends CommandAbstact
{

	public Reload(NoobHelper plugin) { super(plugin); }

	@Override
	public String getName() { return "reload"; }

	@Override
	public String getPermission() { return "reload"; }

	@Override
	public boolean requiredPlayer() { return false; }

	@Override
	public void onCommand( CommandSender sender, String[] args ) {

		config.reload();
		deathChests.reload();
		trashBins.reload();

		plugin.registerCommands();

		sender.sendMessage(plugin.config.getString("reload", true, null, null));

		if ( sender instanceof Player ) {

			Player p = (Player) sender;
			String message = plugin.config.getString("player_reload");
			message = message.replace("%player%", p.getName()).replace("%display%", p.getDisplayName());
			plugin.getLogger().log(Level.WARNING, message);
		}
	}

	@Override
	public @Nullable List<String> onTabComplete( CommandSender sender, String[] args ) {

		if ( args.length <= 1 )
			return Arrays.asList(getName());

		return null;
	}

}
