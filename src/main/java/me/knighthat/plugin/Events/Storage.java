package me.knighthat.plugin.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Leaves;

public interface Storage
{

	public default List<Block> getAffiliation( Block key, int maxBlocks ) {

		maxBlocks = maxBlocks == 0 ? 200 : maxBlocks;

		return getAffiliation(key, initiation(key), maxBlocks);
	}

	public default List<Block> getAffiliation( Block key, Map<Block, List<Block>> map, int maxBlocks ) {

		List<Block> oldBLocks = new ArrayList<Block>(map.get(key));

		if ( !(key.getBlockData() instanceof Leaves) & oldBLocks.size() >= maxBlocks )
			return oldBLocks;

		oldBLocks.stream().forEach(block -> {

			for ( Block b : getFacings(block) )
				if ( !map.get(key).contains(b) )
					map.get(key).add(b);

		});

		if ( map.get(key).size() > oldBLocks.size() )
			getAffiliation(key, map, maxBlocks);

		return map.get(key);
	}

	private boolean isSimilar( Block block1, Block block2 ) {

		return block1.getType().equals(block2.getType());

	}

	private List<Block> getFacings( Block block ) {

		List<Block> blocks = new ArrayList<>();

		for ( BlockFace face : BlockFace.values() )
			if ( isSimilar(block, block.getRelative(face)) )
				blocks.add(block.getRelative(face));

		return blocks;

	}

	private Map<Block, List<Block>> initiation( Block key ) {
		Map<Block, List<Block>> map = new HashMap<>();
		map.put(key, new ArrayList<>());
		map.get(key).add(key);
		return map;
	}

}
