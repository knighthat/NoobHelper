package me.knighthat.plugin.Files;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.knighthat.plugin.NoobHelper;

public abstract class FileAbstract
{
	NoobHelper plugin;

	String fileName;
	File file;
	FileConfiguration fileConfig;

	public void startup() {

		file = new File(plugin.getDataFolder(), fileName);

		if ( !file.exists() )
			plugin.saveResource(fileName, false);

		reload();

		if ( !checkFile() )
			doIfCheckFailed();
	}

	public FileConfiguration get() {
		return fileConfig;
	}

	public void save() {
		try {
			fileConfig.save(file);
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	public void reload() {
		if ( file == null )
			startup();

		fileConfig = YamlConfiguration.loadConfiguration(file);
	}

	public abstract boolean checkFile();

	public abstract void doIfCheckFailed();
}
