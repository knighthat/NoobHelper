package me.knighthat.NoobHelper.Events;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ToolReplacement
{

	public ToolReplacement(PlayerInventory pInv, ItemStack item) {
		if ( !isSimilar(pInv.getItemInMainHand(), item) )
			return;

		for ( int slot = 0 ; slot < pInv.getSize() ; slot++ )
			if ( pInv.getItem(slot) != null && isSimilar(pInv.getItem(slot), item) )
				if ( slot != pInv.getHeldItemSlot() ) {
					swapItems(pInv, slot, pInv.getHeldItemSlot());
					break;
				}

	}

	private boolean isSimilar( ItemStack item1, ItemStack item2 ) {
		return item1.getType().equals(item2.getType());
	}

	private void swapItems( PlayerInventory pInv, int slot1, int slot2 ) {
		pInv.setItem(slot2, pInv.getItem(slot1));
		pInv.clear(slot1);
	}
}
