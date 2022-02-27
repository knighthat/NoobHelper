package me.knighthat.plugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.Files.Config;
import me.knighthat.plugin.Files.DeathChests;
import me.knighthat.plugin.Files.TrashBins;

public abstract class MenuBase implements InventoryHolder
{
	protected NoobHelper plugin;
	protected Config config;
	protected DeathChests deathChests;
	protected TrashBins trashBins;

	protected Inventory inventory;
	protected String title;

	protected Player player;

	@Override
	public Inventory getInventory() { return this.inventory; }

	public MenuBase(NoobHelper plugin, Player player) {
		this.plugin = plugin;
		this.config = plugin.config;
		this.deathChests = plugin.deathChests;
		this.trashBins = plugin.trashBins;
		this.player = player;
		inventory = Bukkit.createInventory(this, getType(), getTitle());
	}

	public abstract String getTitle();

	public abstract InventoryType getType();

	public abstract void setContent();
}
