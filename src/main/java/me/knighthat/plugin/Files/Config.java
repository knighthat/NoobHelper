package me.knighthat.plugin.Files;

import java.io.File;

import me.knighthat.plugin.NoobHelper;

public class Config extends Files
{

	final String fileName = "config.yml";
	String currentVersion;
	String pluginVersion;
	Integer fileNumber = 0;

	public Config(NoobHelper plugin) {
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
		sendMessage("&cDetected unsupported version of &2config.yml");
		sendMessage("&eSaving old file and generating new one...");

		final File oldFile = generateFileName();

		file.renameTo(oldFile);
		file.delete();
		reload();

		sendMessage("&aNew file generated successfully!");
		sendMessage("&aYou can find it under name " + oldFile.getName());
	}

	File generateFileName() {

		String copies = (fileNumber == 0 ? "" : " (" + fileNumber + ")").concat(".old");

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
