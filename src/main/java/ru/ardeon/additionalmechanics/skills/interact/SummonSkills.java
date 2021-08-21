package ru.ardeon.additionalmechanics.skills.interact;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import ru.ardeon.additionalmechanics.configs.settings.SettingsLoaderUseableItems;
import ru.ardeon.additionalmechanics.integrations.mobarena.MobArenaIntegration;
import ru.ardeon.additionalmechanics.skills.template.InteractSkill;

public class SummonSkills {
    public static InteractSkill wolfSummon = new InteractSkill() {
        @Override
        public void execute(PlayerInteractEvent e) {
            Player player = e.getPlayer();
            World world = player.getWorld();
            Material material = SettingsLoaderUseableItems.SettingItems.SUMMON_WOLF_MATERIAL.getMaterial();
            if (!(player.hasCooldown(material))) {
                player.setCooldown(material, SettingsLoaderUseableItems.SettingItems.SUMMON_WOLF_COOLDOWN.getInt());
                MobArenaIntegration.getInstance().addMythicMobPetToArena(player, "SummonedWolf1");
                ItemStack item = e.getItem();
                item.setAmount(item.getAmount()-1);
            }
        }

    };
}
