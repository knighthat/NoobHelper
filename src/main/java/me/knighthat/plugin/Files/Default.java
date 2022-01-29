package me.knighthat.plugin.Files;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.knighthat.plugin.NoobHelper;

public abstract class Default
{

	NoobHelper plugin;

	private String fileName;
	private File file;
	private FileConfiguration config;

	public Default(NoobHelper plugin) {
		this.plugin = plugin;
	}

	protected void setFile( String fileName ) {
		this.fileName = fileName;
	}

	protected void startup() {
		file = new File(plugin.getDataFolder(), fileName);
		if ( !file.exists() )
			plugin.saveResource(fileName, false);
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
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	public void reload() {
		if ( file == null )
			startup();
		config = YamlConfiguration.loadConfiguration(file);
	}

}
