package me.knighthat.plugin.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.knighthat.plugin.NoobHelper;

public class TrashBinMenu extends MenuAbstract
{

	String path;

	public TrashBinMenu(NoobHelper plugin, Player viewer, String path) {

		super(plugin, viewer);
		this.path = path;

		setSlot(config.get().getInt(path.concat(".slots")));
	}

	@Override
	public void setContents() {}

	@Override
	public void setBorder() {}

	@Override
	public String getTitle() { return config.getString(path.concat("title")); }

	@Override
	public void onClose( InventoryCloseEvent event ) {
		new BukkitRunnable() {

			@Override
			public void run() {
				for ( int slot = 0 ; slot < getInventory().getContents().length ; slot++ )
					if ( getInventory().getItem(slot) != null ) {
						new ConfirmMenu(plugin, viewer, getInventory().getContents()).open();
						break;
					}
			}
		}.runTaskLater(plugin, 2L);
	}
}
