package me.dietcookiez.randomspawn.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.Random;

/**
 * DietCookiez
 * At: 9:28 AM (Mar/02/2016)
 * RandomSpawn
 */

public class Util
{
    private static Random random = new Random();

    public static void log(String message)
    {
        log(message, false);
    }

    public static void log(String message, boolean warning)
    {
        Bukkit.getConsoleSender().sendMessage(C.Gold + "[" + C.GoldBold + "RandomSpawn" + C.Gold + "] " +
                (warning ? C.DRedBold + "WARNING: " : "") + C.White + message);
    }

    // This is to make sure the world exists
    public static World getWorld(String world)
    {
        for(World cur : Bukkit.getWorlds())
        {
            if(cur.getName().equals(world))
                return cur;
        }

        return null;
    }

    public static boolean isDouble(String d)
    {
        try
        {
            Double.parseDouble(d);
            return true;
        }

        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isBoolean(String b)
    {
        try
        {
            Boolean.parseBoolean(b);
            return true;
        }

        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

}
