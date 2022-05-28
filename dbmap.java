package io.github.JoltMuz.DodgeBolt;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.Wool;

public class dbmap implements CommandExecutor 
{
	public static Location team1Loc;
	public static Location team2Loc;

	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) 
	{
		if (sender.isOp() && sender instanceof Player)
		{
			if (args.length > 0)
			{
				if (args[0].equalsIgnoreCase("set"))
				{
					if (args.length == 7)
					{
						try
						{
							int x1 = Integer.parseInt(args[1]);
							int y1 = Integer.parseInt(args[2]);
							int z1 = Integer.parseInt(args[3]);
							int x2 = Integer.parseInt(args[4]);
							int y2 = Integer.parseInt(args[5]);
							int z2 = Integer.parseInt(args[6]);
							team1Loc = new Location(((Player) sender).getWorld(),x1,y1,z1);
							team2Loc = new Location(((Player) sender).getWorld(),x2,y2,z2);
							
							sender.sendMessage(main.prefix + ChatColor.DARK_GREEN + "Team 1 Spawn: " + ChatColor.GREEN + x1 + " " + y1 + " " + z1);
							sender.sendMessage(main.prefix + ChatColor.DARK_GREEN + "Team 2 Spawn: " + ChatColor.GREEN + x2 + " " + y2 + " " + z2);
							
						
						}
						catch (NumberFormatException e)
						{
							sender.sendMessage(main.prefix + ChatColor.RED + "Coordinates must be numbers");
						}
					}	
					else
					{
						sender.sendMessage(main.prefix + ChatColor.RED + "Usage: /dbmap set [x1 y1 z1] [x2 y2 z2]");
					}
				}
			
				else if (args[0].equalsIgnoreCase("clear"))
				{
					
					team1Loc = new Location(((Player)sender).getWorld(),0,0,0);
					team2Loc = new Location(((Player)sender).getWorld(),0,0,0);
					sender.sendMessage(main.prefix + ChatColor.GREEN + "Cleared Coordinates.");
						
				}
				
				else if (args[0].equalsIgnoreCase("list"))
				{
					if (team1Loc != null && team2Loc != null)
					{
						sender.sendMessage(main.prefix + ChatColor.DARK_GREEN + "Team 1 Coordinate: " + ChatColor.GREEN + team1Loc.getX() + " " + team1Loc.getY() + " " + team1Loc.getZ());
						sender.sendMessage(main.prefix + ChatColor.DARK_GREEN + "Team 2 Coordinate: " + ChatColor.GREEN + team2Loc.getX() + " " + team2Loc.getY() + " " + team2Loc.getZ());
					}
					else
					{
						sender.sendMessage(main.prefix + ChatColor.RED + "The Locations have not been set (/dbmap set [x1 y1 z1] [x2 y2 z2])" );
					}
				}
				else
				{
					sender.sendMessage(main.prefix + ChatColor.RED + "Usage: /dbmap [set/clear/list] [x1 y1 z1] [x2 y2 z2]");
				}
			}
			else
			{
				sender.sendMessage(main.prefix + ChatColor.RED + "Usage: /dbmap [set/clear/list] [name] [x1 y1 z1] [x2 y2 z2]");
			}
		
		}
		else
		{
			sender.sendMessage(main.prefix + ChatColor.RED + "This command requires operator player.");
		}
		return true;
	}
}
