package me.knighthat.plugin.Events.DeathChest;

import java.util.Iterator;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.knighthat.plugin.NoobHelper;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

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

		ComponentBuilder builder = new ComponentBuilder(input.substring(0, $ - 1));
		builder.append(createButton(input.substring($, input.indexOf("%", $))));
		builder.append(createLegacyString(input.substring(input.indexOf("%", $) + 1, input.length())));

		player.sendMessage(builder.create());
	}

	TextComponent createButton( String input ) {

		String lostItems = new String();
		Iterator<ItemStack> items = contents.values().iterator();
		while ( items.hasNext() ) {

			ItemStack item = items.next();
			String itemName = item.getType().name();

			if ( item.getItemMeta().hasDisplayName() )
				itemName = item.getItemMeta().getDisplayName();

			lostItems = lostItems.concat(itemName + (items.hasNext() ? "\n" : ""));
		}
		ComponentBuilder hoverBuilder = new ComponentBuilder(lostItems);
		HoverEvent hoverEvent = new HoverEvent(Action.SHOW_TEXT, hoverBuilder.create());

		String path = playerName + "_" + uuid + "." + player.getWorld().getName();
		final String cmd = "/noobhelper lostitems " + path.concat("." + generateID());
		ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd);

		TextComponent result = new TextComponent(input);
		result.setHoverEvent(hoverEvent);
		result.setClickEvent(clickEvent);

		return result;
	}

	TextComponent createLegacyString( String input ) {

		TextComponent result = new TextComponent(input);
		result.setHoverEvent(new HoverEvent(Action.SHOW_ENTITY, null));
		result.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, null));

		return result;
	}
}
