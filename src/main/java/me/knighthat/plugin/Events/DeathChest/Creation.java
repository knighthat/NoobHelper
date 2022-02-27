package me.knighthat.plugin.Events.DeathChest;

import java.util.Iterator;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.knighthat.plugin.NoobHelper;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class Creation extends Storage
{

	public Creation(NoobHelper plugin, Player player) {

		initialization(plugin, player, null);

		if ( !playerContents() ) { return; }

		location.getBlock().setType(Material.CHEST);

		registerContents();

		sendDeathLocation();
		sendContents();
	}

	void sendDeathLocation() {

		int x = location.getBlockX(), y = location.getBlockY(), z = location.getBlockZ();
		String X = String.valueOf(x), Y = String.valueOf(y), Z = String.valueOf(z);

		String msg = config.getString(path + "death_message", true);
		msg = msg.replace("%x%", X).replace("%y%", Y).replace("%z%", Z);

		player.sendMessage(msg);
	}

	void sendContents() {

		final String input = config.getString("death_chest.content_message", true);
		int $ = input.indexOf("%") + 1;

		String front = input.substring(0, $ - 1);
		String button = input.substring($, input.indexOf("%", $));
		String end = input.substring(input.indexOf("%", $) + 1, input.length());

		TextComponent frontComp = toComp(front, null, null);
		TextComponent buttonComp = createButton(button);
		TextComponent endComp = toComp(end, null, null);

		ComponentBuilder builder = new ComponentBuilder(frontComp);

		player.sendMessage(builder.append(buttonComp).append(endComp).create());
	}

	TextComponent createButton( String input ) {

		String lostItems = new String();
		Iterator<ItemStack> items = contents.values().iterator();
		while ( items.hasNext() ) {

			ItemStack item = items.next();
			String itemName = item.getType().name();

			if ( item.getItemMeta().hasDisplayName() )
				itemName = item.getItemMeta().getDisplayName();

			if ( items.hasNext() )
				itemName += "\n";

			lostItems = lostItems.concat(itemName);
		}

		HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(lostItems));

		final String cmd = "/noobhelper lostitems " + generateID();
		ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd);

		return toComp(input, hoverEvent, clickEvent);
	}

	TextComponent toComp( String input, HoverEvent hoverEvent, ClickEvent clickEvent ) {

		TextComponent result = new TextComponent(input);

		if ( hoverEvent != null )
			result.setHoverEvent(hoverEvent);

		if ( clickEvent != null )
			result.setClickEvent(clickEvent);

		return result;
	}

}
