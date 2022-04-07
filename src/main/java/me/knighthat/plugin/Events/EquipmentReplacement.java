package me.knighthat.plugin.Events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.knighthat.plugin.files.Config;
import me.knighthat.plugin.utils.EquipmentType;
import me.knighthat.plugin.utils.PermissionChecker;

public class EquipmentReplacement implements PermissionChecker
{

	Config config;

	PlayerInventory pInv;
	int originalSlot;

	public EquipmentReplacement(Config config, Player player, ItemStack brokenItem) {

		this.config = config;

		pInv = player.getInventory();
		Map<Integer, ItemStack> contents = getContents(brokenItem);

		String equipmentType = EquipmentType.match(brokenItem).toString().toLowerCase();
		boolean isEnabled = isEnabled("types." + equipmentType);

		if ( isEnabled("required_permission") & !checkPermission(player, config, "noobhelper.equipment_replacement." + equipmentType) )
			return;

		if ( isEnabled )
			for ( int slot : contents.keySet() )
				if ( pInv.getItem(slot).getType().equals(brokenItem.getType()) ) {
					swapItems(player, slot);
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

	void swapItems( Player player, Integer slot ) {
		pInv.setItem(originalSlot, pInv.getItem(slot));
		pInv.clear(slot);
		player.updateInventory();
	}

	boolean isEnabled( String path ) { return config.get().getBoolean("equipment_replacement." + path); }
}