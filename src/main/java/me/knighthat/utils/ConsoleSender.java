package me.knighthat.utils;

import java.util.logging.Level;

import org.bukkit.Bukkit;

import me.knighthat.NoobHelper;

public interface ConsoleSender extends TextModification
{

	default void sendMessage( String message ) { Bukkit.getServer().getConsoleSender().sendMessage(addColor(message)); }

	default void sendError( NoobHelper plugin, String error ) { plugin.getLogger().log(Level.FINE, error); }

	default void sendWarning( NoobHelper plugin, String warning ) { plugin.getLogger().log(Level.WARNING, warning); }

}
