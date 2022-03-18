package me.knighthat.plugin.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum EquipmentType
{
	WEAPON, TOOL, ARMOR, SHIELD;

	public static EquipmentType match( ItemStack item ) { return match(item.getType()); }

	public static EquipmentType match( Material material ) {

		String mString = material.toString();

		if ( mString.endsWith("HELMET") | mString.endsWith("CHESTPLATE") | mString.endsWith("LEGGINGS") | mString.endsWith("BOOTS") | mString.endsWith("ELYTRA") )
			return ARMOR;

		if ( mString.equals("SHIELD") ) { return SHIELD; }

		if ( mString.endsWith("BOW") | mString.endsWith("SWORD") | mString.endsWith("TRIDENT") | mString.endsWith("CROSSBOW") )
			return WEAPON;

		return TOOL;
	}
}
