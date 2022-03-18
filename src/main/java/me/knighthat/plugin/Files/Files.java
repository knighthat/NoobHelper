package me.knighthat.plugin.Files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.utils.TextModification;

public abstract class Files extends FileAbstract implements TextModification
{

	public String getString( String path ) { return addColor(get().getString(path)); }

	public List<String> getSections( String path, Boolean getKeys ) {

		List<String> result = new ArrayList<>();

		if ( get().contains(path) )
			result.addAll(get().getConfigurationSection(path).getKeys(getKeys));

		return result;
	}

}

abstract class FileAbstract
{
	NoobHelper plugin;

	File file;
	FileConfiguration fileConfig;

	public void startup() {

		file = new File(plugin.getDataFolder(), setFile());

		if ( !file.exists() ) { plugin.saveResource(file.getName(), false); }

		reload();
	}

	public FileConfiguration get() {

		if ( fileConfig == null )
			reload();

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

		if ( file == null | !file.exists() ) { startup(); }

		fileConfig = YamlConfiguration.loadConfiguration(file);

		if ( !checkFile() ) { doIfCheckFailed(); }
	}

	public abstract boolean checkFile();

	public abstract void doIfCheckFailed();

	public abstract String setFile();
}