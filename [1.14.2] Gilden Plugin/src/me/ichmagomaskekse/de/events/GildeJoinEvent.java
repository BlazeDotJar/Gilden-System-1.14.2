package me.ichmagomaskekse.de.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.ichmagomaskekse.de.Gilde;

public class GildeJoinEvent extends Event {
	
	private static HandlerList handlers = new HandlerList();
	
	private Player p = null;
	private Gilde gilde = null;
	private boolean wasSuccessful = false;
	private boolean sendMessage = true;
	
	public GildeJoinEvent(Player p, Gilde gilde, boolean wasSuccessful) {
		this.p = p;
		this.gilde = gilde;
		this.wasSuccessful = wasSuccessful;
	}
	public GildeJoinEvent(Player p, Gilde gilde, boolean wasSuccessful, boolean sendMessage) {
		this.p = p;
		this.gilde = gilde;
		this.wasSuccessful = wasSuccessful;
		this.sendMessage = sendMessage;
	}
	
	public Player getPlayer() {
		return p;
	}
	public Gilde getGilde() {
		return gilde;
	}
	public boolean wasSuccessful() {
		return wasSuccessful;
	}
	public boolean sendMessage() {
		return sendMessage;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
}
