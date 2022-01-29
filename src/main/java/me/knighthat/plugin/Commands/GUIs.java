package me.knighthat.plugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import me.knighthat.plugin.Misc;
import me.knighthat.plugin.NoobHelper;

public class GUIs implements CommandExecutor
{
	NoobHelper plugin;

	String type;

	public GUIs(NoobHelper plugin, String type) {
		this.plugin = plugin;
		this.type = type;

	}

	@Override
	public boolean onCommand( CommandSender sender, Command command, String label, String[] args ) {

		if ( !(sender instanceof Player) )
			return true;

		final Player player = (Player) sender;

		if ( !Misc.checkPermission(player, plugin.config, "command." + type) )
			return true;

		InventoryType invType = InventoryType.valueOf(type.toUpperCase());
		Inventory inv = plugin.getServer().createInventory(player, invType);
		player.openInventory(inv);

		return true;
	}
}
