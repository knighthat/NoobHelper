package me.knighthat.NoobHelper.Files;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.knighthat.NoobHelper.Console;
import me.knighthat.NoobHelper.NoobHelper;

public class Config
{

	private NoobHelper plugin;

	public Config(NoobHelper plugin) {
		this.plugin = plugin;
		startup();
	}

	private File file;
	private FileConfiguration fileConfiguration;
	private String fileName = "config.yml";

	public void startup() {
		file = new File(plugin.getDataFolder(), fileName);
		if ( !file.exists() ) {
			plugin.saveResource(fileName, false);
		}
		reload();
	}

	public FileConfiguration get() {
		if ( fileConfiguration == null ) {
			reload();
		}
		return fileConfiguration;
	}

	public void reload() {
		if ( file == null ) {
			startup();
		}
		fileConfiguration = YamlConfiguration.loadConfiguration(file);
	}

	public void save() {
		try {
			fileConfiguration.save(file);
		} catch ( IOException e ) {
			Console.printErr("Couldn't save " + fileName + "!");
			e.printStackTrace();
		}
	}
}
