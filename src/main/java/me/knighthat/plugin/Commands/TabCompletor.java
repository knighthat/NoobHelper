package me.knighthat.plugin.Commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabCompletor implements TabCompleter
{

	@Override
	public List<String> onTabComplete( CommandSender sender, Command command, String alias, String[] args ) {
		List<String> results = new ArrayList<>();

		switch ( args.length ) {
			case 1 :
				results = Manager.defCmds.stream().map(CommandAbstact::getName).toList();
			break;

			case 2 :
				Iterator<CommandAbstact> cmds = Manager.defCmds.iterator();
				while ( cmds.hasNext() ) {
					CommandAbstact cmd = cmds.next();
					if ( cmd.getName().equalsIgnoreCase(args[0]) ) {
						results.addAll(cmd.onTabComplete(sender, args));
						break;
					}
				}
		}
		return results.stream().filter($ -> $.startsWith(args[args.length - 1])).toList();
	}

}
