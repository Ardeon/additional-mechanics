package ru.ardeon.additionalmechanics.guild.miners;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ru.ardeon.additionalmechanics.AdditionalMechanics;
import ru.ardeon.additionalmechanics.guild.miners.orescaner.OreConverter;
import ru.ardeon.additionalmechanics.guild.miners.orescaner.OreFilter;
import ru.ardeon.additionalmechanics.guild.miners.orescaner.OreScaner;


public class EventsMiner implements Listener {
	OreConverter converter = new OreConverter();
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getHand()!=null) {
			if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				ItemStack item = e.getItem();
				Player p = e.getPlayer();
				if (item != null) {
					ItemMeta meta = item.getItemMeta();
					if (meta.hasLore()) {
						List<String> lore = meta.getLore();
						if (!p.hasCooldown(Material.COMPASS)&&(lore.get(0).equals("§6Сканер")
								||lore.get(0).equals("§6Малый сканер")||lore.get(0).equals("§6Большой сканер"))) {
							if (p.isSneaking()) {
								if (lore.size()>2) {
									lore.set(2, converter.getNext(lore.get(2)));
									meta.setLore(lore);
									item.setItemMeta(meta);
								}
								else {
									String str = lore.get(0);
									lore.clear();
									lore.add(str);
									lore.add("Ищет");
									lore.add(converter.getNext(""));
									meta.setLore(lore);
									item.setItemMeta(meta);
								}
							}
							else {
								OreFilter filter = new OreFilter();
								if (lore.size()>2) {
									filter = new OreFilter(converter.FromString(lore.get(2)));
								}
								if (filter.testPermission(p)) {
									switch (lore.get(0)) {
									case "§6Малый сканер":{
										if (p.hasPermission("additionalmechanics.miner.radar.small")) {
											new OreScaner(p, e.getClickedBlock(), filter, 1);
											p.setCooldown(Material.COMPASS, 20*30);
										}
										else
											p.sendRawMessage("§cВы не умеете это использовать");
										break;
									}
									case "§6Сканер":{
										if (p.hasPermission("additionalmechanics.miner.radar.medium")) {
											new OreScaner(p, e.getClickedBlock(), filter, 2);
											p.setCooldown(Material.COMPASS, 20*45);
										}
										else
											p.sendRawMessage("§cВы не умеете это использовать");
										break;
									}
									case "§6Большой сканер":{
										if (p.hasPermission("additionalmechanics.miner.radar.big")) {
											new OreScaner(p, e.getClickedBlock(), filter, 3);
											p.setCooldown(Material.COMPASS, 20*70);
										}
										else
											p.sendRawMessage("§cВы не умеете это использовать");
										break;
									}
									}
								}
								else {
									p.sendRawMessage("§cВы не умеете искать данный ресурс");
									p.setCooldown(Material.COMPASS, 20*1);
								}
							}
							
						}
					}
				}
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerBreak(BlockDropItemEvent e) {
		Material m = e.getBlockState().getType();
		Player p =e.getPlayer();
		if (converter.testForMaterial(m)&&p.getGameMode().equals(GameMode.SURVIVAL)) {
			ItemStack item = p.getInventory().getItemInMainHand();
			Location loc = e.getBlock().getLocation().clone().add(0.5, 0.5, 0.5);
			int extra = PermissonConvert.getextradrop(p);
			if (extra>0&&!item.containsEnchantment(Enchantment.SILK_TOUCH)) {
				ItemStack drop = MinerExtraDrop.extradrop(m, extra);
				if (drop!=null&&drop.getAmount()>0) {
					p.getWorld().dropItem(loc, drop);
				}
				if (MinerExtraDrop.test(m)) {
					p.getWorld().dropItem(loc, MinerExtraDrop.drop(m));
					e.setCancelled(true);
				}
						
					
				
			}
		}
		
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerMine(BlockBreakEvent e) {
		Player p = e.getPlayer();
		ItemStack item = p.getInventory().getItemInMainHand();
		Block b = e.getBlock();
		Material m = b.getType();
		if (item != null && item.hasItemMeta()) {
			if (item.getItemMeta().hasLore()) {
				ItemMeta met = item.getItemMeta();
				List<String> lore = met.getLore();
				boolean pass = (m.equals(Material.STONE)||m.equals(Material.ANDESITE)||m.equals(Material.GRANITE)||m.equals(Material.DIORITE)||m.equals(Material.NETHERRACK));
				if (lore.contains("§aУлучшение - Широкие тонели")&&pass)
				{
					float yaw = p.getLocation().getYaw();//0 = positive z; 90 = negative x; 
					AdditionalMechanics.getPlugin().getLogger().info(""+yaw);
					int z0=0,x0=0,z1=0,x1=0,y=0,x=0;
					int power = PermissonConvert.getMineSize(p);
					if (power==0)
						return;
					if (power==3)
						y=1;
					if (power>1)
						x=1;
					if (yaw>315||yaw<=45) {
						z0 = -1;
						if (power>1) {
							x0=-1;x1=1;
						}
						if (power==3)
							z1=1;
					}
					else if (yaw<=135) {
						x1 = 1;
						if (power>1) {
							z0=-1;z1=1;
						}
						if (power==3)
							x0=-1;
					}
					else if (yaw<=225) {
						z1 = 1;
						if (power>1) {
							x0=-1;x1=1;
						}
						if (power==3)
							z0=-1;
					}
					else {
						x0 = -1;
						if (power>1) {
							z0=-1;z1=1;
						}
						if (power==3)
							x1=1;
					}
					
					BlockFace face = e.getPlayer().rayTraceBlocks(5).getHitBlockFace();
					
					switch (face) {
					case DOWN :
					{
						for (int i = x0; i<=x1; i++)
						{
							for (int j = z0; j<=z1; j++)
							{
								if ((j==0) && (i==0))
									continue;
								Block rb = b.getRelative(i, 0, j);
								Material rm = rb.getType();
								if (rm.equals(Material.STONE)||rm.equals(Material.ANDESITE)||rm.equals(Material.GRANITE)||rm.equals(Material.DIORITE)||rm.equals(Material.NETHERRACK))
									rb.breakNaturally(item);
							}
						}
						break;
					}
					case UP :
					{
						for (int i = x0; i<=x1;i++)
						{
							for (int j = z0; j<=z1;j++)
							{
								if ((j==0) && (i==0))
									continue;
								Block rb = b.getRelative(i, 0, j);
								Material rm = rb.getType();
								if (rm.equals(Material.STONE)||rm.equals(Material.ANDESITE)||rm.equals(Material.GRANITE)||rm.equals(Material.DIORITE)||rm.equals(Material.NETHERRACK))
									rb.breakNaturally(item);
							}
						}
						break;
					}
					case EAST :
					{
						for (int i = -1; i<=y;i++)
						{
							for (int j = -x; j<=x;j++)
							{
								if ((j==0) && (i==0))
									continue;
								Block rb = b.getRelative(0, i, j);
								Material rm = rb.getType();
								if (rm.equals(Material.STONE)||rm.equals(Material.ANDESITE)||rm.equals(Material.GRANITE)||rm.equals(Material.DIORITE)||rm.equals(Material.NETHERRACK))
									rb.breakNaturally(item);
							}
						}
						break;
					}
					case WEST :
					{
						for (int i = -1; i<=y;i++)
						{
							for (int j = -x; j<=x;j++)
							{
								if ((j==0) && (i==0))
									continue;
								Block rb = b.getRelative(0, i, j);
								Material rm = rb.getType();
								if (rm.equals(Material.STONE)||rm.equals(Material.ANDESITE)||rm.equals(Material.GRANITE)||rm.equals(Material.DIORITE)||rm.equals(Material.NETHERRACK))
									rb.breakNaturally(item);
							}
						}
						break;
					}
					case NORTH :
					{
						for (int i = -1; i<=y;i++)
						{
							for (int j = -x; j<=x;j++)
							{
								if ((j==0) && (i==0))
									continue;
								Block rb = b.getRelative(j, i, 0);
								Material rm = rb.getType();
								if (rm.equals(Material.STONE)||rm.equals(Material.ANDESITE)||rm.equals(Material.GRANITE)||rm.equals(Material.DIORITE)||rm.equals(Material.NETHERRACK))
									rb.breakNaturally(item);
							}
						}
						break;
					}
					case SOUTH :
					{
						for (int i = -1; i<=y;i++)
						{
							for (int j = -x; j<=x;j++)
							{
								if ((j==0) && (i==0))
									continue;
								Block rb = b.getRelative(j, i, 0);
								Material rm = rb.getType();
								if (rm.equals(Material.STONE)||rm.equals(Material.ANDESITE)||rm.equals(Material.GRANITE)||rm.equals(Material.DIORITE)||rm.equals(Material.NETHERRACK))
									rb.breakNaturally(item);
							}
						}
						break;
					}
					default:
						break;
					}
				}
				
			}
		}
		
		
	}
	
	
}
