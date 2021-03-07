package ru.ardeon.additionalmechanics;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import com.Zrips.CMI.events.CMIAfkEnterEvent;
import com.Zrips.CMI.events.CMIAfkLeaveEvent;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import net.joshb.deathmessages.api.events.BroadcastDeathMessageEvent;
import net.raidstone.wgevents.events.RegionsEnteredEvent;
import ru.ardeon.additionalmechanics.skills.BlockBreakSkillSwitcher;
import ru.ardeon.additionalmechanics.skills.BlockDropItemSkillSwitcher;
import ru.ardeon.additionalmechanics.skills.EntityDamageByEntitySkillSwitcher;
import ru.ardeon.additionalmechanics.skills.InteractEntitySkillSwitcher;
import ru.ardeon.additionalmechanics.skills.InteractSkillSwitcher;
import ru.ardeon.additionalmechanics.skills.ProjectileHitSkillSwitcher;
import ru.ardeon.additionalmechanics.skills.ShootBowSkillSwitcher;
import ru.ardeon.additionalmechanics.util.ItemUtil;
import ru.ardeon.additionalmechanics.util.sidebar.AdmSideBar;

public class EventsListener implements Listener 
{
	private BlockBreakSkillSwitcher blockBreakSkillSwitcher = BlockBreakSkillSwitcher.getInstance();
	private BlockDropItemSkillSwitcher blockDropItemSkillSwitcher = BlockDropItemSkillSwitcher.getInstance();
	private EntityDamageByEntitySkillSwitcher entityDamageByEntitySkillSwitcher = EntityDamageByEntitySkillSwitcher.getInstance();
	private InteractEntitySkillSwitcher interactEntitySkillSwitcher = InteractEntitySkillSwitcher.getInstance();
	private InteractSkillSwitcher interactSkillSwitcher = InteractSkillSwitcher.getInstance();
	private ProjectileHitSkillSwitcher projectileHitSkillSwitcher = ProjectileHitSkillSwitcher.getInstance();
	private ShootBowSkillSwitcher shootBowSkillSwitcher = ShootBowSkillSwitcher.getInstance();
	
	public void reload() {
		this.interactSkillSwitcher = InteractSkillSwitcher.getInstance();
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void PlayerDeath(BroadcastDeathMessageEvent e) {
		String text = e.getTextComponent().toLegacyText();
		for (AdmSideBar bar : AdditionalMechanics.getPlugin().sideBars.getAllBars()){
			bar.pushString(text);
		}
		e.setCancelled(true);
		e.setCancelled(true);
		e.getBroadcastedWorlds().clear();
	}
	
	@EventHandler
	public void PlayerJoin(PlayerJoinEvent e) {
		
		String message = "";
		Player player = e.getPlayer();
		AdditionalMechanics.getPlugin().sideBars.addPlayer(player);
		AdditionalMechanics.getPlugin().sideBars.getBar(player).addViewer(player);
		if (!player.hasPlayedBefore())
			message = "§8[§a+§8] §f" + player.getName() + " §7на сервере впервые!";
		else
			message = "§8[§a+§8] §f" + player.getName();
		for (AdmSideBar bar : AdditionalMechanics.getPlugin().sideBars.getAllBars()){
			bar.pushString(message);
		}
	}
	
	@EventHandler
	public void PlayerAFK(CMIAfkEnterEvent e) {
		Player player = e.getPlayer();
		for (AdmSideBar bar : AdditionalMechanics.getPlugin().sideBars.getAllBars()){
			bar.pushString("§8[§7●§8] §f" + player.getName() + " §7афк");
		}
	}
	
	@EventHandler
	public void PlayerAFKleave(CMIAfkLeaveEvent e) {
		Player player = e.getPlayer();
		for (AdmSideBar bar : AdditionalMechanics.getPlugin().sideBars.getAllBars()){
			bar.pushString("§8[§a●§8] §f" + player.getName() + " §7активен");
		}
	}
	
	
	@EventHandler
	public void PlayerEnterRegion(RegionsEnteredEvent e) {
		Player player = e.getPlayer();
		if (player==null)
			return;
		List<ProtectedRegion> regions = new ArrayList<ProtectedRegion>();
		regions.addAll(e.getRegions());
		regions.sort((region1, region2) -> region2.getPriority() - region1.getPriority());
		String string = null;
		for (int i = 0; i < regions.size(); i++) {
			if (string==null) {
				string = "" + regions.get(i).getId() + "§7";
			} else {
				string = string + ", " + regions.get(i).getId();
			}
		}
		if (string == null)
			string = "§7Можно приватить";
		string = "§eРегион§f: " + string;
		AdditionalMechanics.getPlugin().sideBars.getBar(player).setString(string, 13);
	}
	
	@EventHandler
	public void PlayerQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		for (AdmSideBar bar : AdditionalMechanics.getPlugin().sideBars.getAllBars()){
			bar.pushString("§8[§c-§8] §f" + player.getName());
		}
		AdditionalMechanics.getPlugin().sideBars.removePlayer(player);;
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerMine(BlockBreakEvent e) {
		blockBreakSkillSwitcher.SkillChoose(e);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerGetBlockDrop(BlockDropItemEvent e) {
		blockDropItemSkillSwitcher.SkillChoose(e);
	}
	
	@EventHandler
	public void ShootBow(EntityShootBowEvent e) {
		shootBowSkillSwitcher.SkillChoose(e);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayEditSpawner(PlayerInteractEvent e) {
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getState() instanceof CreatureSpawner)) {
			CreatureSpawner sp = (CreatureSpawner) e.getClickedBlock().getState();
			ItemStack i = e.getItem();
		    switch (e.getMaterial()) {
		    case CREEPER_HEAD :
		    	if (!sp.getSpawnedType().equals(EntityType.CREEPER)) {
		    		sp.setSpawnedType(EntityType.CREEPER);
		    		i.setAmount(i.getAmount()-1);
		    	}
		    	break;
		    case ZOMBIE_HEAD :
		    	if (!sp.getSpawnedType().equals(EntityType.ZOMBIE)) {
		    		sp.setSpawnedType(EntityType.ZOMBIE);
		    		i.setAmount(i.getAmount()-1);
		    	}
		    	break;
		    case SKELETON_SKULL :
		    	if (!sp.getSpawnedType().equals(EntityType.SKELETON)) {
		    		sp.setSpawnedType(EntityType.SKELETON);
		    		i.setAmount(i.getAmount()-1);
		    	}
		    	break;
			default:
				break;
		    }
		    e.setCancelled(true);
		    sp.update();
		}
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) 
	{
		projectileHitSkillSwitcher.SkillChoose(e);
	}
	
	@EventHandler
	public void onPlayerTouchUnTouchable(PlayerArmorStandManipulateEvent e) 
	{
		if (e.getRightClicked().getScoreboardTags().contains("untouchable"))
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerHitEntity(EntityDamageByEntityEvent e) 
	{
		entityDamageByEntitySkillSwitcher.SkillChoose(e);
	}
	
	@EventHandler
	public void onPlayerClickPlayer(PlayerInteractEntityEvent e) 
	{
		interactEntitySkillSwitcher.SkillChoose(e);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) 
	{ 
		if (e.getHand()!=null) {
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			{
				ItemStack item = e.getItem();
				if (ItemUtil.testForSkillTag(item)) {
					interactSkillSwitcher.SkillChoose(e);
				}
			}
		}
	}
}
