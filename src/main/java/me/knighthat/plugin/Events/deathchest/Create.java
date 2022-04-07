package me.knighthat.plugin.Events.deathchest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.files.Config;
import me.knighthat.plugin.utils.PathGenerator;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class Create implements PathGenerator
{

	NoobHelper plugin;
	Config config;

	Player player;
	Location location;

	Map<Integer, ItemStack> contents = new HashMap<>();

	public Create(NoobHelper plugin, Player player) {

		this.plugin = plugin;
		this.config = plugin.config;
		this.player = player;
		this.location = player.getLocation();

		PlayerInventory i = player.getInventory();
		for ( int slot = 0 ; slot < player.getInventory().getSize() ; slot++ )
			if ( i.getItem(slot) != null )
				contents.put(slot, i.getItem(slot));

		if ( contents.keySet().isEmpty() & getPlayerExp(player) == 0 ) { return; }

		player.getLocation().getBlock().setType(Material.CHEST);

		registerContents();

		player.sendMessage(deathMessage());
		player.spigot().sendMessage(contentMessage());

	}

	@Override
	public Player getPlayer() { return this.player; }

	void set( String path, Object value ) { plugin.deathChests.get().set(getFullPath(location) + "." + path, value); }

	void registerContents() {

		set("X", location.getBlockX());
		set("Y", location.getBlockY());
		set("Z", location.getBlockZ());
		set("Exp", getPlayerExp(player));
		for ( int slot : contents.keySet() ) { set("items." + slot, contents.get(slot)); }
		plugin.deathChests.save();

	}

	String deathMessage() {

		String x = String.valueOf(location.getBlockX());
		String y = String.valueOf(location.getBlockY());
		String z = String.valueOf(location.getBlockZ());

		String msg = config.getString("death_chest.death_message", true, player, new String[] { x, y, z });

		return msg;
	}

	TextComponent contentMessage() {

		final String input = config.getString("death_chest.content_message", true, player, null);
		int index = input.indexOf("%") + 1;

		String front = input.substring(0, index - 1);
		String button = input.substring(index, input.indexOf("%", index));
		String end = input.substring(input.indexOf("%", index) + 1, input.length());

		TextComponent beforeButton = applyEvents(front, null, null);
		TextComponent atButton = createButton(button);
		TextComponent afterButton = applyEvents(end, null, null);

		beforeButton.addExtra(atButton);
		beforeButton.addExtra(afterButton);

		return beforeButton;
	}

	TextComponent createButton( String input ) {

		String itemList = new String();

		Iterator<ItemStack> items = contents.values().iterator();
		while ( items.hasNext() ) {

			ItemStack item = items.next();
			String name = ChatColor.WHITE + item.getType().name();

			if ( item.getItemMeta().hasDisplayName() )
				name = item.getItemMeta().getDisplayName();

			itemList += name + (items.hasNext() ? "\n" : "");
		}

		HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(itemList));
		ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/noobhelper lostitems " + getId(location));

		return applyEvents(input, hoverEvent, clickEvent);
	}

	TextComponent applyEvents( String input, HoverEvent hover, ClickEvent click ) {

		TextComponent result = new TextComponent(input);

		result.setHoverEvent(hover);
		result.setClickEvent(click);

		return result;
	}

	/*
	 * BIG shout out to DOGC_Kyle for doing the research
	 * https://www.spigotmc.org/threads/how-to-get-players-exp-points.239171/
	 */

	public int getPlayerExp( Player player ) {

		int level = player.getLevel(), expToLvlUp = 0, expAtLvl = 0;

		if ( level <= 15 ) {
			expToLvlUp = 2 * level + 7;
		} else if ( level <= 30 ) {
			expToLvlUp = 5 * level - 38;
		} else
			expToLvlUp = 9 * level - 158;

		if ( level <= 16 ) {
			expAtLvl = (int) (Math.pow(level, 2) + 6 * level);
		} else if ( level <= 31 ) {
			expAtLvl = (int) (2.5 * Math.pow(level, 2) - 40.5 * level + 360.0);
		} else
			expAtLvl = (int) (4.5 * Math.pow(level, 2) - 162.5 * level + 2220.0);

		expToLvlUp *= player.getExp();

		return expAtLvl + expToLvlUp;
	}
}
