package me.knighthat.plugin.Events;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.knighthat.plugin.NoobHelper;

public class FastLeafDecay implements Storage
{

	public FastLeafDecay(NoobHelper plugin, LeavesDecayEvent e) {

		long period = plugin.config.getInt("fast_leaf_decay.slowness");

		new BukkitRunnable() {

			List<Block> leaves = getAffiliation(e.getBlock(), 0);
			int count;

			@Override
			public void run() {

				if ( count < leaves.size() ) {

					Block leaf = leaves.get(count);

					if ( plugin.config.getBoolean("fast_leaf_decay.play_effect") )
						playEffect(leaf);

					decomposition(leaf);

					count++;
				} else
					cancel();

			}

		}.runTaskTimer(plugin, 1L, (period >= 1 ? period : 1L));

	}

	private void decomposition( Block leaf ) {
		if ( !(leaf.getBlockData() instanceof Leaves) )
			return;

		Leaves l = (Leaves) leaf.getBlockData();
		if ( !(l.isPersistent() & l.getDistance() > 2) )
			leaf.breakNaturally();
	}

	private void playEffect( Block block ) {
		Location loc = block.getLocation().add(.5, .5, .5);

		loc.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 30, .1, .1, .1, block.getBlockData());
		loc.getWorld().playSound(loc, Sound.BLOCK_GRASS_BREAK, 29, 1);

	}

}
