package io.github.JoltMuz.DodgeBolt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class dbgame implements Listener {

    public static HashMap<String, UUID> uuids = new HashMap<>();

    @EventHandler
    public void Freeze(PlayerMoveEvent e) {
        if (db.startingCooldown && (db.currentTeam1.contains(e.getPlayer()) || db.currentTeam2.contains(e.getPlayer()))) {
            if (e.getPlayer().getVelocity().getX() > -1 || e.getPlayer().getVelocity().getZ() > -1) {
                Player player = e.getPlayer();
                Location location = player.getLocation();
                player.teleport(location);
            }
        }
    }

    @EventHandler
    public void onBow(EntityDamageByEntityEvent e) {
        if (!db.gameRunning && !db.startingCooldown) {
            return;
        }
        if (e.getEntity() instanceof Player) {
            Player damagee = (Player) e.getEntity();

            if (e.getDamager() instanceof Player) {
                Player damager = (Player) e.getDamager();

                // Can not hit team mates.
                if (areTeamMates(damager, damagee)) {
                    e.setCancelled(true);
                    return;
                }
            }

            if (e.getDamager() instanceof Arrow && ((Arrow) e.getDamager()).getShooter() instanceof Player) {
                Player damager = (Player) ((Arrow) e.getDamager()).getShooter();

                if (db.currentTeam1.contains(damager) && db.currentTeam1.contains(damagee)) {
                    e.setCancelled(true);
                }

                if (db.currentTeam2.contains(damager) && db.currentTeam2.contains(damagee)) {
                    e.setCancelled(true);
                }

                if (areOppositeTeam(damager, damagee)) {
                    // They are shooting on the enemy!
                    if (!isTeamOne(damagee)) {
                        // Team 1 kill
                        killPlayer(damager, damagee, false);
                        Bukkit.broadcastMessage(main.prefix + db.team2color + damagee.getName() + ChatColor.DARK_GRAY + " " + dbkmsg.setMessages.get(new Random().nextInt(dbkmsg.setMessages.size())) + " " + db.team1color + damager.getName());
                    } else {
                        // Team 2 kill
                        killPlayer(damager, damagee, true);
                        Bukkit.broadcastMessage(main.prefix + db.team1color + damagee.getName() + ChatColor.DARK_GRAY + " " + dbkmsg.setMessages.get(new Random().nextInt(dbkmsg.setMessages.size())) + " " + db.team2color + damager.getName());
                    }
                }

                if (db.currentTeam1remaining.size() == 0) {

                    Bukkit.broadcastMessage(main.prefix + ChatColor.AQUA + "Winners: " + ChatColor.GREEN + db.currentTeam2Players);
                    doWinners(db.currentTeam2);
                    clearGame();
                } else if (db.currentTeam2remaining.size() == 0) {

                    Bukkit.broadcastMessage(main.prefix + ChatColor.AQUA + "Winners: " + ChatColor.GREEN + db.currentTeam1Players);
                    doWinners(db.currentTeam1);
                    clearGame();
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Location location = new Location(Bukkit.getWorld("Dodgeball[Mykull]"), 0.5, 57, -23.5);
        e.getPlayer().teleport(location);
    }

    private void killPlayer(Player killer, Player player, boolean teamOne) {
        if (teamOne) {
            db.currentTeam1remaining.remove(player.getUniqueId());
        } else {
            db.currentTeam2remaining.remove(player.getUniqueId());
        }
        player.teleport(player.getWorld().getSpawnLocation());
        killer.sendMessage(main.prefix + ChatColor.DARK_GREEN + "Your Total points: " + ChatColor.GREEN + points.points.get(killer.getName()));
        player.getInventory().clear();
        points.points.put(killer.getName(), points.points.get(killer.getName()) + 2);
    }

    private void doWinners(List<Player> players) {
        for (Player p : players) {
            db.winners.add(p);
            points.points.put(p.getName(), points.points.get(p.getName()) + 5);
            p.sendMessage(main.prefix + ChatColor.DARK_GREEN + "Your Total points: " + ChatColor.GREEN + points.points.get(p.getName()));
            p.teleport(p.getWorld().getSpawnLocation());
            p.setCustomName(p.getName());
            //p.setPlayerListName(p.getName());
            p.getInventory().clear();
        }
    }

    private void clearGame() {
        db.gameRunning = false;
        db.currentTeam1.clear();
        db.currentTeam2.clear();
        db.currentTeam2Players = "";
        db.currentTeam1Players = "";
        db.currentTeam1remaining.clear();
    }

    private List<Player> getOppositeTeam(Player damager) {
        if (db.currentTeam1.contains(damager)) {
            return db.currentTeam1;
        } else if (db.currentTeam2.contains(damager)) {
            return db.currentTeam2;
        }

        return new ArrayList<>();
    }

    private boolean areTeamMates(Player p1, Player p2) {
        if (db.currentTeam1.contains(p1) && db.currentTeam1.contains(p2)) {
            return true;
        }

        return db.currentTeam2.contains(p1) && db.currentTeam2.contains(p2);
    }

    private boolean areOppositeTeam(Player p1, Player p2) {
        if (db.currentTeam1.contains(p1) && db.currentTeam2remaining.contains(p2.getUniqueId())) {
            return true;
        }

        return db.currentTeam2.contains(p1) && db.currentTeam1remaining.contains(p2.getUniqueId());
    }

    private boolean isTeamOne(Player elimated) {
        return db.currentTeam1remaining.contains(elimated.getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        uuids.put(p.getName(), p.getUniqueId());
    }
}
