package me.knighthat.plugin.Events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public abstract class Storage
{
	protected Block starter;
	protected List<Block> blocks = new ArrayList<>();

	public abstract int getMaxBlock();

	protected List<Block> getAffiliation( List<Block> oldBlocks ) {

		if ( this.blocks.size() <= oldBlocks.size() ) { return this.blocks; }

		List<Block> result = new ArrayList<>(this.blocks);
		result.stream().filter(block -> !oldBlocks.contains(block)).forEach(block -> {

			for ( BlockFace blockFace : BlockFace.values() ) {
				Block faceWith = block.getRelative(blockFace);
				if ( this.blocks.size() < getMaxBlock() )
					if ( faceWith.getType().equals(starter.getType()) & !this.blocks.contains(faceWith) )
						this.blocks.add(faceWith);
			}
		});
		return getAffiliation(result);
	}

}
