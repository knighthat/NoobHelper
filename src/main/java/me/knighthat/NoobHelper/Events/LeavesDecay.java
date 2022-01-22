package me.knighthat.NoobHelper.Events;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.knighthat.NoobHelper.NoobHelper;

public class LeavesDecay extends EventStorage
{

	public LeavesDecay(NoobHelper plugin) {
		super(plugin);
	}

	public void decomposition( LeavesDecayEvent e ) {

		long dSpeed = confLong("fast_leaf_decay.speed");

		new BukkitRunnable() {

			List<Block> leaves = getAffiliation(e.getBlock());
			int count;

			@Override
			public void run() {

				if ( count < leaves.size() ) {

					decay(leaves.get(count));
					count++;

				} else {
					cancel();
				}
			}
		}.runTaskTimer(plugin, 0L, dSpeed >= 0 ? 1 + dSpeed : 1);
	}

	private void decay( Block leaf ) {
		if ( !(leaf.getBlockData() instanceof Leaves) )
			return;

		Leaves l = (Leaves) leaf.getBlockData();
		if ( !(l.isPersistent() & l.getDistance() > 2) ) {
			leaf.breakNaturally();
		}
	}

}
