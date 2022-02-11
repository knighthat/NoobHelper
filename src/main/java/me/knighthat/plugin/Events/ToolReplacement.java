package me.knighthat.plugin.Events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ToolReplacement
{

	PlayerInventory pInv;
	int originalSlot;

	public ToolReplacement(Player player, ItemStack brokenItem) {

		pInv = player.getInventory();
		Map<Integer, ItemStack> contents = getContents(brokenItem);

		for ( int slot : contents.keySet() )
			if ( pInv.getItem(slot).getType().equals(brokenItem.getType()) ) {
				pInv.setItem(originalSlot, pInv.getItem(slot));
				pInv.clear(slot);
				player.updateInventory();
				break;
			}
	}

	Map<Integer, ItemStack> getContents( ItemStack brokenItem ) {

		Map<Integer, ItemStack> contents = new HashMap<>();

		for ( int slot = 0 ; slot < pInv.getSize() ; slot++ )
			if ( pInv.getItem(slot) != null )
				if ( pInv.getItem(slot).equals(brokenItem) ) {
					originalSlot = slot;
				} else
					contents.put(slot, pInv.getItem(slot));

		return contents;
	}

}
