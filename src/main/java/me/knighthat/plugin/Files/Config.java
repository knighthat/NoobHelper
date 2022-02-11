package me.knighthat.plugin.Files;

import java.io.File;

import me.knighthat.plugin.NoobHelper;

public class Config extends Getters
{

	int fileNumber;

	public Config(NoobHelper plugin) {

		super(plugin);

		setFile("config.yml");
		startup();

		if ( !checkVersion() )
			replaceFile();
	}

	Boolean checkVersion() {

		String currentVersion = get().getString("version");
		String pluginVersion = plugin.getDescription().getVersion();

		return currentVersion.equals(pluginVersion);
	}

	void replaceFile() {

		file.renameTo(generateFileName());
		file.delete();
		reload();
	}

	File generateFileName() {

		String copies = ".old" + (fileNumber == 0 ? "" : " (" + fileNumber + ")");

		File newFile = new File(plugin.getDataFolder(), fileName + copies);

		if ( newFile.exists() ) {
			fileNumber++;
			newFile = generateFileName();
		}

		return newFile;
	}

}
