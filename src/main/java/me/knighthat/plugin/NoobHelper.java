package me.knighthat.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import me.knighthat.plugin.Commands.Reload;
import me.knighthat.plugin.Events.Listener;
import me.knighthat.plugin.Files.BlockDataFile;
import me.knighthat.plugin.Files.ConfigFile;

public class NoobHelper extends JavaPlugin
{

	public ConfigFile config = new ConfigFile(this);
	public BlockDataFile blockdata = new BlockDataFile(this);

	@Override
	public void onEnable() {

		getCommand("noobhelper").setExecutor(new Reload(this));

		getServer().getPluginManager().registerEvents(new Listener(this), this);
	}
}
