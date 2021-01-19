package afa.justsiri.armorstandfollowarrow;

import afa.justsiri.armorstandfollowarrow.Listener.Projectile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Timer;
import java.util.TimerTask;

public final class ArmorstandFollowArrow extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new Projectile(), this);
    }

}
