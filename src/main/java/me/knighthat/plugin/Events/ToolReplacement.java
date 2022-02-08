package me.knighthat.plugin.Events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ToolReplacement
{

	public ToolReplacement(Player player, ItemStack brokenItem) {

		PlayerInventory pInv = player.getInventory();
		Map<Integer, ItemStack> content = new HashMap<>();
		int originalSlot = 0;

		for ( int slot = 0 ; slot < pInv.getSize() ; slot++ )
			if ( pInv.getItem(slot) != null )
				content.put(slot, pInv.getItem(slot));

		for ( int slot : content.keySet() )
			if ( pInv.getItem(slot).equals(brokenItem) )
				originalSlot = slot;

		content.remove(originalSlot);

		for ( int slot : content.keySet() )
			if ( pInv.getItem(slot).getType().equals(brokenItem.getType()) ) {
				pInv.setItem(originalSlot, pInv.getItem(slot));
				pInv.clear(slot);
				player.updateInventory();
				break;
			}
	}

}
