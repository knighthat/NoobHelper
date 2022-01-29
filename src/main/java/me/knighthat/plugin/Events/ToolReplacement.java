package me.knighthat.plugin.Events;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ToolReplacement
{
	public ToolReplacement(PlayerItemBreakEvent e) {

		final PlayerInventory pInv = e.getPlayer().getInventory();
		final Material material = e.getBrokenItem().getType();

		if ( !isSimilar(e.getBrokenItem(), pInv.getItemInMainHand()) )
			return;

		if ( !pInv.contains(material, 2) )
			return;

		for ( int slot : pInv.all(material).keySet() )
			if ( pInv.getHeldItemSlot() != slot ) {
				swapItems(pInv, slot);
				break;
			}

	}

	private boolean isSimilar( ItemStack item1, ItemStack item2 ) {
		return item1.getType().equals(item2.getType());
	}

	private void swapItems( PlayerInventory pInv, int slot ) {
		pInv.setItem(pInv.getHeldItemSlot(), pInv.getItem(slot));
		pInv.clear(slot);
	}

}
