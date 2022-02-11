package me.knighthat.plugin;

import java.util.ArrayList;
import java.util.List;

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

	public List<String> cmds = new ArrayList<>();

	boolean isAfter14 = false;
	boolean isAfter16 = false;

	@Override
	public void onEnable() {

		getVersion();

		registerFiles();

		registerCommands();
		registerCommandAliases();

		getServer().getPluginManager().registerEvents(new Listener(this), this);
	}

	void registerCommandAliases() {

		for ( String cmd : cmds )
			registerCommandAliasses(cmd);
	}

	void registerCommandAliasses( String command ) {

		List<String> aliases = config.getStringList("command_shortcuts.aliases." + command);

		getCommand(command).setAliases(aliases);
	}

	void registerCommands() {
		getCommand("noobhelper").setExecutor(new Reload(this));

		registerCommands("workbench");
		registerCommands("anvil");
		if ( !isAfter14 )
			return;
		registerCommands("cartography");
		registerCommands("loom");
		registerCommands("stonecutter");
		registerCommands("grindstone");
		if ( isAfter16 )
			registerCommands("smithing");

	}

	void registerCommands( String command ) {
		getCommand(command).setExecutor(new GUIs(this, command));
		cmds.add(command);
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
