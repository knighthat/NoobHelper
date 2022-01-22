package me.knighthat.NoobHelper.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import me.knighthat.NoobHelper.FileGetters;
import me.knighthat.NoobHelper.NoobHelper;

public class Loom extends FileGetters implements CommandExecutor
{

	public Loom(NoobHelper plugin) {
		super(plugin);
	}

	private String className = "loom";

	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {

		if ( !(sender instanceof Player) )
			return true;

		Player player = (Player) sender;

		if ( confBoolean(cShortcuts + className) & !player.hasPermission(cmdPerm + className) )
			return true;

		Inventory inv = plugin.getServer().createInventory(player, InventoryType.valueOf(className.toUpperCase()));

		player.openInventory(inv);

		return true;
	}

}
