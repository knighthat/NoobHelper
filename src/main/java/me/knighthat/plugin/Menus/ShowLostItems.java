package me.knighthat.plugin.Menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.knighthat.plugin.NoobHelper;

public class ShowLostItems extends MenuAbstract
{
	String path;

	public ShowLostItems(NoobHelper plugin, Player viewer, String path) {
		super(plugin, viewer);
		viewer.sendMessage(path);
		this.path = path;
	}

	@Override
	public String getTitle() {

		String title = config.getString("death_chest.container_title");
		String x = deathChests.getString(path.concat(".X"));
		String y = deathChests.getString(path.concat(".Y"));
		String z = deathChests.getString(path.concat(".Z"));

		title = title.replace("%x%", x).replace("%y%", y).replace("%z%", z);
		title = title.replace("%player%", viewer.getName()).replace("%display_name%", viewer.getDisplayName());

		return title;
	}

	@Override
	public void setBorder() {

		ItemStack exp = new ItemStack(Material.EXPERIENCE_BOTTLE);
		ItemMeta expMeta = exp.getItemMeta();

		String expName = config.getString("death_chest.exp_bottle_name");
		expName = expName.replace("%exp%", deathChests.getString(path.concat(".Exp")));
		expMeta.setDisplayName(expName);

		exp.setItemMeta(expMeta);

		getInventory().setItem(49, exp);
	}

	@Override
	public void setContents() {

		if ( deathChests.get().contains(path.concat(".items")) )
			for ( String stringSlot : deathChests.get().getConfigurationSection(path.concat(".items")).getKeys(false) ) {

				int slot = Integer.parseInt(stringSlot);

				slot += slot >= 36 ? 2 : 0;

				ItemStack item = deathChests.get().getItemStack(path.concat(".items." + stringSlot));

				getInventory().setItem(slot, item);
			}
	}

	@Override
	public boolean onClick( InventoryClickEvent event ) { return true; }
}
