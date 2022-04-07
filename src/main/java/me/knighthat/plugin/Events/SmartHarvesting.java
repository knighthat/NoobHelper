package me.knighthat.plugin.Events;

import java.util.List;

import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.knighthat.plugin.files.Config;

public class SmartHarvesting
{

	public SmartHarvesting(final Block block, Player player, Config config) {

		if ( !checkTools(player.getInventory().getItemInMainHand(), config) )
			return;

		final Ageable crop = (Ageable) block.getBlockData();

		if ( crop.getAge() != crop.getMaximumAge() ) { return; }

		block.breakNaturally();
		block.setType(crop.getMaterial());
		if ( config.get().getBoolean("smart_harvest.sound") )
			block.getWorld().playSound(block.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
	}

	boolean checkTools( ItemStack itemInMainHand, Config config ) {

		List<String> tools = config.get().getStringList("smart_harvest.special_tools");

		for ( String tool : tools )
			if ( itemInMainHand.getType().name().endsWith(tool.toUpperCase()) )
				return true;

		return false;
	}

}
