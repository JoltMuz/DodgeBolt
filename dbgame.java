package io.github.JoltMuz.DodgeBolt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class dbgame implements Listener
{
	@EventHandler
	public void Freeze(PlayerMoveEvent e)
	{
		if (db.startingCooldown && (db.currentTeam1.contains(e.getPlayer()) || db.currentTeam2.contains(e.getPlayer())))
		{
			Bukkit.broadcastMessage("move debug");
			if (e.getPlayer().getVelocity().getX() > 0 || e.getPlayer().getVelocity().getZ() > 0)
			{
				
				e.setCancelled(true);
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
							Bukkit.broadcastMessage(main.prefix + damagee.getPlayerListName() + ChatColor.RED + " was eliminated by " + damager.getPlayerListName());
							damagee.damage(20,damager);
							damagee.setPlayerListName(ChatColor.DARK_GRAY + damagee.getName());
							points.points.put(damager.getName(), points.points.get(damager.getName()) + 2);
							damager.sendMessage(main.prefix + ChatColor.DARK_GREEN + "Your points: " + ChatColor.GREEN + points.points.get(damager.getName()));
							damagee.getInventory().clear();
							
						}
						if (db.currentTeam2.contains(damager) && db.currentTeam1remaining.contains(damagee))
						{
							db.currentTeam1remaining.remove(damagee);
							Bukkit.broadcastMessage(main.prefix + damagee.getPlayerListName() + ChatColor.RED + " was eliminated by " + damager.getPlayerListName());
							damagee.damage(30, damager);
							damagee.setPlayerListName(ChatColor.DARK_GRAY + damagee.getName());
							points.points.put(damager.getName(), points.points.get(damager.getName()) + 2);
							damager.sendMessage(main.prefix + ChatColor.DARK_GREEN + "Your points: "+ ChatColor.GREEN + points.points.get(damager.getName()));
							damagee.getInventory().clear();
						}
						if (db.currentTeam1remaining.size() == 0 )
						{
							Bukkit.broadcastMessage(main.prefix + ChatColor.AQUA + "Winners: " + ChatColor.GREEN +  db.currentTeam2Players);
							for (Player p : db.currentTeam2)
							{
								db.winners.add(p);
								points.points.put(p.getName(), points.points.get(p.getName()) + 5);
								p.sendMessage(main.prefix + ChatColor.DARK_GREEN + "Your points: "+ ChatColor.GREEN + points.points.get(p.getName()));
								p.setHealth(0);
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
						if (db.currentTeam2remaining.size() == 0 )
						{
							Bukkit.broadcastMessage(main.prefix + ChatColor.AQUA + "Winners: " + ChatColor.GREEN + db.currentTeam1Players);	
							for (Player p : db.currentTeam1)
							{
								points.points.put(p.getName(), points.points.get(p.getName()) + 5);
								p.sendMessage(main.prefix + ChatColor.DARK_GREEN + "Your points: " + ChatColor.GREEN + points.points.get(p.getName()));
								db.winners.add(p);
								p.setHealth(0);
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
}
