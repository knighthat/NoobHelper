package me.knighthat.NoobHelper;

import java.util.List;

import me.knighthat.NoobHelper.Files.Blockdata;
import me.knighthat.NoobHelper.Files.Config;

public class FileGetters extends Console
{

	protected Config config;
	protected Blockdata blockdata;

	public FileGetters(NoobHelper plugin) {
		super(plugin);
		config = new Config(plugin);
		blockdata = new Blockdata(plugin);
	}

	public String cShortcuts = "command_shortcuts.require_permission.";
	public String cmdPerm = "noobhelper.command.";

	public String confString( String path ) {
		return addColor(config.get().getString(path));
	}

	public Integer confInt( String path ) {
		return config.get().getInt(path);
	}

	public Double confDouble( String path ) {
		return config.get().getDouble(path);
	}

	public Long confLong( String path ) {
		return config.get().getLong(path);
	}

	public Boolean confBoolean( String path ) {
		return config.get().getBoolean(path);
	}

	public List<String> confStringList( String path ) {
		return config.get().getStringList(path);
	}
}
