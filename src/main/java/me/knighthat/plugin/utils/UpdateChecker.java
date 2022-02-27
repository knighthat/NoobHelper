package me.knighthat.plugin.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Consumer;

import me.knighthat.plugin.NoobHelper;
import net.md_5.bungee.api.ChatColor;

public class UpdateChecker
{

	public UpdateChecker(NoobHelper plugin) {

		compareVersions(plugin);

		if ( plugin.config.get().getBoolean("update_checker") )
			new BukkitRunnable() {

				@Override
				public void run() { compareVersions(plugin); }
			}.runTaskTimer(plugin, 432000l, 432000l);
	}

	void getVersion( final Consumer<String> consumer ) {

		try {

			InputStream getVersion = new URL("https://api.spigotmc.org/legacy/update.php?resource=99419").openStream();

			Scanner scanner = new Scanner(getVersion);

			if ( scanner.hasNext() )
				consumer.accept(scanner.next());

			scanner.close();

		} catch ( IOException e ) {

			error("Couldn't connect to update server. Please report this to developer");
			error(e.getMessage());
			e.printStackTrace();
		}

	}

	void compareVersions( NoobHelper plugin ) {

		String currentVersion = plugin.getDescription().getVersion();

		getVersion(version -> {

			if ( !version.equalsIgnoreCase(currentVersion) ) {

				warning("You're behind! Please update to the newest version.");
				warning("Download version " + version + "@https://www.spigotmc.org/resources/99419/");
			} else
				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "You're up-to-date!");
		});

	}

	void warning( String msg ) {

		String prefix = "[NoobHelper] ";
		Bukkit.getLogger().warning(prefix + msg);

	}

	void error( String msg ) {

		String prefix = "[NoobHelper] ";
		Bukkit.getLogger().finest(prefix + msg);

	}
}
