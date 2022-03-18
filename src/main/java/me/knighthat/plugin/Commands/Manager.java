package me.knighthat.plugin.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.utils.PermissionChecker;

public class Manager implements CommandExecutor, PermissionChecker
{

	NoobHelper plugin;

	public static List<CommandAbstact> defCmds = new ArrayList<>();
	public static Map<CommandSender, List<Object>> confirmCountdown = new HashMap<>();

	public Manager(NoobHelper plugin) {

		this.plugin = plugin;

		defCmds.add(new LostItems(plugin));
		defCmds.add(new DeleteChest(plugin));
		defCmds.add(new Reload(plugin));
	}

	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {

		Iterator<CommandAbstact> cmds = defCmds.iterator();
		while ( cmds.hasNext() ) {

			CommandAbstact cmd = cmds.next();

			if ( !cmd.getName().equalsIgnoreCase(args[0]) )
				continue;

			if ( cmd.requiredPlayer() ) {

				if ( !(sender instanceof Player) )
					break;

				if ( !checkPermission((Player) sender, plugin.config, "command." + cmd.getPermission(), true) )
					break;
			}
			cmd.onCommand(sender, args);
			break;
		}
		return true;
	}

}
