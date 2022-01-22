package me.knighthat.NoobHelper.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Leaves;

import me.knighthat.NoobHelper.FileGetters;
import me.knighthat.NoobHelper.NoobHelper;

public class EventStorage extends FileGetters
{

	public EventStorage(NoobHelper plugin) {
		super(plugin);
	}

	protected List<Block> getAffiliation( Block block ) {
		return getAffiliation(block, init(block));
	}

	protected List<Block> getAffiliation( Block key, HashMap<Block, List<Block>> map ) {

		List<Block> oldBlocks = new ArrayList<>(map.get(key));

		if ( !(key.getBlockData() instanceof Leaves) & oldBlocks.size() >= confInt("block_break_assist.max_block") )
			return oldBlocks;

		for ( Block b : oldBlocks ) {
			for ( Block faceWith : getFacings(b) )
				if ( !map.get(key).contains(faceWith) ) {
					map.get(key).add(faceWith);
				}
		}

		if ( map.get(key).size() > oldBlocks.size() ) {
			getAffiliation(key, map);
		}

		return map.get(key);
	}

	private List<Block> getFacings( Block block ) {
		List<Block> blocks = new ArrayList<>();
		for ( BlockFace bf : BlockFace.values() )
			if ( block.getRelative(bf).getType().equals(block.getType()) ) {
				blocks.add(block.getRelative(bf));
			}
		return blocks;
	}

	private HashMap<Block, List<Block>> init( Block block ) {
		HashMap<Block, List<Block>> map = new HashMap<>();
		map.put(block, new ArrayList<>());
		map.get(block).add(block);
		return map;
	}
}
