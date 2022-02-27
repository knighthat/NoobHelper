package me.knighthat.plugin.Events.TrashBin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.Events.Listener;
import me.knighthat.plugin.utils.MenuBase;

public class ConfirmMenu extends MenuBase
{

	final String path = "trash_bin.confirmation_menu.";
	Material border;
	Material decline;
	Material accept;
	Material represent;

	public ConfirmMenu(NoobHelper plugin, Player player, List<ItemStack> contents) {
		super(plugin, player);
		Listener.itemsForReturn.put(player, contents);
		border = Material.valueOf(config.getString(path.concat("border_material")));
		decline = Material.valueOf(config.getString(path.concat("decline_material")));
		accept = Material.valueOf(config.getString(path.concat("accept_material")));
		represent = Material.valueOf(config.getString(path.concat("represent_item")));
		setContent();
	}

	@Override
	public InventoryType getType() { return InventoryType.CHEST; }

	@Override
	public String getTitle() { return config.getString(path.concat("title")); }

	@Override
	public void setContent() {

		for ( int row = 0 ; row < 3 ; row++ )
			if ( row == 1 ) {
				for ( int collumn = 0 ; collumn < 9 ; collumn++ ) {

					int index = row * 9 + collumn;

					Material itemMaterial = represent;
					String itemName = config.getString(path.concat("represent_name"));
					List<String> lore = new ArrayList<>();
					String data = "";

					if ( collumn == 4 )
						for ( ItemStack item : Listener.itemsForReturn.get(player) )
							if ( item.getItemMeta().hasDisplayName() ) {
								lore.add(item.getItemMeta().getDisplayName());
							} else
								lore.add(item.getType().name());

					if ( collumn > 4 ) {
						itemMaterial = accept;
						itemName = config.getString(path.concat("accept_name"));
						data = "accept";
					}
					if ( collumn < 4 ) {
						itemMaterial = decline;
						itemName = config.getString(path.concat("decline_name"));
						data = "decline";
					}

					ItemStack item = applyDescr(itemMaterial, itemName, lore, data);

					getInventory().setItem(index, new ItemStack(item));
				}
			} else
				for ( int collumn = 0 ; collumn < 9 ; collumn++ ) {
					ItemStack borderItem = applyDescr(border, " ", new ArrayList<>(), "");
					getInventory().setItem(row * 9 + collumn, borderItem);
				}
	}

//	ItemStack applyDescr( Material material, String name, List<String> lore ) {
//		ItemStack item = new ItemStack(material);
//		ItemMeta iMeta = item.getItemMeta();
//		iMeta.setDisplayName(name);
//		iMeta.setLore(lore);
//		item.setItemMeta(iMeta);
//		return item;
//	}

	ItemStack applyDescr( Material material, String name, List<String> lore, String data ) {
		ItemStack item = new ItemStack(material);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName(name);
		iMeta.setLore(lore);
		iMeta.getPersistentDataContainer().set(plugin.NamespacedKey, plugin.dataType, data);
		item.setItemMeta(iMeta);
		return item;
	}
}
