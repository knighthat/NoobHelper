package me.knighthat.plugin.Events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.knighthat.plugin.NoobHelper;
import me.knighthat.plugin.Files.Config;
import me.knighthat.plugin.Files.DeathChests;
import me.knighthat.plugin.utils.GetRelatives;
import me.knighthat.plugin.utils.PermissionChecker;

public class BreakAssistant extends GetRelatives implements PermissionChecker
{
	NoobHelper plugin;
	Config config;
	DeathChests deathChests;

	Player player;
	ItemStack inHand;
	Boolean hungry, applyDamage;

	final String path = "break_assistant.";
	int count = 0;

	public BreakAssistant(NoobHelper plugin, Player player, Block starter) {
		super(starter, plugin.config.get().getInt("break_assistant.max_block"));
		this.plugin = plugin;
		this.config = plugin.config;
		this.deathChests = plugin.deathChests;
		this.player = player;
		this.inHand = player.getInventory().getItemInMainHand();
		this.hungry = isEnabled("food_consumption.enabled");
		this.applyDamage = isEnabled("apply_damage");
	}

	public BreakAssistant(NoobHelper plugin, BlockBreakEvent event) {
		this(plugin, event.getPlayer(), event.getBlock());

		List<Block> blocks = getSurrounding(new ArrayList<>());

		if ( isEnabled("add_delay") ) {
			new BukkitRunnable() {

				int count;

				@Override
				public void run() {
					if ( count < blocks.size() ) {
						if ( !breakBlock(blocks.get(count), true) ) { cancel(); }
						count++;
					} else
						cancel();
				}
			}.runTaskTimer(plugin, 0L, config.get().getLong(path.concat("delay")));
		} else
			for ( Block block : blocks )
				if ( !breakBlock(block, false) ) { break; }

	}

	boolean breakBlock( Block block, Boolean isDelayed ) {

		if ( toolNotSupport() ) { return false; }

		if ( !checkRequirements() ) { return false; }

		if ( !player.getGameMode().equals(GameMode.CREATIVE) ) {
			if ( hungry )

				if ( player.getFoodLevel() > 0 ) {

					if ( count % (1 / config.get().getDouble(path.concat("food_consumption.rate"))) == 0 ) {
						int random = new Random().nextInt(100) + 1, level = inHand.getEnchantmentLevel(Enchantment.DURABILITY) + 1;

						if ( random <= (100 / level) ) { player.setFoodLevel(player.getFoodLevel() - 1); }
					}
				} else
					return false;

			if ( applyDamage ) {

				Damageable itemMeta = (Damageable) this.inHand.getItemMeta();

				int chance = (new Random()).nextInt(100) + 1;
				int protecttion = 100 / (inHand.getEnchantmentLevel(Enchantment.DURABILITY) + 1);

				if ( chance <= protecttion ) {

					if ( inHand.getType().getMaxDurability() - itemMeta.getDamage() <= 1 )
						return false;

					itemMeta.setDamage(itemMeta.getDamage() + 1);
					inHand.setItemMeta((ItemMeta) itemMeta);
				}
			}
			block.breakNaturally(inHand);
		} else
			block.setType(Material.AIR);

		if ( plugin.checkVersion(16.5) & isDelayed & isEnabled("add_effect") ) {
			Location loc = block.getLocation();
			block.getWorld().playSound(loc, block.getBlockData().getSoundGroup().getBreakSound(), 1, 1);
			loc.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 30, .1, .1, .1, block.getBlockData());
		}

		count++;

		return true;
	}

	boolean toolNotSupport() {
		String material = this.inHand.getType().name();
		return !material.endsWith("_PICKAXE") & !material.endsWith("_SHOVEL") & !material.endsWith("_AXE");
	}

	boolean isEnabled( String path ) { return config.get().getBoolean(this.path.concat(path)); }

	boolean isRequired( String path ) { return isEnabled("requirements." + path); }

	boolean checkRequirements() {

		if ( isRequired("sneaking") & !player.isSneaking() )
			return false;

		if ( isRequired("permission") & !checkPermission(player, config, "break_assistant") )
			return false;

		if ( isRequired("survival_mode") & !player.getGameMode().equals(GameMode.SURVIVAL) )
			return false;

		return true;
	}

}
