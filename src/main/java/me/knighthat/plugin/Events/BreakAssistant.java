package me.knighthat.plugin.Events;

import java.util.List;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import me.knighthat.plugin.Misc;
import me.knighthat.plugin.Files.ConfigFile;

public class BreakAssistant extends BreakAssistantStorage
{

	public BreakAssistant(ConfigFile config, BlockBreakEvent e) {

		this.config = config;
		this.player = e.getPlayer();

		if ( checkTool() & checkRequirements() )
			assist(getAffiliation(e.getBlock(), config.get().getInt(path + "max_block")));

	}

	void assist( List<Block> blocks ) {

		boolean addHungry = isEnabled(path + "food_consumption.enabled"), addDamage = isEnabled(path + "apply_damage");

		for ( int i = 0 ; i < blocks.size() ; i++ ) {
			if ( addHungry & !addHungry(i) )
				break;

			if ( addDamage & !addDamage() )
				break;

			blocks.get(i).breakNaturally(player.getInventory().getItemInMainHand());
		}
	}
}

abstract class BreakAssistantStorage implements Storage
{

	Player player;
	ConfigFile config;

	final String path = "break_assistant.";

	public Boolean isEnabled( @NotNull String path ) {
		return config.get().getBoolean(path);
	}

	boolean checkTool() {

		String itemName = player.getInventory().getItemInMainHand().getType().name();
		if ( itemName.endsWith("_SHOVEL") | itemName.endsWith("_PICKAXE") | itemName.endsWith("_AXE") )
			return true;
		return false;
	}

	boolean isRequired( @NotNull String path ) {
		return isEnabled(this.path + "requirements." + path);
	}

	boolean checkRequirements() {

		if ( isRequired("sneaking") & !player.isSneaking() )
			return false;

		if ( isRequired("permission") )
			if ( !Misc.checkPermission(player, config, this.path.replace(".", ""), false) )
				return false;

		if ( isRequired("survival_mode") & !player.getGameMode().equals(GameMode.SURVIVAL) )
			return false;

		return true;
	}

	boolean addDamage() {

		ItemStack item = player.getInventory().getItemInMainHand();
		Damageable dmg = (Damageable) item.getItemMeta();

		if ( item.getType().getMaxDurability() - dmg.getDamage() <= 1 )
			return false;

		int rate = 100 / (item.getEnchantmentLevel(Enchantment.DURABILITY) + 1);

		if ( (new Random()).nextInt(100) <= rate )
			dmg.setDamage(dmg.getDamage() + 1);

		item.setItemMeta((ItemMeta) dmg);

		return true;
	}

	boolean addHungry( int broke ) {

		if ( player.getFoodLevel() <= 0 )
			return false;

		double rate = config.get().getDouble(this.path + "food_consumption.rate");
		int required = (int) (rate >= 1 ? rate : 1 / rate);

		if ( broke % required == 0 )
			player.setFoodLevel(player.getFoodLevel() - 1);

		return true;
	}
}
