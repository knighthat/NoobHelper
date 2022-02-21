package me.knighthat.plugin.Files;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import me.knighthat.plugin.NoobHelper;

public class DeathChests extends Files
{

	public DeathChests(NoobHelper plugin) {
		super.plugin = plugin;
		startup();
	}

	@Override
	public String setFile() { return "deathchests"; }

	@Override
	public boolean checkFile() { return !get().contains("death_chest"); }

	@Override
	public void doIfCheckFailed() {

		getSections("death_chest", false).forEach(section -> {

			final String path = "death_chest." + section + ".";

			String name = getString(path.concat("Name")), uuid = getString(path.concat("UUID")), worldName = null;
			int x = get().getInt(path.concat("X")), y = get().getInt(path.concat("Y")),
					z = get().getInt(path.concat("Z"));
			Map<Integer, ItemStack> items = new HashMap<>();

			for ( String itemSection : getSections(path.concat(".items"), false) )
				items.put(Integer.parseInt(itemSection), get().getItemStack(path.concat(".items." + itemSection)));

			for ( World world : Bukkit.getWorlds() )
				if ( world.getBlockAt(x, y, z).getType().equals(Material.CHEST) )
					worldName = world.getName();

			if ( worldName != null ) {

				final String newPath = name.concat("_" + uuid + "." + worldName + "." + section);

				get().set(newPath.concat(".X"), x);
				get().set(newPath.concat(".Y"), y);
				get().set(newPath.concat(".Z"), z);
				get().set(newPath.concat(".Exp"), 0);
				for ( Integer slot : items.keySet() )
					get().set(newPath.concat(".Items." + slot), items.get(slot));
			}
		});

		get().set("death_chest", null);
		save();
	}

	public void copyContent( File oldFile ) {

		FileConfiguration converter = YamlConfiguration.loadConfiguration(oldFile);

		Iterator<String> sections = converter.getConfigurationSection("").getKeys(true).iterator();

		while ( sections.hasNext() ) {
			final String section = sections.next();
			get().set(section, converter.get(section));
		}

		save();
	}
}
