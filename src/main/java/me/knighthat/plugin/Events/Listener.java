package me.knighthat.plugin.Events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.block.data.type.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;

import me.knighthat.plugin.Misc;
import me.knighthat.plugin.NoobHelper;

public class Listener implements org.bukkit.event.Listener
{

	NoobHelper plugin;

	private List<Location> decayLocation = new ArrayList<>();

	public Listener(NoobHelper plugin) {
		this.plugin = plugin;
	}

	private boolean isEnabled( String path ) {
		return plugin.config.getBoolean(path);
	}

	boolean checkPerm( Player player, String permission ) {
		return Misc.checkPermission(player, plugin.config, permission);
	}

	@EventHandler
	public void onToolBreak( PlayerItemBreakEvent event ) {

		Player player = event.getPlayer();

		if ( isEnabled("equipment_replacement") & checkPerm(player, ".eqiuipment_replacement") )
			new ToolReplacement(event.getPlayer(), event.getBrokenItem());
	}

	@EventHandler
	public void onInteract( PlayerInteractEvent event ) {

		if ( !EquipmentSlot.HAND.equals(event.getHand()) | !Action.RIGHT_CLICK_BLOCK.equals(event.getAction()) )
			return;

		BlockData blockData = event.getClickedBlock().getBlockData();

		if ( isEnabled("smart_harvesting.enabled") & blockData instanceof Ageable )
			if ( checkPerm(event.getPlayer(), "smart_harvesting") ) {
				new SmartHarvesting(event, isEnabled("smart_harvesting.sound"));
				return;
			}

		if ( plugin.config.getBoolean("trash_bin.enabled") & blockData instanceof Sign ) {
			new TrashBin(plugin, event.getPlayer(), event.getClickedBlock().getLocation());
			return;
		}

	}

	@EventHandler
	public void playerBreakBlock( BlockBreakEvent event ) {

		BlockData blockData = event.getBlock().getBlockData();

		if ( blockData instanceof Leaves ) {
			Bukkit.getServer().getPluginManager().callEvent(new LeavesDecayEvent(event.getBlock()));
			return;
		}

		if ( blockData instanceof Sign ) {
			new TrashBin(plugin, event);
			return;
		}

		if ( isEnabled("break_assistant.enabled") )
			new BreakAssistant(plugin.config, event);

	}

	@EventHandler
	public void onLeavesDecay( LeavesDecayEvent event ) {

		if ( isEnabled("fast_leaf_decay.enabled") ) {

			if ( isEnabled("fast_leaf_decay.play_effect") ) {

				final Location eLoc = event.getBlock().getLocation();

				for ( Location loc : decayLocation )
					if ( eLoc.distance(loc) <= 5D ) {
						event.setCancelled(true);
						return;
					}

				decayLocation.add(eLoc);

				new BukkitRunnable() {

					@Override
					public void run() {
						decayLocation.remove(eLoc);
					}
				}.runTaskLaterAsynchronously(plugin, 20L * 60L);

			}

			new FastLeafDecay(plugin, event);
		}
	}

	@EventHandler
	public void playerPlaceTrashBin( SignChangeEvent event ) {

		if ( isEnabled("trash_bin.enabled") )
			new TrashBin(plugin, event);

		for ( int line = 0 ; line < event.getLines().length ; line++ )
			event.setLine(line, Misc.addColor(event.getLine(line)));
	}

}
