package io.github.JoltMuz.DodgeBolt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class points implements CommandExecutor
{
	public static HashMap<String,Integer> points = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) 
	{
		if (sender.isOp())
		{
			Bukkit.broadcastMessage(main.prefix + ChatColor.BLUE + ChatColor.BOLD.toString() + "The Dodgebolt Event has ended!");
	        List<String> keys = points.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).limit(3).map(Map.Entry::getKey).collect(Collectors.toList());
	        if (keys.size() > 0)
	        {
	            Bukkit.broadcastMessage(main.prefix + ChatColor.AQUA + "♕ #1 》" + keys.get(0));
	        }
	        if (keys.size() > 1)
	        {
	            Bukkit.broadcastMessage(main.prefix + ChatColor.YELLOW + "♖ #2 》" + keys.get(1));
	        }
	        if (keys.size() > 2)
	        {
	            Bukkit.broadcastMessage(main.prefix + ChatColor.WHITE + "♘ #3 》" + keys.get(2));
	        }
	        if (db.gameRunning)
	        {
	        	db.gameRunning = false;
	        }
	        for (Player p : Bukkit.getOnlinePlayers())
	        {
	        	if (p.getPlayerListName().contains(ChatColor.DARK_GRAY.toString()))
	        	{
	        	//	p.setPlayerListName(p.getName());
	        	}
	        	db.teams.clear();
	        	db.winners.clear();
	        	db.remaining.clear();
	        	
	        	
	        }
		}
		else
		{
			sender.sendMessage(main.prefix + ChatColor.RED + "You must be operator.");
		}
		return true;
	}
	

}
