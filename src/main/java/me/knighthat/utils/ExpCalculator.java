package me.knighthat.utils;

import org.bukkit.entity.Player;

public interface ExpCalculator
{

	/*
	 * BIG shout out to DOGC_Kyle for doing the research
	 * https://www.spigotmc.org/threads/how-to-get-players-exp-points.239171/
	 */

	public default int getExpToLevelUp( int level ) {
		if ( level <= 15 ) {
			return 2 * level + 7;
		} else if ( level <= 30 ) {
			return 5 * level - 38;
		} else
			return 9 * level - 158;
	}

	public default int getExpAtLevel( int level ) {
		if ( level <= 16 ) {
			return (int) (Math.pow(level, 2) + 6 * level);
		} else if ( level <= 31 ) {
			return (int) (2.5 * Math.pow(level, 2) - 40.5 * level + 360.0);
		} else
			return (int) (4.5 * Math.pow(level, 2) - 162.5 * level + 2220.0);
	}

	public default int getPlayerExp( Player player ) {
		int level = player.getLevel();
		int exp = Math.round(getExpToLevelUp(level) * player.getExp());
		return getExpAtLevel(level) + exp;
	}
}
