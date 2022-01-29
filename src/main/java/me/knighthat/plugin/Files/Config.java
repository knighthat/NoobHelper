package me.knighthat.plugin.Files;

import me.knighthat.plugin.NoobHelper;

public class Config extends Getters
{

	public Config(NoobHelper plugin) {
		super(plugin);
		setFile("config.yml");
		startup();
	}

}
