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

import me.knighthat.plugin.Files.Config;

public class BreakAssistant implements Storage
{
	Config config;

	private final String path = "break_assistant.";

	public BreakAssistant(Config config, BlockBreakEvent e) {

		this.config = config;

		final Player player = e.getPlayer();

		if ( !checkTool(player) )
			return;

		if ( !checkRequirements(player) )
			return;

		assist(getAffiliation(e.getBlock(), config.getInt(path + "max_block")), player);

	}

	private void assist( List<Block> blocks, Player player ) {

		boolean addHungry = config.getBoolean(path + "food_consumption.enabled"),
				addDamage = config.getBoolean(path + "apply_damage");

		for ( int i = 0 ; i < blocks.size() ; i++ ) {
			if ( addHungry )
				if ( addHungry(player, i) )
					break;

			if ( addDamage )
				if ( addDamage(player) )
					break;

			blocks.get(i).breakNaturally(player.getInventory().getItemInMainHand());
		}
	}

	private boolean checkTool( Player player ) {

		String itemName = player.getInventory().getItemInMainHand().getType().name();
		if ( itemName.endsWith("_SHOVEL") | itemName.endsWith("_PICKAXE") | itemName.endsWith("_AXE") )
			return true;

		return false;
	}

	private boolean checkRequirements( Player player ) {

		final String path = this.path + "requirements.";

		boolean rSneaking = config.getBoolean(path + "sneaking"), rPermission = config.getBoolean(path + "permission"),
				rGameMode = config.getBoolean(path + "survival_mode");

		if ( rSneaking & !player.isSneaking() )
			return false;

		if ( rPermission & !player.hasPermission("noobhelper." + this.path.replace(".", "")) )
			return false;

		if ( rGameMode & !player.getGameMode().equals(GameMode.SURVIVAL) )
			return false;

		return true;
	}

	private boolean addDamage( Player player ) {

		ItemStack item = player.getInventory().getItemInMainHand();
		Damageable dmg = (Damageable) item.getItemMeta();

		if ( item.getMaxItemUseDuration() - dmg.getDamage() == 1 )
			return true;

		int rate = 100 / (item.getEnchantmentLevel(Enchantment.DURABILITY) + 1);

		if ( (new Random()).nextInt(100) <= rate )
			dmg.setDamage(dmg.getDamage() + 1);

		item.setItemMeta((ItemMeta) dmg);

		return false;
	}

	private boolean addHungry( Player player, int broke ) {

		if ( player.getFoodLevel() <= 0 )
			return true;

		double rate = config.getDouble("break_assistant.food_consumption.rate");
		int required = (int) (rate >= 1 ? rate : 1 / rate);

		if ( broke % required == 0 )
			player.setFoodLevel(player.getFoodLevel() - 1);

		return false;
	}

}
