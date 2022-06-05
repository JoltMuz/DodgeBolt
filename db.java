package io.github.JoltMuz.DodgeBolt;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
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
	public static ArrayList<Player> exceptions = new ArrayList<>();
	public static String currentTeam2Players = "";
	public static boolean startingCooldown = false;
	public static boolean gameRunning = false;
	public static int randomMapName = 0;
	public static Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
	
	public static ChatColor team1color = ChatColor.WHITE;
	public static ChatColor team2color = ChatColor.WHITE;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) 
	{
		if (sender.isOp())
		{
			if (args.length > 0)
			{
				if (args[0].equalsIgnoreCase("points"))
				{
					sender.sendMessage(main.prefix + ChatColor.AQUA + "Points List: ");
	                for (String p : points.points.keySet())
	                {
	                	ChatColor color = ChatColor.DARK_GRAY;
	                	if (winners.contains(Bukkit.getPlayer(p)))
	                	{
	                		color = ChatColor.DARK_AQUA;
	                	}
	                    
	                    sender.sendMessage(main.prefix + color + p + ChatColor.AQUA + " : " + points.points.get(p));
	                }
					
				}
				else if (args[0].equalsIgnoreCase("eliminate"))
				{
					if (args.length > 1)
					{
						if (dbgame.uuids.containsKey(args[1]))
		        		{
							OfflinePlayer p = Bukkit.getOfflinePlayer(dbgame.uuids.get(args[1]));
							if (winners.contains(p))
							{
								winners.remove(p);
								sender.sendMessage(main.prefix + ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + " removed from winners.");
							}
							else
							{
								if (db.currentTeam2remaining.contains(p))
								{
									db.currentTeam2remaining.remove(p);
									Bukkit.broadcastMessage(main.prefix + db.team2color +  p.getName() + ChatColor.DARK_GRAY + dbkmsg.setMessages.get(new Random().nextInt(0,dbkmsg.setMessages.size())) + " void." );
									
								}
								else if (db.currentTeam1remaining.contains(p))
								{
									db.currentTeam1remaining.remove(p);
									Bukkit.broadcastMessage(main.prefix + db.team1color + p.getName() + ChatColor.DARK_GRAY + dbkmsg.setMessages.get(new Random().nextInt(0,dbkmsg.setMessages.size())) + " void.");
								
								}
								else
								{
									sender.sendMessage(main.prefix + ChatColor.RED + "is not in a game or a winner.");
								}
								
								if (db.currentTeam1remaining.size() == 0 )
								{
									Bukkit.broadcastMessage(main.prefix + ChatColor.AQUA + "Winners: " + ChatColor.GREEN +  db.currentTeam2Players);
									for (Player pl : db.currentTeam2)
									{
										db.winners.add(pl);
										points.points.put(pl.getName(), points.points.get(pl.getName()) + 5);
										pl.sendMessage(main.prefix + ChatColor.DARK_GREEN + "Your Total points: "+ ChatColor.GREEN + points.points.get(pl.getName()));
										pl.teleport(pl.getWorld().getSpawnLocation());
										pl.setCustomName(pl.getName());
										pl.setPlayerListName(pl.getName());
										pl.getInventory().clear();
									}
									db.gameRunning = false;
									db.currentTeam1.clear();
									db.currentTeam2.clear();
									db.currentTeam2Players = "";
									db.currentTeam1Players = "";
									db.currentTeam2remaining.clear();
									
									
								}
								else if (db.currentTeam2remaining.size() == 0 )
								{
									Bukkit.broadcastMessage(main.prefix + ChatColor.AQUA + "Winners: " + ChatColor.GREEN + db.currentTeam1Players);	
									for (Player pl : db.currentTeam1)
									{
										db.winners.add(pl);
										points.points.put(pl.getName(), points.points.get(pl.getName()) + 5);
										pl.sendMessage(main.prefix + ChatColor.DARK_GREEN + "Your Total points: "+ ChatColor.GREEN + points.points.get(pl.getName()));
										pl.teleport(pl.getWorld().getSpawnLocation());
										pl.setCustomName(pl.getName());
										pl.setPlayerListName(pl.getName());
										pl.getInventory().clear();
									}
									db.gameRunning = false;
									db.currentTeam1.clear();
									db.currentTeam2.clear();
									db.currentTeam2Players = "";
									db.currentTeam1Players = "";
									db.currentTeam1remaining.clear();
								}
								
							}
							
		        		}
		        		else if (Bukkit.getPlayer(args[1]) != null)
		        		{
		        			Player p = Bukkit.getPlayer(args[1]);
							if (winners.contains(p))
							{
								winners.remove(p);
								sender.sendMessage(main.prefix + ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + " removed from winners.");
							}
							else
							{
								if (db.currentTeam2remaining.contains(p))
								{
									db.currentTeam2remaining.remove(p);
									Bukkit.broadcastMessage(main.prefix + db.team2color +  p.getName() + " " + ChatColor.DARK_GRAY + dbkmsg.setMessages.get(new Random().nextInt(0,dbkmsg.setMessages.size())) + " void." );
									p.setPlayerListName(ChatColor.DARK_GRAY + p.getName());
									p.getInventory().clear();
									p.teleport(p.getWorld().getSpawnLocation());
								}
								else if (db.currentTeam1remaining.contains(p))
								{
									db.currentTeam1remaining.remove(p);
									Bukkit.broadcastMessage(main.prefix + db.team1color + p.getName() + " " + ChatColor.DARK_GRAY + dbkmsg.setMessages.get(new Random().nextInt(0,dbkmsg.setMessages.size())) + " void.");
									p.setPlayerListName(ChatColor.DARK_GRAY + p.getName());
									p.getInventory().clear();
									p.teleport(p.getWorld().getSpawnLocation());
									
								}
								else
								{
									sender.sendMessage(main.prefix + ChatColor.RED + "is not in a game or a winner.");
								}
								
								if (db.currentTeam1remaining.size() == 0 )
								{
									Bukkit.broadcastMessage(main.prefix + ChatColor.AQUA + "Winners: " + ChatColor.GREEN +  db.currentTeam2Players);
									for (Player pl : db.currentTeam2)
									{
										db.winners.add(pl);
										points.points.put(pl.getName(), points.points.get(pl.getName()) + 5);
										pl.sendMessage(main.prefix + ChatColor.DARK_GREEN + "Your Total points: "+ ChatColor.GREEN + points.points.get(pl.getName()));
										pl.teleport(pl.getWorld().getSpawnLocation());
										pl.setCustomName(pl.getName());
										pl.setPlayerListName(pl.getName());
										pl.getInventory().clear();
									}
									db.gameRunning = false;
									db.currentTeam1.clear();
									db.currentTeam2.clear();
									db.currentTeam2Players = "";
									db.currentTeam1Players = "";
									db.currentTeam2remaining.clear();
									
									
								}
								else if (db.currentTeam2remaining.size() == 0 )
								{
									Bukkit.broadcastMessage(main.prefix + ChatColor.AQUA + "Winners: " + ChatColor.GREEN + db.currentTeam1Players);	
									for (Player pl : db.currentTeam1)
									{
										db.winners.add(pl);
										points.points.put(pl.getName(), points.points.get(pl.getName()) + 5);
										pl.sendMessage(main.prefix + ChatColor.DARK_GREEN + "Your Total points: "+ ChatColor.GREEN + points.points.get(pl.getName()));
										pl.teleport(pl.getWorld().getSpawnLocation());
										pl.setCustomName(pl.getName());
										pl.setPlayerListName(pl.getName());
										pl.getInventory().clear();
									}
									db.gameRunning = false;
									db.currentTeam1.clear();
									db.currentTeam2.clear();
									db.currentTeam2Players = "";
									db.currentTeam1Players = "";
									db.currentTeam1remaining.clear();
								}
							}
		        		}
		        		else
		        		{
		        			sender.sendMessage(main.prefix + ChatColor.RED + args[1] + " player not found.");
		        		}
			
					}
					
				}
			
				else if (args[0].equalsIgnoreCase("addwinner"))
				{
					if (args.length > 1)
					{
						if (Bukkit.getPlayer(args[1]) != null)
		        		{
							if (winners.contains(Bukkit.getPlayer(args[1])))
							{
								sender.sendMessage(main.prefix + ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + " already a winner.");
							}
							else
							{
								winners.add(Bukkit.getPlayer(args[1]));
								Bukkit.getPlayer(args[1]).setPlayerListName(Bukkit.getPlayer(args[1]).getName());
								sender.sendMessage(main.prefix + ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + " added to winners.");
							}
							
		        		}
		        		else
		        		{
		        			sender.sendMessage(main.prefix + ChatColor.DARK_RED + args[1] + ChatColor.RED + " player not found.");
		        		}
					}
					else
					{
						sender.sendMessage(main.prefix + ChatColor.RED + "Usage: /db addwinner [player]");
					}
					
				}
				
				else if (args[0].equalsIgnoreCase("exception"))
				{
					if (args.length > 1)
					{
						if (Bukkit.getPlayer(args[1]) != null)
		        		{
							exceptions.add(Bukkit.getPlayer(args[1]));
							sender.sendMessage(main.prefix + ChatColor.DARK_GREEN + args[1] + ChatColor.GREEN + " will not play.");
		        		}
		        		else
		        		{
		        			sender.sendMessage(main.prefix + ChatColor.DARK_RED + args[1] + ChatColor.RED + " player not found.");
		        		}
					}
					else
					{
						sender.sendMessage(main.prefix + ChatColor.RED + "Usage: /db exception [player]");
					}
					
				}
				else if (args[0].equalsIgnoreCase("clearteams"))
				{
					winners.clear();
					remaining.clear();
					teams.clear();
					currentTeam1.clear();
					currentTeam1remaining.clear();
					currentTeam2.clear();
					currentTeam2remaining.clear();
					sender.sendMessage(main.prefix + ChatColor.GREEN + "All teams cleared, you may do /db teams");
					
				}
				else if (args[0].equalsIgnoreCase("teams"))
				{
					if (winners.isEmpty())
					{
						remaining.addAll(Bukkit.getOnlinePlayers());
						for (Player p : exceptions)
						{
							remaining.remove(p);
						}
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
						if (remaining.size() > 3)
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
						else
						{
							sender.sendMessage(main.prefix + ChatColor.RED + remaining.size()+  " players can not enter the game. Minimum team size: 4");
							sender.sendMessage(main.prefix + ChatColor.RED +  " Removed remaining players from the list.");
							remaining.clear();
						}
						
					}
					if (teams.size() % 2 > 0)
					{
						sender.sendMessage(main.prefix + ChatColor.RED + "Warning! There is odd number of teams.");
						sender.sendMessage(main.prefix + ChatColor.RED + "Make sure you have correct number of players.");
						sender.sendMessage(main.prefix + ChatColor.RED + "Do /db clearteams and /db teams again");
					}
				}
				else if (args[0].equalsIgnoreCase("nextmatch"))
				{
					Location loc1 = dbmap.team1Loc;
					Location loc2 = dbmap.team2Loc;
					
					
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
				            	team1color = ChatColor.WHITE;
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
						
						Wool wool = new Wool(loc2.getBlock().getType(), loc2.getBlock().getData());
						DyeColor woolColor = wool.getColor();
				        switch (woolColor) 
				        {
					        case RED:
				            	team2color = ChatColor.RED;
								armorColor2 = Color.fromRGB(255,0,0);
				                break;
				            case BLUE:
				            	team2color = ChatColor.BLUE;
				            	armorColor2 = Color.fromRGB(0,0,255);
				                break;
				            case BLACK:
				            	team2color = ChatColor.BLACK;
				            	armorColor2 = Color.fromRGB(0,0,0);
				            	break;
				            case SILVER:
				            	team2color = ChatColor.WHITE;
				            	armorColor2 = Color.fromRGB(200,200,200);
				            	break;
				            case GREEN:
				            	team2color = ChatColor.DARK_GREEN;
				            	armorColor2 = Color.fromRGB(0,255,0);
				            	break;
				            case LIME:
				            	team2color = ChatColor.GREEN;
				            	armorColor2 = Color.fromRGB(125,255,0);
				            	break;
				            case LIGHT_BLUE:
				            	team2color = ChatColor.AQUA;
				            	armorColor2 = Color.fromRGB(0,255,255);
				            	break;
				            case ORANGE:
				            	team2color = ChatColor.GOLD;
				            	armorColor2 = Color.fromRGB(255,125,255);
				            	break;
				            case MAGENTA:
				            	team2color = ChatColor.DARK_PURPLE;
				            	armorColor2 = Color.fromRGB(125,0,255);
				            	break;
				            case PURPLE:
				            	team2color = ChatColor.DARK_PURPLE;
				            	armorColor2 = Color.fromRGB(255,125,255);
				            	break;
				            case PINK:
				            	team2color = ChatColor.LIGHT_PURPLE;
				            	armorColor2 = Color.fromRGB(255,0,255);
				            	break;
				            case YELLOW:
				            	team2color = ChatColor.YELLOW;
				            	armorColor2 = Color.fromRGB(255,255,0);
				            	break;
				            case CYAN:
				            	team2color = ChatColor.DARK_AQUA;
				            	armorColor2 = Color.fromRGB(0,200,200);
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
						
						
						currentTeam1Players = team1color + "";
						currentTeam2Players = team2color + "";
						
						for (Player p : teams.get(0))
						{
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
						
						World w = ((Player) sender ).getWorld();
						currentTeam1.get(0).teleport(new Location(w,loc1.getX() + 7.5,loc1.getY()+ 1,loc1.getZ() +3.5));
						currentTeam1.get(1).teleport(new Location(w,loc1.getX() + 7.5,loc1.getY()+ 1,loc1.getZ() -2.5));
						currentTeam1.get(2).teleport(new Location(w,loc1.getX() + 4.5,loc1.getY()+ 1,loc1.getZ() +9.5));
						currentTeam1.get(3).teleport(new Location(w,loc1.getX() + 4.5,loc1.getY()+ 1,loc1.getZ() -8.5));
						
						teams.remove(0);
						
						
						if (teams.size() > 0)
						{
							for (Player p : teams.get(0))
							{
								
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
							currentTeam2.get(0).teleport(new Location(w,loc2.getX() -6.5,loc2.getY() +1,loc2.getZ() +3.5));
							currentTeam2.get(1).teleport(new Location(w,loc2.getX() -6.5,loc2.getY() +1,loc2.getZ() -2.5));
							currentTeam2.get(2).teleport(new Location(w,loc2.getX() -3.5,loc2.getY() +1,loc2.getZ() +9.5));
							currentTeam2.get(3).teleport(new Location(w,loc2.getX() -3.5,loc2.getY() +1,loc2.getZ() -8.5));
							teams.remove(0);
						}
						
						if (teams.size() < 3)
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
					sender.sendMessage(main.prefix + ChatColor.RED + "Usage: /db [teams/nextmatch/clearteams/exception/points/addwinner]");
				}
			}
			else
			{
				sender.sendMessage(main.prefix + ChatColor.RED + "Usage: /db [teams/nextmatch/clearteams/exception/points/addwinner]");
			}
		}
		else
		{
			sender.sendMessage(main.prefix + ChatColor.RED + "This command requires operator");
		}
		return true;
	}

}
