package me.knighthat.plugin.Files;

import java.io.File;

import me.knighthat.plugin.Misc;
import me.knighthat.plugin.NoobHelper;

public class ConfigFile extends Files
{

	final String fileName = "config.yml";
	String currentVersion;
	String pluginVersion;
	Integer fileNumber = 0;

	public ConfigFile(NoobHelper plugin) {
		super.plugin = plugin;
		startup();
	}

	@Override
	public String setFile() { return "config.yml"; }

	@Override
	public boolean checkFile() {
		currentVersion = get().getString("version");
		pluginVersion = plugin.getDescription().getVersion();

		return currentVersion.equals(pluginVersion);
	}

	@Override
	public void doIfCheckFailed() {
		Misc.sendMessage("&cDetected unsupported version of &2config.yml");
		Misc.sendMessage("&eSaving old file and generating new one...");

		final File oldFile = generateFileName();

		file.renameTo(oldFile);
		file.delete();
		reload();

		Misc.sendMessage("&aNew file generated successfully!");
		Misc.sendMessage("&aYou can find it under name " + oldFile.getName());
	}

	File generateFileName() {

		String copies = (fileNumber == 0 ? "" : " (" + fileNumber + ") ").concat(".old");

		File newFile = new File(plugin.getDataFolder(), fileName + copies);

		if ( newFile.exists() ) {
			fileNumber++;
			newFile = generateFileName();
		}

		fileNumber = 0;

		return newFile;
	}

	public String getString( String path, Boolean addPrefix ) {

		final String prefix = addPrefix ? super.getString("prefix") : "";
		final String result = super.getString(path);

		return prefix.concat(result);
	}
}
