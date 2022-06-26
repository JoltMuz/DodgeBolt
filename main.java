package io.github.JoltMuz.DodgeBolt;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin
{
	public static Plugin plugin;
	@Override
	public void onEnable()
	{
		plugin = this;
		this.getCommand("dbmap").setExecutor(new dbmap());
		this.getCommand("db").setExecutor(new db());
		this.getCommand("dbend").setExecutor(new points());
		this.getCommand("dbkmsg").setExecutor(new dbkmsg());
		getServer().getPluginManager().registerEvents(new dbgame(), this);
	}
	@Override
	public void onDisable()
	{
		db.gameRunning = false;
		db.currentTeam1.clear();
		db.currentTeam2.clear();
		db.currentTeam2Players = "";
		db.currentTeam1Players = "";
		db.currentTeam1remaining.clear();
	}
	
	public static String prefix = ChatColor.BLUE + "DodgeBolt" + ChatColor.DARK_GRAY + " 》 ";
}
