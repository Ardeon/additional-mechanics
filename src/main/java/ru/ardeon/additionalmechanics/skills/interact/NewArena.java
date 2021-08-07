package ru.ardeon.additionalmechanics.skills.interact;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.ardeon.additionalmechanics.configs.settings.SettingsLoaderUseableItems;
import ru.ardeon.additionalmechanics.skills.template.InteractSkill;

public class NewArena {
    public static InteractSkill snowball = new InteractSkill() {
        @Override
        public void execute(PlayerInteractEvent e) {
            Player player = e.getPlayer();
            World world = player.getWorld();
            Material material;
            material = SettingsLoaderUseableItems.SettingItems.SNOWBALL_MATERIAL.getMaterial();
            if (!(player.hasCooldown(material)))
            {
                int cooldown = SettingsLoaderUseableItems.SettingItems.SNOWBALL_COOLDOWN.getInt();
                Class<Snowball> ball = Snowball.class;
                Snowball projectile = e.getPlayer().launchProjectile(ball);
                projectile.setShooter(e.getPlayer());
                projectile.addScoreboardTag("snowball");
                ItemStack itemStack = new ItemStack(Material.LEATHER_HORSE_ARMOR);
                ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(Material.LEATHER_HORSE_ARMOR);
                itemMeta.setCustomModelData(1);
                itemStack.setItemMeta(itemMeta);
                projectile.setItem(itemStack);
                player.setCooldown(material, cooldown);
                world.playSound(player.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 2, 2);
                world.playSound(player.getLocation(), Sound.ENTITY_RABBIT_AMBIENT, 2, 0.5f);
            }
        }
    };
}
