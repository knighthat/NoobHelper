package me.knighthat.plugin.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public abstract class GetRelatives
{

	Block starter;
	int max = 0;
	List<Block> surroundedBy = new ArrayList<>();

	public GetRelatives(Block starter, int max) {

		this.starter = starter;
		this.max = max;
		surroundedBy.add(starter);
	}

	public GetRelatives(Block starter) { this(starter, 200); }

	public List<Block> getSurrounding( List<Block> oldList ) {

		if ( this.surroundedBy.size() <= oldList.size() ) { return this.surroundedBy; }

		List<Block> result = new ArrayList<>(this.surroundedBy);
		result.stream().filter(block -> !oldList.contains(block)).forEach(block -> {

			for ( BlockFace blockFace : BlockFace.values() ) {

				Block faceWith = block.getRelative(blockFace);

				if ( this.surroundedBy.size() < max )
					if ( faceWith.getType().equals(starter.getType()) & !this.surroundedBy.contains(faceWith) )
						this.surroundedBy.add(faceWith);
			}
		});
		return getSurrounding(result);
	}
}
