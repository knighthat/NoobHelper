package me.knighthat.NoobHelper.Events;

import java.util.List;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import me.knighthat.NoobHelper.NoobHelper;

public class BlockBreakAssistant extends EventStorage
{

	private final String path = "block_break_assist.";

	private final boolean rSneaking = confBoolean(path + "sneaking");
	private final boolean rPermission = confBoolean(path + "permission");
	private final boolean rSurvival = confBoolean(path + "survival_mode");

	private final boolean fConsumption = confBoolean(path + "food_consumption.enabled");
	private final double cRate = confDouble(path + "food_consumption.rate");

	public BlockBreakAssistant(NoobHelper plugin) {
		super(plugin);
	}

	public void assist( Player player, Block block ) {

		if ( !checkRequirements(player) )
			return;

		List<Block> blocks = getAffiliation(block);

		for ( int i = 0 ; i < blocks.size() ; i++ ) {

			if ( !addHungry(player, i) || !damageItem(player.getInventory().getItemInMainHand()) )
				break;

			blocks.get(i).breakNaturally();
		}
	}

	private boolean checkRequirements( Player player ) {

		if ( rSneaking & !player.isSneaking() || rPermission & !player.hasPermission("easygame.block_break_assist") )
			return false;

		if ( rSurvival & !player.getGameMode().equals(GameMode.SURVIVAL) )
			return false;
		return true;
	}

	private boolean addHungry( Player player, Integer i ) {
		if ( player.getFoodLevel() <= 0 )
			return false;

		double required = cRate >= 1 ? cRate : 1 / cRate;

		if ( (i + 1) % required == 0 )
			player.setFoodLevel(player.getFoodLevel() - (fConsumption ? 1 : 0));

		return true;
	}

	private boolean damageItem( ItemStack item ) {

		if ( !confBoolean(path + "apply_damage") )
			return false;

		Damageable damage = (Damageable) item.getItemMeta();

		if ( item.getType().getMaxDurability() - damage.getDamage() == 1 )
			return false;

		int chance = new Random().nextInt(100);
		int safezone = item.getEnchantmentLevel(Enchantment.DURABILITY) + 1;

		if ( chance <= 100 / safezone ) {
			damage.setDamage(damage.getDamage() + 1);
		}
		item.setItemMeta((ItemMeta) damage);
		return true;
	}
}
