package me.knighthat.plugin.Events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.Files.Config;

public class FastLeafDecay extends Storage
{

	Config config;

	public FastLeafDecay(NoobHelper plugin, LeavesDecayEvent e) {

		this.config = plugin.config;
		super.starter = e.getBlock();
		super.blocks.add(starter);
		List<Block> leaves = super.getAffiliation(new ArrayList<>());

		new BukkitRunnable() {

			int count;

			@Override
			public void run() {

				if ( count < leaves.size() ) {

					Block leaf = leaves.get(count);

					if ( config.get().getBoolean("fast_leaf_decay.add_effect") ) { playEffect(leaf); }

					decomposition(leaf);

					count++;
				} else
					cancel();

			}

		}.runTaskTimer(plugin, 1L, config.get().getInt("fast_leaf_decay.slowness"));

	}

	private void decomposition( Block leaf ) {
		if ( !(leaf.getBlockData() instanceof Leaves) ) { return; }

		Leaves l = (Leaves) leaf.getBlockData();
		if ( !(l.isPersistent() & l.getDistance() > 2) ) { leaf.breakNaturally(); }
	}

	private void playEffect( Block block ) {
		Location loc = block.getLocation().add(.5, .5, .5);

		loc.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 30, .1, .1, .1, block.getBlockData());
		loc.getWorld().playSound(loc, Sound.BLOCK_GRASS_BREAK, 29, 1);

	}

	@Override
	public int getMaxBlock() { return 999999999; }
}
