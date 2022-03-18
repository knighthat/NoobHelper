package me.knighthat.plugin.Events.TrashBin;

import java.util.StringJoiner;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.Files.TrashBins;
import me.knighthat.plugin.utils.PathGenerator;
import me.knighthat.plugin.utils.PermissionChecker;

public class TrashBinStorage implements PermissionChecker, PathGenerator
{
	protected NoobHelper plugin;
	protected TrashBins trashBins;

	protected Player player;
	protected Location location;

	protected final String PATH = "trash_bin.";

	public TrashBinStorage(NoobHelper plugin, Player player, Block block) {

		this.plugin = plugin;
		this.trashBins = plugin.trashBins;
		this.player = player;
		this.location = block.getLocation();
	}

	@Override
	public Player getPlayer() { return this.player; }

	protected boolean checkPermission( String permission ) { return checkPermission(player, plugin.config, permission, true); }

	public String getId() {

		StringJoiner joiner = new StringJoiner(".");
		joiner = joiner.add(getWorldName(location)).add(getId(location));

		return joiner.toString();
	}

}
