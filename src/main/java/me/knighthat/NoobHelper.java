package me.knighthat;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import me.knighthat.plugin.Commands.Manager;
import me.knighthat.plugin.Events.Listener;
import me.knighthat.plugin.Files.Config;
import me.knighthat.plugin.Files.DeathChests;
import me.knighthat.plugin.Files.TrashBins;

public class NoobHelper extends JavaPlugin
{

	public Config config = new Config(this);
	public TrashBins trashBins = new TrashBins(this);
	public DeathChests deathChests = new DeathChests(this);

	@Override
	public void onEnable() {

		getCommand("noobhelper").setExecutor(new Manager(this));

		getServer().getPluginManager().registerEvents(new Listener(this), this);

		checkFiles();
	}

	void checkFiles() {

		File oldFile = new File(getDataFolder(), "blockdata.yml");

		if ( !oldFile.exists() )
			return;

		trashBins.copyContent(oldFile);
		trashBins.reload();
		deathChests.copyContent(oldFile);
		deathChests.reload();

		oldFile.delete();
	}

	public final boolean checkVersion( double versionToCompare ) {

		String version = getServer().getVersion();
		int pointer = version.lastIndexOf(".");
		version = version.substring(pointer - 2, pointer + 1);

		return Double.parseDouble(version) >= versionToCompare;
	}
}
