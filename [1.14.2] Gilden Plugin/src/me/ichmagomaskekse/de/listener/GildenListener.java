package me.ichmagomaskekse.de.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.ichmagomaskekse.de.GildenSystem;
import me.ichmagomaskekse.de.events.GildeCreateEvent;
import me.ichmagomaskekse.de.events.GildeJoinEvent;

public class GildenListener implements Listener {
	
	public GildenListener() {
		GildenSystem.getInstance().getServer().getPluginManager().registerEvents(this, GildenSystem.getInstance());
	}
	
	@EventHandler
	public void onGildeCreate(GildeCreateEvent e) {
		if(e.sendMessage()) {			
			if(e.wasSuccessful()) e.getCreator().sendMessage("§6Du hast die Gilde §b"+e.getGildenName()+" §6erstellt!");
			else e.getCreator().sendMessage("§6Die Gilde §b"+e.getGildenName()+" §6konnte nicht erstellt werden");
		}
	}
	
	@EventHandler
	public void onGildeJoin(GildeJoinEvent e) {
		if(e.sendMessage()) {
			if(e.wasSuccessful()) e.getPlayer().sendMessage("§6Du bist der Gilde §b"+e.getGilde().getName()+" §6beigetreten");
			else e.getPlayer().sendMessage("§6Du konntest der Gilde §b"+e.getGilde().getName()+" §6nicht beitreten");
		}
	}
}
