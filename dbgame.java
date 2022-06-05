package io.github.JoltMuz.DodgeBolt;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class dbgame implements Listener
{
	
	public static HashMap<String,UUID> uuids = new HashMap<>();
	@EventHandler
	public void Freeze(PlayerMoveEvent e)
	{
		if (db.startingCooldown && (db.currentTeam1.contains(e.getPlayer()) || db.currentTeam2.contains(e.getPlayer())))
		{
			if (e.getPlayer().getVelocity().getX() > -1 || e.getPlayer().getVelocity().getZ() > -1)
			{
				Player player = e.getPlayer();
    		    Location location = player.getLocation();
    		    player.teleport(location);
			}
		}
	}
	@EventHandler
	public void onBow(EntityDamageByEntityEvent e)
	{
		if (db.gameRunning || db.startingCooldown)
		{
			if (e.getEntity() instanceof Player)
			{
				Player damagee = (Player) e.getEntity();
				if ( e.getDamager() instanceof Player)
				{
					Player damager = (Player) e.getDamager();
					
					if (db.currentTeam1.contains(damager) && db.currentTeam1.contains(damagee))
					{
						e.setCancelled(true);
					}
					if (db.currentTeam2.contains(damager) && db.currentTeam2.contains(damagee))
					{
						e.setCancelled(true);
					}
				}
				if (e.getDamager() instanceof Arrow)
				{
					if (((Arrow) e.getDamager()).getShooter() instanceof Player)
					{
						Player damager = (Player) ((Arrow) e.getDamager()).getShooter();
						
						if (db.currentTeam1.contains(damager) && db.currentTeam1.contains(damagee))
						{
							e.setCancelled(true);
						}
						if (db.currentTeam2.contains(damager) && db.currentTeam2.contains(damagee))
						{
							e.setCancelled(true);
						}
					
						if (db.currentTeam1.contains(damager) && db.currentTeam2remaining.contains(damagee))
						{
							db.currentTeam2remaining.remove(damagee);
							Bukkit.broadcastMessage(main.prefix + db.team2color + damagee.getName() + ChatColor.DARK_GRAY + " " + dbkmsg.setMessages.get(new Random().nextInt(0,dbkmsg.setMessages.size())) + " " + db.team1color + damager.getName());
							damagee.setPlayerListName(ChatColor.DARK_GRAY + damagee.getName());
							damagee.teleport(damagee.getWorld().getSpawnLocation());
							points.points.put(damager.getName(), points.points.get(damager.getName()) + 2);
							damager.sendMessage(main.prefix + ChatColor.DARK_GREEN + "Your Total points: " + ChatColor.GREEN + points.points.get(damager.getName()));
							damagee.getInventory().clear();
							
						}
						if (db.currentTeam2.contains(damager) && db.currentTeam1remaining.contains(damagee))
						{
							db.currentTeam1remaining.remove(damagee);
							Bukkit.broadcastMessage(main.prefix + db.team1color + damagee.getName() + ChatColor.DARK_GRAY + " " + dbkmsg.setMessages.get(new Random().nextInt(0,dbkmsg.setMessages.size())) + " " + db.team2color + damager.getName());
							damagee.setPlayerListName(ChatColor.DARK_GRAY + damagee.getName());
							damagee.teleport(damagee.getWorld().getSpawnLocation());
							points.points.put(damager.getName(), points.points.get(damager.getName()) + 2);
							damager.sendMessage(main.prefix + ChatColor.DARK_GREEN + "Your Total points: "+ ChatColor.GREEN + points.points.get(damager.getName()));
							damagee.getInventory().clear();
						}
						if (db.currentTeam1remaining.size() == 0 )
						{
							Bukkit.broadcastMessage(main.prefix + ChatColor.AQUA + "Winners: " + ChatColor.GREEN +  db.currentTeam2Players);
							for (Player p : db.currentTeam2)
							{
								db.winners.add(p);
								points.points.put(p.getName(), points.points.get(p.getName()) + 5);
								p.sendMessage(main.prefix + ChatColor.DARK_GREEN + "Your Total points: "+ ChatColor.GREEN + points.points.get(p.getName()));
								p.teleport(p.getWorld().getSpawnLocation());
								p.setCustomName(p.getName());
								p.setPlayerListName(p.getName());
								p.getInventory().clear();
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
							for (Player p : db.currentTeam1)
							{
								points.points.put(p.getName(), points.points.get(p.getName()) + 5);
								p.sendMessage(main.prefix + ChatColor.DARK_GREEN + "Your Total points: " + ChatColor.GREEN + points.points.get(p.getName()));
								db.winners.add(p);
								p.teleport(p.getWorld().getSpawnLocation());
								p.setCustomName(p.getName());
								p.setPlayerListName(p.getName());
								p.getInventory().clear();
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
			}
		}
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent e)
	{
		Player p = e.getPlayer();
		uuids.put(p.getName(), p.getUniqueId());
	}
}
