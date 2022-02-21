package me.knighthat.plugin.Events.BreakAssistant;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;

import me.knighthat.plugin.Files.Config;

public class BreakAssistant extends Storage
{
	public BreakAssistant(Config config, BlockBreakEvent e) {

		this.config = config;
		this.player = e.getPlayer();

		if ( checkTool() & checkRequirements() )
			assist(getAffiliation(e.getBlock(), config.get().getInt(path + "max_block")));

	}

	void assist( List<Block> blocks ) {

		boolean addHungry = isEnabled(path + "food_consumption.enabled"), addDamage = isEnabled(path + "apply_damage");

		for ( int i = 0 ; i < blocks.size() ; i++ ) {

			if ( addHungry & !addHungry(i) ) { break; }

			if ( addDamage & !addDamage() ) { break; }

			blocks.get(i).breakNaturally(player.getInventory().getItemInMainHand());
		}
	}
}
