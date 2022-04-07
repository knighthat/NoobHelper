package me.knighthat.plugin.files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.knighthat.plugin.NoobHelper;

public class TrashBins extends Files
{

	List<World> worlds = Bukkit.getServer().getWorlds();

	public TrashBins(NoobHelper plugin) {
		super.plugin = plugin;
		startup();
	}

	@Override
	public String setFile() { return "trashbins"; }

	@Override
	public boolean checkFile() {

		worlds.forEach(world -> {

			getSections(world.getName()).map(section -> world.getName().concat("." + section)).forEach(section -> {

				get().set(section.replace("sign_", ""), get().getString(section));
				if ( section.contains("sign_") & section.endsWith(".Owner") )
					get().set(section.replace(".Owner", ""), null);
			});
			save();
		});

		return true;
	}

	@Override
	public void doIfCheckFailed() {}

	Stream<String> getSections( String path ) {

		if ( !get().contains(path) ) { return (new ArrayList<String>()).stream(); }

		return getSections(path, true).stream().filter(section -> section.startsWith("sign_"));
	}

	public void copyContent( File oldFile ) {

		FileConfiguration converter = YamlConfiguration.loadConfiguration(oldFile);

		worlds.forEach(world -> {

			final String wName = world.getName();

			if ( converter.contains(world.getName()) ) {

				Iterator<String> sections = converter.getConfigurationSection(wName).getKeys(true).iterator();

				while ( sections.hasNext() ) {
					final String section = "." + sections.next();
					get().set(wName.concat(section), converter.get(wName.concat(section)));
				}

				save();
			}

			converter.set(wName, null);
			try {
				converter.save(oldFile);
			} catch ( IOException e ) {}
		});
	}
}
