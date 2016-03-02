package me.dietcookiez.randomspawn;

import me.dietcookiez.randomspawn.utils.C;
import me.dietcookiez.randomspawn.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Random;

/**
 * DietCookiez
 * At: 9:25 AM (Mar/02/2016)
 * RandomSpawn
 */

public class Main extends JavaPlugin implements Listener
{
    public static Main instance;

    @Override
    public void onEnable()
    {
        try
        {
            long delta = System.currentTimeMillis();
            instance = this;

            if(!(new File(getDataFolder(), "config.yml")).exists())
            {
                Util.log("No config.yml, generating one...");
                saveDefaultConfig();
            }

            getServer().getPluginManager().registerEvents(this, this);
            getCommand("randomspawn").setExecutor(new Command());

            if(!Util.isBoolean(getConfig().getString("world")))
                Util.log("\"world\" in config in not formatted correctly!", true);

            for(int i = 1; i <= 4; i++)
            {
                if(!Util.isDouble(getConfig().getString("spawn" + i + ".x")))
                    Util.log("\"spawn" + i + ".x\" in config in not formatted correctly!", true);
                if(!Util.isDouble(getConfig().getString("spawn" + i + ".y")))
                    Util.log("\"spawn" + i + ".y\" in config in not formatted correctly!", true);
                if(!Util.isDouble(getConfig().getString("spawn" + i + ".z")))
                    Util.log("\"spawn" + i + ".z\" in config in not formatted correctly!", true);
            }

            Util.log("Enabled RandomSpawn v" +
                    Bukkit.getPluginManager().getPlugin("RandomSpawn").getDescription().getVersion() +
                    " in " + (System.currentTimeMillis() - delta) + " milliseconds!");
        }

        catch(Exception e)
        {
            Util.log("An error occurred while enabling! Check to make sure config.yml is formatted correctly. Contact " +
            Bukkit.getPluginManager().getPlugin("RandomSpawn").getDescription().getAuthors().get(0) + "for help.");
        }
    }

    @Override
    public void onDisable()
    {
        Util.log("Disabling RandomSpawn.");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        try
        {
            Player player = event.getPlayer();

            if(player.hasPlayedBefore() && getConfig().getBoolean("world"))
                return;

            if(getConfig().getString("players-joined." + player.getUniqueId()) != null)
                return;

            int random = new Random().nextInt((4 - 1) + 1) + 1;
            World world = Util.getWorld(getConfig().getString("spawn" + random + ".world"));
            double x, y, z;

            if(world == null)
            {
                Util.log("No such world: " + C.Yellow + getConfig().getString("spawn" + random + ".world"));
                return;
            }

            if(Util.isDouble(getConfig().getString("spawn" + random + ".x")))
                x = Double.parseDouble(getConfig().getString("spawn" + random + ".x"));
            else
            {
                Util.log(C.Yellow + getConfig().getString("spawn" + random + ".x") + C.White + " is not a number!");
                return;
            }

            if(Util.isDouble(getConfig().getString("spawn" + random + ".y")))
                y = Double.parseDouble(getConfig().getString("spawn" + random + ".y"));
            else
            {
                Util.log(C.Yellow + getConfig().getString("spawn" + random + ".y") + C.White + " is not a number!");
                return;
            }

            if(Util.isDouble(getConfig().getString("spawn" + random + ".z")))
                z = Double.parseDouble(getConfig().getString("spawn" + random + ".z"));
            else
            {
                Util.log(C.Yellow + getConfig().getString("spawn" + random + ".z") + C.White + " is not a number!");
                return;
            }

            if(!getConfig().getString("message").equals("null"))
                player.sendMessage(String.format(getConfig().getString("message"), random));
            player.teleport(new Location(world, x, y, z));
            getConfig().set("players-joined." + player.getUniqueId(), "joined");
            saveConfig();
        }

        catch(Exception e)
        {
            Util.log("A \"" + e + "\"" + " occurred! Check to make sure config.yml is formatted correctly. Contact " +
                    Bukkit.getPluginManager().getPlugin("RandomSpawn").getDescription().getAuthors().get(0) + "for help.");
        }
    }
}
