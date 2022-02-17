package me.knighthat.plugin.Events.DeathChest;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.knighthat.plugin.NoobHelper;

public class Creation extends Storage
{

	public Creation(NoobHelper plugin, Player player) {

		initialization(plugin, player, null);

		if ( !playerContents() ) { return; }

		location.getBlock().setType(Material.CHEST);

		registerContents();

		sendMessage();
	}

}
