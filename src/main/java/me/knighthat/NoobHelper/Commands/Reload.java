package me.knighthat.NoobHelper.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.knighthat.NoobHelper.FileGetters;
import me.knighthat.NoobHelper.NoobHelper;

public class Reload extends FileGetters implements CommandExecutor
{

	public Reload(NoobHelper plugin) {
		super(plugin);
	}

	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {

		if ( !sender.hasPermission("noobhelper.command.reload") )
			return true;

		config.reload();

		sender.sendMessage(addColor("&aAll files had been reloaded!"));

		if ( sender instanceof Player ) {
			printWarn(((Player) sender).getName() + " just reloaded plugin!");
		}

		return true;
	}

}
