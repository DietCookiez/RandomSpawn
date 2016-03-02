package me.dietcookiez.randomspawn;

import me.dietcookiez.randomspawn.utils.C;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * DietCookiez
 * At: 10:25 AM (Mar/02/2016)
 * RandomSpawn
 */

public class Command implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args)
    {
        if(cmd.getName().equalsIgnoreCase("randomspawn"))
        {
            if(sender instanceof Player && !sender.isOp())
            {
                sender.sendMessage(C.Red + "You do not have permission to use this command!");
                return true;
            }

            if(args == null || args.length < 1)
            {
                sender.sendMessage(C.Red + "/randomspawn <player> " + C.Gray + "Checks if a player is on joinlist.");
                return true;
            }

            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);

            if(player == null)
            {
                sender.sendMessage(C.Red + args[1] + C.Gray + " has not joined the server before.");
                return true;
            }

            if(Main.instance.getConfig().getString("players-joined." + player.getUniqueId()) == null)
                sender.sendMessage(C.Red + player.getName() + C.Gray + " is not on the joined list.");
            else
                sender.sendMessage(C.Red + player.getName() + C.Gray + " is on the joined list.");
            return true;
        }

        return false;
    }
}
