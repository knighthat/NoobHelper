package me.knighthat.plugin.Files;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.World;

import me.knighthat.plugin.NoobHelper;

public class BlockDataFile extends Files
{

	final String fileName = "blockdata.yml";
	final List<World> worlds = Bukkit.getServer().getWorlds();

	public BlockDataFile(NoobHelper plugin) {
		super.plugin = plugin;
		startup();
	}

	@Override
	public String setFile() { return "blockdata.yml"; }

	@Override
	public boolean checkFile() { return false; }

	@Override
	public void doIfCheckFailed() {

		worlds.forEach(world -> {

			getSections(world.getName()).forEach(section -> {

				section = world.getName().concat("." + section);

				get().set(section.replace("sign_", ""), get().getString(section));
				if ( section.contains("sign_") & section.endsWith(".Owner") )
					get().set(section.replace(".Owner", ""), null);
			});

			save();
		});
	}

	Stream<String> getSections( String path ) {

		if ( !get().contains(path) ) { return (new ArrayList<String>()).stream(); }

		Stream<String> result = get().getConfigurationSection(path).getKeys(true).stream();
		return result.filter(section -> section.startsWith("sign_"));
	}

}
