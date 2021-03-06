package com.koningcool.fastboat.fastboat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Objects;

public final class Fastboat extends JavaPlugin implements Listener {

    public float boatSpeed;
    private static Fastboat plugin;

    @Override
    public void onLoad() {
        System.out.println(ChatColor.GREEN + "FastBoat loaded!");
    }

    @Override
    public void onEnable() {
        plugin = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        Objects.requireNonNull(getCommand("setboatspeed")).setExecutor(new boatSetSpeedCommand());
        Objects.requireNonNull(getCommand("boatspeed")).setExecutor(new boatSpeedCommand());
        plugin.boatSpeed = Float.parseFloat(Objects.requireNonNull(getConfig().getString("speedmultiplier")));

        System.out.println(ChatColor.GREEN + "FastBoat is enabled!");
        System.out.println(ChatColor.RED + "Warning, only change the config file with /setboatspeed [your speed here]");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        System.out.println(ChatColor.RED + "FastBoat is disabled!");
    }

    @EventHandler
    public void onVehicleDrive(VehicleMoveEvent event) {
        Entity vehicle = event.getVehicle();
        ArrayList<Entity> passengers = (ArrayList<Entity>) event.getVehicle().getPassengers();
        boolean hasPlayer = false;
        for (Entity entity : passengers) {
            if (entity instanceof Player) {
                hasPlayer = true;
                break;
            }
        }
        if (vehicle instanceof Boat) {
            if (hasPlayer) {
                Player p = (Player) passengers.get(0);
                Boat boat = (Boat) vehicle;
                if (p.hasPermission("fastboat.use")) {
                    boat.setVelocity(new Vector(boat.getVelocity().clone().multiply(this.boatSpeed).getX(), boat.getVelocity().clone().getY() , boat.getVelocity().clone().multiply(this.boatSpeed).getZ()));
                    System.out.println("X:" + boat.getVelocity().getX());
                    System.out.println("Y:" + boat.getVelocity().getY());
                    System.out.println("Z:" + boat.getVelocity().getZ());
                } else {
                    p.sendMessage("You do not have permission to use the boat fast, but you can still you the boat!");
                }

            }
        }
    }

    public static Fastboat getPlugin() {
        return plugin;
    }
}




