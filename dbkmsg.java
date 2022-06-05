package io.github.JoltMuz.DodgeBolt;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class dbkmsg implements CommandExecutor 
{
	public static boolean enabled = false;
	public static HashMap<String,String> killmessages = new HashMap<>();
	public static ArrayList<String> setMessages = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) 
	{
		if (sender.isOp())
		{
			if (args.length > 0)
			{
				if (args[0].equalsIgnoreCase("help"))
				{
					sender.sendMessage(main.prefix + ChatColor.GOLD  + ChatColor.UNDERLINE + "/dbkmsg or /dbkillmessage:");
					sender.sendMessage(main.prefix + ChatColor.YELLOW + "/kmsg [add/remove/list/set/enable/disable]");
					sender.sendMessage(main.prefix + ChatColor.GOLD  + ChatColor.UNDERLINE + "Add [name] [message]:");
					sender.sendMessage(main.prefix + ChatColor.GOLD + "Description:" + ChatColor.YELLOW + " Adds the message to the list.");
					sender.sendMessage(main.prefix + ChatColor.GOLD + "Example:" + ChatColor.YELLOW +  "/kmsg add JoltKillMsg was jolted by");
					sender.sendMessage(main.prefix + ChatColor.GOLD  + ChatColor.UNDERLINE + "remove [name]:");
					sender.sendMessage(main.prefix + ChatColor.GOLD + "Description: " + ChatColor.YELLOW + " Removes the message from the list.");
					sender.sendMessage(main.prefix + ChatColor.GOLD + "Example:" + ChatColor.YELLOW + " /killmessage remove JoltKillMessage");
					sender.sendMessage(main.prefix + ChatColor.GOLD  + ChatColor.UNDERLINE + "set [name]");
					sender.sendMessage(main.prefix + ChatColor.GOLD + "Description:" + ChatColor.YELLOW + " Set messages from list to be active.");
					sender.sendMessage(main.prefix + ChatColor.YELLOW + "(Can be multple, chooses randomly on death)");
					sender.sendMessage(main.prefix + ChatColor.GOLD + "Example:" + ChatColor.YELLOW + " /killmessage set JoltKillMsg OtherKillMsg");
					sender.sendMessage(main.prefix + ChatColor.GOLD  + ChatColor.UNDERLINE + "list");
					sender.sendMessage(main.prefix + ChatColor.GOLD + "Description:" + ChatColor.YELLOW + " Shows the list of kill messages.");
					sender.sendMessage(main.prefix + ChatColor.GOLD + "Example:"+ ChatColor.YELLOW + " /killmessage list");
					sender.sendMessage(main.prefix + ChatColor.GOLD  + ChatColor.UNDERLINE + "enable or disable");
					sender.sendMessage(main.prefix + ChatColor.GOLD + "Description:"+ ChatColor.YELLOW + " Enables or disables kill messages.");
					sender.sendMessage(main.prefix + ChatColor.GOLD + "Example:"+ ChatColor.YELLOW + " /killmessage disable /killmessage enable");
					if (!enabled)
					{
						sender.sendMessage(main.prefix + ChatColor.RED + "This is currently disabled.");
					}
					
				}
				if (args[0].equalsIgnoreCase("enable"))
				{
					if (!enabled)
					{
						sender.sendMessage(main.prefix + ChatColor.GREEN + "Enabled.");
						enabled = true;
					}
					else
					{
						sender.sendMessage(main.prefix + ChatColor.RED + "Already enabled.");
					}
					
				}
				if (args[0].equalsIgnoreCase("disable"))
				{
					if (enabled)
					{
						sender.sendMessage(main.prefix + ChatColor.GREEN + "Disabled.");
						enabled = false;
					}
					else
					{
						sender.sendMessage(main.prefix + ChatColor.RED + "Already disabled.");
					}
				}
				if (args[0].equalsIgnoreCase("add"))
				{
					if (args.length > 2)
					{
						StringBuilder msg = new StringBuilder();
						
						for (int i = 2; i < args.length; i++)
						{
							msg.append(args[i]);
							msg.append(" ");
						}
						killmessages.put(args[1], msg.toString());
						sender.sendMessage(main.prefix + ChatColor.GREEN + "Added kill message:");
						sender.sendMessage(main.prefix + ChatColor.GOLD + args[1] + " : " + ChatColor.YELLOW + msg.toString());
					}
					else
					{
						sender.sendMessage(main.prefix + ChatColor.RED + "Usage: /kmsg add [name] [was whatevered by]");
					}
				}
				if (args[0].equalsIgnoreCase("remove"))
				{
					if (args.length > 1)
					{
						if (killmessages.containsKey(args[1]))
						{
							killmessages.remove(args[1]);
							if (setMessages.contains(args[1]))
							{
								setMessages.remove(args[1]);
							}
							sender.sendMessage(main.prefix + ChatColor.GREEN + "Removed kill message: " + args[1]);
						}
						else
						{
							sender.sendMessage(main.prefix + ChatColor.RED + "There is no kill message with that name.");
						}
					}
					else
					{
						sender.sendMessage(main.prefix + ChatColor.RED + "Usage: /killmessage remove [name]");
					}
				}
				if (args[0].equalsIgnoreCase("list"))
				{
					sender.sendMessage(main.prefix + ChatColor.GOLD + "Full List:");
					sender.sendMessage(ChatColor.YELLOW + killmessages.toString());
					sender.sendMessage(main.prefix + ChatColor.GOLD + "Active Messages");
					for (String msg : setMessages)
					{
						sender.sendMessage(ChatColor.DARK_RED + "killed " + ChatColor.RED +  msg + ChatColor.DARK_RED + "killer");
					}
				}
				if (args[0].equalsIgnoreCase("set"))
				{
					if (args.length > 1)
					{
						setMessages.clear();
						for (int i = 1; i < args.length; i++)
						{
							if (killmessages.containsKey(args[i]))
							{
								setMessages.add(killmessages.get(args[i]));
								sender.sendMessage(main.prefix + ChatColor.GREEN + "Set " + args[i] + ".");
							}
							else
							{
								sender.sendMessage(main.prefix + ChatColor.RED + "List does not contain " + args[i]);
							}
						}
					}
					else
					{
						sender.sendMessage(main.prefix + ChatColor.RED + "Usage: /killmessage set [name] [name]...");
					}
					
				}
			}
			else
			{
				sender.sendMessage(main.prefix + ChatColor.RED + "Try /dbkmsg help");
			}
			
		}
		else
		{
			sender.sendMessage(main.prefix + ChatColor.RED + "You must be operator to execute this command.");
		}
		return true;
	}
}
