package me.knighthat.plugin;

import java.io.File;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import me.knighthat.plugin.Commands.Guis;
import me.knighthat.plugin.Commands.Manager;
import me.knighthat.plugin.Commands.TabCompletor;
import me.knighthat.plugin.Events.Listener;
import me.knighthat.plugin.files.Config;
import me.knighthat.plugin.files.DeathChests;
import me.knighthat.plugin.files.TrashBins;
import me.knighthat.plugin.utils.UpdateChecker;

public class NoobHelper extends JavaPlugin
{

	public Config config = new Config(this);
	public TrashBins trashBins = new TrashBins(this);
	public DeathChests deathChests = new DeathChests(this);

	public final NamespacedKey NamespacedKey = new NamespacedKey(this, getName());
	public final PersistentDataType<String, String> dataType = PersistentDataType.STRING;

	@Override
	public void onEnable() {

		getCommand("noobhelper").setExecutor(new Manager(this));
		getCommand("noobhelper").setTabCompleter(new TabCompletor());
		registerCommands();

		getServer().getPluginManager().registerEvents(new Listener(this), this);

		checkFiles();

		new UpdateChecker(this);
	}

	public void registerCommands() {

		if ( !checkVersion(16.2) )
			return;

		config.getSections("gui_shortcuts", false).forEach(cmd -> {

			Guis shortcut = new Guis(config, cmd, config.get().getStringList("gui_shortcuts." + cmd));

			getServer().getCommandMap().register(cmd, "noobhelper", shortcut);

		});
	}

	void checkFiles() {

		File oldFile = new File(getDataFolder(), "blockdata.yml");

		if ( !oldFile.exists() ) { return; }

		trashBins.copyContent(oldFile);
		trashBins.reload();
		deathChests.copyContent(oldFile);
		deathChests.reload();

		oldFile.delete();
	}

	public final boolean checkVersion( double versionToCompare ) {

		String version = getServer().getVersion();
		int pointer = version.lastIndexOf("(MC: ") + 5;

		version = version.substring(pointer, version.indexOf(")", pointer));
		if ( version.startsWith("1.") )
			version = version.replace("1.", "");

		return Double.parseDouble(version) >= versionToCompare;
	}
}
