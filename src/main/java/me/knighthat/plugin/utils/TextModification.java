package me.knighthat.plugin.utils;

import net.md_5.bungee.api.ChatColor;

public interface TextModification
{

	default String addColor( String text ) { return ChatColor.translateAlternateColorCodes('&', text); }

	default String stripColor( String text ) { return ChatColor.stripColor(addColor(text)); }

}
