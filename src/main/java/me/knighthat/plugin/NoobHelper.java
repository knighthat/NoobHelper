package me.knighthat.plugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import me.knighthat.plugin.Commands.Reload;
import me.knighthat.plugin.Events.Listener;
import me.knighthat.plugin.Files.BlockData;
import me.knighthat.plugin.Files.Config;

public class NoobHelper extends JavaPlugin
{

	public Config config;
	public BlockData blockdata;

	public List<String> cmds = new ArrayList<>();

	boolean isAfter14 = false;
	boolean isAfter16 = false;

	@Override
	public void onEnable() {

		getVersion();

		registerFiles();

		registerCommands();

		getServer().getPluginManager().registerEvents(new Listener(this), this);
	}

	void registerCommands() {
		getCommand("noobhelper").setExecutor(new Reload(this));
	}

	void registerFiles() {
		config = new Config(this);
		blockdata = new BlockData(this);
	}

	void getVersion() {

		String version = getServer().getVersion();
		int index = version.lastIndexOf(".");
		version = version.substring(index - 2, index);

		if ( Integer.parseInt(version) >= 14 )
			isAfter14 = true;

		if ( Integer.parseInt(version) >= 16 )
			isAfter16 = true;
	}

}
