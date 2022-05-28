package io.github.JoltMuz.DodgeBolt;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.Wool;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

public class db implements CommandExecutor {
	
	public static ArrayList<Player> winners = new ArrayList<>();
	public static ArrayList<Player> remaining = new ArrayList<>();
	public static ArrayList<ArrayList<Player>> teams = new ArrayList<>();
	public static ArrayList<Player> currentTeam1 = new ArrayList<>();
	public static ArrayList<Player> currentTeam1remaining = new ArrayList<>();
	public static String currentTeam1Players = "";
	public static ArrayList<Player> currentTeam2 = new ArrayList<>();
	public static ArrayList<Player> currentTeam2remaining = new ArrayList<>();
	public static String currentTeam2Players = "";
	public static boolean startingCooldown = false;
	public static boolean gameRunning = false;
	public static int randomMapName = 0;
	public static Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) 
	{
		if (sender.isOp())
		{
			if (args.length > 0)
			{
				if (args[0].equalsIgnoreCase("teams"))
				{
					if (winners.isEmpty())
					{
						remaining.addAll(Bukkit.getOnlinePlayers());
						for (Player p : remaining)
						{
							points.points.put(p.getName(), 0);
						}
					}
					else
					{
						remaining.addAll(winners);
					}
				
					while (!remaining.isEmpty())
					{
						ArrayList<Player> temp = new ArrayList<>();
						for (int in = 0; in < 4; in++)
						{
							remaining.get(0).sendMessage(main.prefix + ChatColor.DARK_AQUA + "Your teammates for next round: ");
							temp.add(remaining.get(0));
							remaining.remove(0);
						}
						teams.add(temp);
						
						for (Player p : temp)
						{
							p.sendMessage(main.prefix + ChatColor.AQUA + temp.get(0).getName() + " " + temp.get(1).getName()  + " " + temp.get(2).getName()  + " " + temp.get(3).getName());
						}
					}
				}
				else if (args[0].equalsIgnoreCase("nextmatch"))
				{
					Location loc1 = dbmap.team1Loc;
					Location loc2 = dbmap.team2Loc;
					ChatColor team1color = ChatColor.WHITE;
					ChatColor team2color = ChatColor.WHITE;
					
					Color armorColor1 = Color.fromRGB(0,0,0);
					Color armorColor2 = Color.fromRGB(0,0,0);
					
				
					if (loc1.getBlock().getType() == Material.WOOL)
					{
						
						Wool wool = new Wool(loc1.getBlock().getType(), loc1.getBlock().getData());
						DyeColor woolColor = wool.getColor();
						
				        switch (woolColor) 
				        {
				            case RED:
				            	team1color = ChatColor.RED;
								armorColor1 = Color.fromRGB(255,0,0);
				                break;
				            case BLUE:
				            	team1color = ChatColor.BLUE;
								armorColor1 = Color.fromRGB(0,0,255);
				                break;
				            case BLACK:
				            	team1color = ChatColor.BLACK;
								armorColor1 = Color.fromRGB(0,0,0);
				            	break;
				            case SILVER:
				            	team1color = ChatColor.BLACK;
								armorColor1 = Color.fromRGB(200,200,200);
				            	break;
				            case GREEN:
				            	team1color = ChatColor.DARK_GREEN;
				            	armorColor1 = Color.fromRGB(0,255,0);
				            	break;
				            case LIME:
				            	team1color = ChatColor.GREEN;
				            	armorColor1 = Color.fromRGB(125,255,0);
				            	break;
				            case LIGHT_BLUE:
				            	team1color = ChatColor.AQUA;
				            	armorColor1 = Color.fromRGB(0,255,255);
				            	break;
				            case ORANGE:
				            	team1color = ChatColor.GOLD;
				            	armorColor1 = Color.fromRGB(255,125,255);
				            	break;
				            case MAGENTA:
				            	team1color = ChatColor.DARK_PURPLE;
				            	armorColor1 = Color.fromRGB(125,0,255);
				            	break;
				            case PURPLE:
				            	team1color = ChatColor.DARK_PURPLE;
				            	armorColor1 = Color.fromRGB(255,125,255);
				            	break;
				            case PINK:
				            	team1color = ChatColor.LIGHT_PURPLE;
				            	armorColor1 = Color.fromRGB(255,0,255);
				            	break;
				            case YELLOW:
				            	team1color = ChatColor.YELLOW;
				            	armorColor1 = Color.fromRGB(255,255,0);
				            	break;
				            case CYAN:
				            	team1color = ChatColor.DARK_AQUA;
				            	armorColor1 = Color.fromRGB(0,200,200);
				            	break;
				            	
				        }
						
						
					}
					else
					{
						sender.sendMessage(main.prefix + ChatColor.RED + "(!) The spawn block for team 1 is not wool!");
					}
					if (loc2.getBlock().getType() == Material.WOOL)
					{
						Bukkit.broadcastMessage("loc2 is also wool");
						Wool wool = (Wool) loc2.getBlock();
						DyeColor woolColor = wool.getColor();
				        switch (woolColor) 
				        {
				        case RED:
			            	team1color = ChatColor.RED;
							armorColor1 = Color.fromRGB(255,0,0);
			                break;
			            case BLUE:
			            	team1color = ChatColor.BLUE;
							armorColor1 = Color.fromRGB(0,0,255);
			                break;
			            case BLACK:
			            	team1color = ChatColor.BLACK;
							armorColor1 = Color.fromRGB(0,0,0);
			            	break;
			            case SILVER:
			            	team1color = ChatColor.BLACK;
							armorColor1 = Color.fromRGB(200,200,200);
			            	break;
			            case GREEN:
			            	team1color = ChatColor.DARK_GREEN;
			            	armorColor1 = Color.fromRGB(0,255,0);
			            	break;
			            case LIME:
			            	team1color = ChatColor.GREEN;
			            	armorColor1 = Color.fromRGB(125,255,0);
			            	break;
			            case LIGHT_BLUE:
			            	team1color = ChatColor.AQUA;
			            	armorColor1 = Color.fromRGB(0,255,255);
			            	break;
			            case ORANGE:
			            	team1color = ChatColor.GOLD;
			            	armorColor1 = Color.fromRGB(255,125,255);
			            	break;
			            case MAGENTA:
			            	team1color = ChatColor.DARK_PURPLE;
			            	armorColor1 = Color.fromRGB(125,0,255);
			            	break;
			            case PURPLE:
			            	team1color = ChatColor.DARK_PURPLE;
			            	armorColor1 = Color.fromRGB(255,125,255);
			            	break;
			            case PINK:
			            	team1color = ChatColor.LIGHT_PURPLE;
			            	armorColor1 = Color.fromRGB(255,0,255);
			            	break;
			            case YELLOW:
			            	team1color = ChatColor.YELLOW;
			            	armorColor1 = Color.fromRGB(255,255,0);
			            	break;
			            case CYAN:
			            	team1color = ChatColor.DARK_AQUA;
			            	armorColor1 = Color.fromRGB(0,200,200);
			            	break;
				        }
						
						
					}
					else
					{
						sender.sendMessage(main.prefix + ChatColor.RED + "(!) The spawn block for team 2 is not wool!");
					}
					if (teams.isEmpty())
					{
						sender.sendMessage(main.prefix + ChatColor.RED + "No complete teams found.");
					}
					else
					{
						
						if (teams.size() % 2 > 0)
						{
							sender.sendMessage(main.prefix + ChatColor.RED + "Warning! There is odd number of teams");
						}
						currentTeam1Players = team1color + "";
						currentTeam2Players = team2color + "";
						
						for (Player p : teams.get(0))
						{
							if (scoreboard.getTeam(p.getName()) == null);
							{
								scoreboard.registerNewTeam(p.getName());
								scoreboard.getTeam(p.getName()).addPlayer(p);
								p.sendMessage("Debug message: you're in scoreboard team: so you should have color on your nametag ");
							}
							scoreboard.getTeam(p.getName()).setPrefix("§4");
							
							ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
							LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta(); 
							meta.setColor(armorColor1);
							chestplate.setItemMeta(meta);
							p.getInventory().setChestplate(chestplate);
							
							ItemStack bow = new ItemStack(Material.BOW);
							bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
							p.getInventory().addItem(bow);
							
							p.setCustomName(team1color + p.getName());
							p.teleport(loc1);
							remaining.remove(p);
							currentTeam1.add(p);
							currentTeam1remaining.add(p);
							currentTeam1Players = currentTeam1Players + p.getName() + " ";
						}
						currentTeam1.get(0).teleport(loc1.add(new Location(((Player)sender).getWorld(),7,0,3)));
						currentTeam1.get(1).teleport(loc1.add(new Location(((Player)sender).getWorld(),7,0,-3)));
						currentTeam1.get(2).teleport(loc1.add(new Location(((Player)sender).getWorld(),4,0,8)));
						currentTeam1.get(3).teleport(loc1.add(new Location(((Player)sender).getWorld(),4,0,-8)));
						
						teams.remove(0);
						if (teams.size() > 0)
						{
							for (Player p : teams.get(0))
							{
								if (!scoreboard.getTeams().contains(p.getName()))
								{
									scoreboard.registerNewTeam(p.getName());
									scoreboard.getTeam(p.getName()).addPlayer(p);
								}
								scoreboard.getTeam(p.getName()).setPrefix(team2color.toString());
								
								ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
								LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta(); 
								meta.setColor(armorColor2);
								chestplate.setItemMeta(meta);
								p.getInventory().setChestplate(chestplate);
								
								ItemStack bow = new ItemStack(Material.BOW);
								bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
								p.getInventory().addItem(bow);
								
								p.setCustomName(team2color + p.getName());
								p.teleport(loc2);
								remaining.remove(p);
								currentTeam2.add(p);
								currentTeam2remaining.add(p);
								currentTeam2Players = currentTeam2Players + p.getName() + " ";
							}
							currentTeam2.get(0).teleport(loc2.add(new Location(((Player)sender).getWorld(),-7,0,3)));
							currentTeam2.get(1).teleport(loc2.add(new Location(((Player)sender).getWorld(),-7,0,-3)));
							currentTeam2.get(2).teleport(loc2.add(new Location(((Player)sender).getWorld(),-4,0,8)));
							currentTeam2.get(3).teleport(loc2.add(new Location(((Player)sender).getWorld(),-4,0,-8)));
							teams.remove(0);
						}
						
						if (remaining.size() < 4)
						{
							sender.sendMessage(main.prefix + ChatColor.YELLOW + "(!) This should be the last game of this round.");
						}
						
						Bukkit.broadcastMessage(main.prefix + currentTeam1Players);
						Bukkit.broadcastMessage(main.prefix + ChatColor.GOLD + "VS");
						Bukkit.broadcastMessage(main.prefix + currentTeam2Players);
						startingCooldown = true;
						
						new BukkitRunnable()
                        {
							int timer = 5;
							
                            @Override
                            public void run()
                            {
                            	
                            	Bukkit.broadcastMessage(main.prefix + ChatColor.YELLOW + timer);
                            	timer = timer -1;
                              if (timer == 0)
                              {
                            	  this.cancel();
                            	  Bukkit.broadcastMessage(main.prefix + ChatColor.GOLD + "Start!");
                            	  gameRunning = true;
                            	  startingCooldown = false;
                              }
                            }
                        }.runTaskTimer(main.plugin,0L,20L);
						
					}
				}
				else
				{
					sender.sendMessage(main.prefix + ChatColor.RED + "Usage: /db [teams/nextmatch]");
				}
			}
			else
			{
				sender.sendMessage(main.prefix + ChatColor.RED + "Usage: /db [teams/nextmatch]");
			}
		}
		else
		{
			sender.sendMessage(main.prefix + ChatColor.RED + "This command requires operator");
		}
		return true;
	}

}
