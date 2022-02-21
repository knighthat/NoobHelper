package me.knighthat.plugin.Files;

import java.util.ArrayList;
import java.util.List;

import me.knighthat.plugin.Miscellaneous;

public abstract class Files extends FileAbstract implements Miscellaneous
{

	public String getString( String path ) { return addColor(get().getString(path)); }

	public List<String> getSections( String path, Boolean getKeys ) {

		List<String> result = new ArrayList<>();

		if ( get().contains(path) )
			result.addAll(get().getConfigurationSection(path).getKeys(getKeys));

		return result;
	}

}
