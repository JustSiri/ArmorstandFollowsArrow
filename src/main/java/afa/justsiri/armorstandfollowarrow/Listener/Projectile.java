package afa.justsiri.armorstandfollowarrow.Listener;

import afa.justsiri.armorstandfollowarrow.ArmorstandFollowArrow;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Timer;
import java.util.TimerTask;

public class Projectile implements Listener {

    public Arrow arrow;
    public ArmorStand armorStand;

    private boolean tf;

    private void teleport(Entity entity, Player player) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                entity.teleport(arrow.getLocation());
                if (tf) {
                    entity.remove();
                    tf = false;
                }
            }
        }; new Timer().schedule(task, 0, 10);
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();
            if (event.getEntity() instanceof Arrow) {
                arrow = (Arrow) event.getEntity();
                armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
                ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                assert skullMeta != null;
                skullMeta.setOwner(player.getDisplayName());
                skull.setItemMeta(skullMeta);
                armorStand.setHelmet(skull);
                armorStand.setVisible(false);
                armorStand.setGravity(false);
                teleport(armorStand, player);
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            if (event.getEntity() instanceof Arrow) {
                Arrow arrow1 = (Arrow) event.getEntity();
                if (arrow1.equals(arrow)) {
                    armorStand.remove();
                    tf = true;
                    if (!(event.getHitEntity() instanceof ArmorStand)) {
                        event.getHitEntity().getWorld().createExplosion(event.getHitEntity().getLocation(), 2, false, true);
                    }
                }
            }
        }
    }

}
