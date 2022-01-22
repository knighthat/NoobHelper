package me.knighthat.NoobHelper.Events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;

public class SmartHarvest
{

	public SmartHarvest(Block block) {
		final Material material = block.getType();
		Ageable crop = (Ageable) block.getBlockData();
		if ( crop.getAge() != crop.getMaximumAge() )
			return;
		block.breakNaturally();
		block.setType(material);
	}

}
