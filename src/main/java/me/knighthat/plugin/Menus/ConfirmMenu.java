package me.knighthat.plugin.Menus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.knighthat.plugin.NoobHelper;

public class ConfirmMenu extends MenuAbstract
{

	final String PATH = "trash_bin.confirmation_menu.";

	ItemStack[] contents;

	public ConfirmMenu(NoobHelper plugin, Player player, ItemStack[] contents) {

		super(plugin, player);

		this.contents = contents;

		setSlot(27);
	}

	String get( String path ) { return config.getString(PATH.concat(path)); }

	@Override
	public String getTitle() { return get("title"); }

	@Override
	public void setBorder() {

		for ( int row = 0 ; row < slots / 9 ; row += 2 )
			for ( int collumn = 0 ; collumn < 9 ; collumn++ ) {

				Material border = Material.valueOf(get("border_material"));
				ItemStack borderItem = applyDescr(border, " ", new ArrayList<>(), "");
				getInventory().setItem(row * 9 + collumn, borderItem);
			}
	}

	@Override
	public void setContents() {

		for ( int collumn = 9 ; collumn < 18 ; collumn++ ) {

			String data = collumn < 13 ? "accept" : collumn > 13 ? "decline" : "represent";

			var lore = new ArrayList<String>();
			if ( collumn == 13 )
				for ( ItemStack item : contents )
					if ( item != null )
						if ( item.getItemMeta().hasDisplayName() ) {

							lore.add(item.getItemMeta().getDisplayName());

						} else
							lore.add(item.getType().name());

			var material = Material.getMaterial(get(data.concat("_material")).toUpperCase());
			var name = get(data.concat("_name"));

			getInventory().setItem(collumn, applyDescr(material, name, lore, data));

		}
	}

	ItemStack applyDescr( Material material, String name, List<String> lore, String data ) {
		ItemStack item = new ItemStack(material);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName(name);
		iMeta.setLore(lore);
		iMeta.getPersistentDataContainer().set(plugin.NamespacedKey, plugin.dataType, data);
		item.setItemMeta(iMeta);
		return item;
	}

	@Override
	public boolean onClick( InventoryClickEvent event ) {

		switch ( event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(plugin.NamespacedKey, plugin.dataType) ) {

			case "accept" :

				contents = null;
				viewer.sendMessage(plugin.config.getString("trash_bin.delete_message", true, null, null));

			case "decline" :

				returnItems();

				viewer.closeInventory();

			default :
				return true;
		}
	}

	@Override
	public void onClose( InventoryCloseEvent event ) { returnItems(); }

	void returnItems() {

		if ( contents == null )
			return;

		for ( ItemStack item : contents )
			if ( item != null )
				viewer.getInventory().addItem(item);

		contents = null;
	}
}
