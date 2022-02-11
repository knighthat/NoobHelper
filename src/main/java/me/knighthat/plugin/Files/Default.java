package me.knighthat.plugin.Files;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.knighthat.plugin.NoobHelper;

public abstract class Default
{

	NoobHelper plugin;

	String fileName;
	File file;
	FileConfiguration config;

	public Default(NoobHelper plugin) {
		this.plugin = plugin;
	}

	void setFile( String fileName ) {
		this.fileName = fileName;
	}

	void startup() {
		file = new File(plugin.getDataFolder(), fileName);
		checkFile();
		reload();
	}

	public FileConfiguration get() {
		if ( file == null )
			startup();
		return config;
	}

	public void save() {
		try {
			config.save(file);
			reload();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	public void reload() {
		if ( file == null )
			startup();
		checkFile();
		config = YamlConfiguration.loadConfiguration(file);
	}

	void checkFile() {
		if ( !file.exists() )
			plugin.saveResource(fileName, false);
	}

}
