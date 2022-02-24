package me.knighthat.plugin.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class ContainerAbstract implements InventoryHolder
{

	Inventory inventory;

	Player player;

	@Override
	public Inventory getInventory() { return inventory; }

	public abstract String getTitle();

	public abstract void setContent();

	public ContainerAbstract(Player player) {
		this.player = player;
		inventory = Bukkit.createInventory(this, 54, getTitle());
		setContent();
	}

	public void openInventory() { player.openInventory(inventory); }
}
