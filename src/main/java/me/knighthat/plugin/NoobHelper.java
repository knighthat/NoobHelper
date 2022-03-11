package me.knighthat.plugin;

import java.io.File;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import me.knighthat.plugin.Commands.Guis;
import me.knighthat.plugin.Commands.Manager;
import me.knighthat.plugin.Commands.TabCompletor;
import me.knighthat.plugin.Events.Listener;
import me.knighthat.plugin.Files.Config;
import me.knighthat.plugin.Files.DeathChests;
import me.knighthat.plugin.Files.TrashBins;
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
		getCommand("noobhelper").setTabCompleter(new TabCompletor(this));
		registerCommands();

		getServer().getPluginManager().registerEvents(new Listener(this), this);

		checkFiles();

		new UpdateChecker(this);
	}

	void registerCommands() {

		getCommand("workbench").setExecutor(new Guis(this));

		if ( !checkVersion(16.2) )
			return;

		getCommand("anvil").setExecutor(new Guis(this));
		getCommand("cartography").setExecutor(new Guis(this));
		getCommand("loom").setExecutor(new Guis(this));
		getCommand("grindstone").setExecutor(new Guis(this));
		getCommand("stonecutter").setExecutor(new Guis(this));
		getCommand("smithingtable").setExecutor(new Guis(this));

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
		int pointer = version.lastIndexOf(".");
		version = version.substring(pointer - 2, version.indexOf(")", pointer));

		return Double.parseDouble(version) >= versionToCompare;
	}
}
