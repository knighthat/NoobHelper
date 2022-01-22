package me.knighthat.NoobHelper;

import org.bukkit.plugin.java.JavaPlugin;

import me.knighthat.NoobHelper.Commands.Anvil;
import me.knighthat.NoobHelper.Commands.Cartography;
import me.knighthat.NoobHelper.Commands.Grindstone;
import me.knighthat.NoobHelper.Commands.Loom;
import me.knighthat.NoobHelper.Commands.Reload;
import me.knighthat.NoobHelper.Commands.SmithingTable;
import me.knighthat.NoobHelper.Commands.Stonecutter;
import me.knighthat.NoobHelper.Commands.Workbench;
import me.knighthat.NoobHelper.Events.EventListener;

public class NoobHelper extends JavaPlugin
{

	private boolean isAfter14;
	private boolean isAfter16;

	@Override
	public void onEnable() {
		checkVersion();
		new Console(this);
		new FileGetters(this);
		getServer().getPluginManager().registerEvents(new EventListener(this), this);
		registerCommands();
	}

	private void registerCommands() {
		getCommand("noobhelper").setExecutor(new Reload(this));
		getCommand("anvil").setExecutor(new Anvil(this));
		getCommand("workbench").setExecutor(new Workbench(this));
		if ( !isAfter14 )
			return;
		getCommand("cartography").setExecutor(new Cartography(this));
		getCommand("grindstone").setExecutor(new Grindstone(this));
		getCommand("loom").setExecutor(new Loom(this));
		getCommand("stonecutter").setExecutor(new Stonecutter(this));
		if ( isAfter16 ) {
			getCommand("smithing").setExecutor(new SmithingTable(this));
		}
	}

	private void checkVersion() {
		isAfter14 = compareVersion(14);
		isAfter16 = compareVersion(16);
	}

	private boolean compareVersion( int compareTo ) {
		String ver = getServer().getVersion();
		ver = ver.substring(ver.lastIndexOf(".") - 2, ver.lastIndexOf("."));
		return Integer.parseInt(ver) >= compareTo;
	}
}
