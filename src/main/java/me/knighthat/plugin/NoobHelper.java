package me.knighthat.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import me.knighthat.plugin.Commands.GUIs;
import me.knighthat.plugin.Commands.Reload;
import me.knighthat.plugin.Events.Listener;
import me.knighthat.plugin.Files.BlockData;
import me.knighthat.plugin.Files.Config;

public class NoobHelper extends JavaPlugin
{

	public Config config;
	public BlockData blockdata;

	boolean isAfter14 = false;
	boolean isAfter16 = false;

	@Override
	public void onEnable() {

		getVersion();

		registerFiles();

		registerCommands();

		getServer().getPluginManager().registerEvents(new Listener(this), this);
	}

	private void registerCommands() {
		getCommand("noobhelper").setExecutor(new Reload(this));

		getCommand("workbench").setExecutor(new GUIs(this, "workbench"));
		getCommand("anvil").setExecutor(new GUIs(this, "anvil"));
		if ( !isAfter14 )
			return;
		getCommand("cartography").setExecutor(new GUIs(this, "cartography"));
		getCommand("loom").setExecutor(new GUIs(this, "loom"));
		getCommand("stonecutter").setExecutor(new GUIs(this, "stonecutter"));
		getCommand("grindstone").setExecutor(new GUIs(this, "grindstone"));
		if ( isAfter16 )
			getCommand("smithing").setExecutor(new GUIs(this, "smithing"));

	}

	private void registerFiles() {
		config = new Config(this);
		blockdata = new BlockData(this);
	}

	private void getVersion() {

		String version = getServer().getVersion();
		int index = version.lastIndexOf(".");
		version = version.substring(index - 2, index);

		if ( Integer.parseInt(version) >= 14 )
			isAfter14 = true;

		if ( Integer.parseInt(version) >= 16 )
			isAfter16 = true;
	}

}
