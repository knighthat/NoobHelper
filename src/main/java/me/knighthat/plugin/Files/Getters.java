package me.knighthat.plugin.Files;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import me.knighthat.plugin.Misc;
import me.knighthat.plugin.NoobHelper;

public abstract class Getters extends Default
{

	public Getters(NoobHelper plugin) {
		super(plugin);
	}

	String getPrefix() {
		return get().getString("prefix");
	}

	public String getString( String path, Boolean addPrefix ) {
		return Misc.addColor(addPrefix ? getPrefix().concat(getString(path)) : getString(path));
	}

	@Deprecated
	public String getString( String path ) {
		return get().getString(path);
	}

	public Integer getInt( String path ) {
		return get().getInt(path);
	}

	public Double getDouble( String path ) {
		return get().getDouble(path);
	}

	public Boolean getBoolean( String path ) {
		return get().getBoolean(path);
	}

	public List<String> getStringList( String path ) {
		return get().getStringList(path);
	}

	public ConfigurationSection getConfigurationSections( String path ) {
		return get().getConfigurationSection(path);
	}

	public Boolean contains( String path ) {
		return get().contains(path);
	}

	public void set( String path, Object value ) {
		get().set(path, value);
	}

}
