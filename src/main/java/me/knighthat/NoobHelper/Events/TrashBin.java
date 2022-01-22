package me.knighthat.NoobHelper.Events;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import me.knighthat.NoobHelper.NoobHelper;

public class TrashBin extends EventStorage
{

	public TrashBin(NoobHelper plugin) {
		super(plugin);
	}

	private static String getID( Location location ) {
		int x = (int) location.getX();
		int y = (int) location.getY();
		int z = (int) location.getZ();
		return ".sign_" + x + y + z;
	}

	public void enterTrashBin( Player player, Block block ) {

		final Location loc = block.getLocation();

		if ( blockdata.get().contains(loc.getWorld().getName() + "." + getID(loc)) ) {
			Inventory inv = plugin.getServer().createInventory(player, InventoryType.CHEST,
					addColor(confString("trash_bin.title")));
			player.openInventory(inv);
		}
	}

	public String[] placeTrashBin( SignChangeEvent e ) {

		List<String> newLines = confStringList("trash_bin.lines");
		for ( String line : newLines ) {
			line = addColor(line);
		}

		setData(e.getPlayer(), e.getBlock().getLocation());

		e.getPlayer().sendMessage(addColor(confString("trash_bin.created_message")));

		String[] str = new String[4];
		for ( int i = 0 ; i < newLines.size() ; i++ ) {
			str[i] = newLines.get(i);
		}
		return str;
	}

	public void removeTrashBin( Location loc ) {
		final String id = loc.getWorld().getName() + getID(loc);
		blockdata.get().set(id, null);
		blockdata.save();
	}

	private void setData( Player player, Location loc ) {
		final String worldName = loc.getWorld().getName();
		final String id = getID(loc);
		blockdata.get().set(worldName + id + ".X", loc.getX());
		blockdata.get().set(worldName + id + ".Y", loc.getY());
		blockdata.get().set(worldName + id + ".Z", loc.getZ());
		blockdata.get().set(worldName + id + ".Owner", player.getName());
		blockdata.save();
	}
}
