package me.knighthat.plugin.Menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.Files.Config;
import me.knighthat.plugin.Files.DeathChests;
import me.knighthat.plugin.Files.TrashBins;

public abstract class MenuAbstract implements InventoryHolder, Listener
{
	protected NoobHelper plugin;
	protected Config config;
	protected DeathChests deathChests;
	protected TrashBins trashBins;

	protected Player viewer;

	Inventory inventory;
	protected int slots = 54;

	public MenuAbstract(NoobHelper plugin, Player viewer) {

		this.plugin = plugin;
		this.config = plugin.config;
		this.deathChests = plugin.deathChests;
		this.trashBins = plugin.trashBins;
		this.viewer = viewer;

		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	public abstract String getTitle();

	public abstract void setBorder();

	public abstract void setContents();

	@Override
	public Inventory getInventory() { return this.inventory; }

	public boolean onClick( InventoryClickEvent event ) { return false; }

	public void onClose( InventoryCloseEvent event ) {}

	public void setSlot( int slots ) { this.slots = slots; }

	@EventHandler
	public void inventoryClose( InventoryCloseEvent event ) {

		if ( event.getInventory() == null )
			return;

		if ( !(event.getInventory().getHolder() instanceof MenuAbstract) )
			return;

		if ( event.getInventory().equals(this.inventory) )
			onClose(event);
	}

	@EventHandler
	public void inventoryClick( InventoryClickEvent event ) {

		if ( event.getCurrentItem() == null | event.getClickedInventory() == null )
			return;

		Inventory clickedInv = event.getView().getTopInventory();

		if ( !(clickedInv.getHolder() instanceof MenuAbstract) )
			return;

		if ( clickedInv.equals(this.inventory) )
			event.setCancelled(onClick(event));
	}

	public void open() {

		inventory = Bukkit.createInventory(this, this.slots, getTitle());

		setBorder();

		setContents();

		viewer.openInventory(getInventory());
		viewer.updateInventory();
	}
}
