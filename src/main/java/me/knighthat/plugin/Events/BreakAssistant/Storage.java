package me.knighthat.plugin.Events.BreakAssistant;

import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import me.knighthat.plugin.Miscellaneous;
import me.knighthat.plugin.Files.Config;

public abstract class Storage implements me.knighthat.plugin.Events.Storage, Miscellaneous
{

	Player player;
	Config config;

	final String path = "break_assistant.";

	public Boolean isEnabled( String path ) { return config.get().getBoolean(path); }

	boolean checkTool() {

		String itemName = player.getInventory().getItemInMainHand().getType().name();
		if ( itemName.endsWith("_SHOVEL") | itemName.endsWith("_PICKAXE") | itemName.endsWith("_AXE") )
			return true;
		return false;
	}

	boolean isRequired( String path ) { return isEnabled(this.path + "requirements." + path); }

	boolean checkRequirements() {

		if ( isRequired("sneaking") & !player.isSneaking() ) { return false; }

		if ( isRequired("permission") )
			if ( !checkPermission(player, config, path.replace(".", ""), false) )
				return false;

		if ( isRequired("survival_mode") & !player.getGameMode().equals(GameMode.SURVIVAL) )
			return false;

		return true;
	}

	boolean addDamage() {

		ItemStack item = player.getInventory().getItemInMainHand();
		Damageable dmg = (Damageable) item.getItemMeta();

		if ( item.getType().getMaxDurability() - dmg.getDamage() <= 1 ) { return false; }

		int rate = 100 / (item.getEnchantmentLevel(Enchantment.DURABILITY) + 1);

		if ( (new Random()).nextInt(100) <= rate ) { dmg.setDamage(dmg.getDamage() + 1); }

		item.setItemMeta((ItemMeta) dmg);

		return true;
	}

	boolean addHungry( int broke ) {

		if ( player.getFoodLevel() <= 0 ) { return false; }

		double rate = config.get().getDouble(this.path + "food_consumption.rate");
		int required = (int) (rate >= 1 ? rate : 1 / rate);

		if ( broke % required == 0 ) { player.setFoodLevel(player.getFoodLevel() - 1); }

		return true;
	}

}
