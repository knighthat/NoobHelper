package me.knighthat.NoobHelper.Events;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.EquipmentSlot;

import me.knighthat.NoobHelper.NoobHelper;

public class EventListener extends EventStorage implements Listener
{

	private TrashBin trashBin;
	private LeavesDecay leafsDecay;
	private BlockBreakAssistant bAssistant;

	public EventListener(NoobHelper plugin) {
		super(plugin);
		trashBin = new TrashBin(plugin);
		leafsDecay = new LeavesDecay(plugin);
		bAssistant = new BlockBreakAssistant(plugin);
	}

	@EventHandler
	public void onInteract( PlayerInteractEvent e ) {

		if ( !(e.getHand().equals(EquipmentSlot.HAND)
				& e.getAction().equals(org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) )
			return;

		final Player player = e.getPlayer();
		final Block block = e.getClickedBlock();

		if ( confBoolean("smart_harvest") & block.getBlockData() instanceof Ageable ) {
			new SmartHarvest(block);
		}
		if ( confBoolean("trash_bin.enabled") & block.getState() instanceof Sign ) {
			trashBin.enterTrashBin(player, block);
		}
	}

	@EventHandler
	public void onSignChange( SignChangeEvent e ) {
		String[] lines = e.getLines();
		if ( confBoolean("trash_bin.enabled") & lines[0].equalsIgnoreCase("[Trash Bin]") ) {
			lines = trashBin.placeTrashBin(e);
		}

		for ( int i = 0 ; i < lines.length ; i++ ) {
			e.setLine(i, addColor(lines[i]));
		}
	}

	@EventHandler
	public void onToolBreaks( PlayerItemBreakEvent e ) {
		if ( confBoolean("replace_broken_tool") ) {
			new ToolReplacement(e.getPlayer().getInventory(), e.getBrokenItem());
		}
	}

	@EventHandler
	public void onBlockBreak( BlockBreakEvent e ) {
		final Player player = e.getPlayer();
		final Block block = e.getBlock();

		if ( block.getState() instanceof Sign ) {
			trashBin.removeTrashBin(e.getBlock().getLocation());
		}

		if ( player.getInventory().getItemInMainHand() == null )
			return;

		String holding = player.getInventory().getItemInMainHand().getType().toString();
		String[] tools = new String[] { "AXE", "PICKAXE", "SHOVEL" };
		for ( String tool : tools )
			if ( holding.endsWith(tool) && confBoolean("block_break_assist.enabled") ) {
				bAssistant.assist(player, block);
			}
	}

	@EventHandler
	public void onLeafDecay( LeavesDecayEvent e ) {
		if ( confBoolean("fast_leaf_decay.enabled") ) {
			leafsDecay.decomposition(e);
		}
	}

}
