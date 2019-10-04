package me.ichmagomaskekse.de;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.ichmagomaskekse.de.events.GildeCreateEvent;
import me.ichmagomaskekse.de.listener.GildenListener;

public class GildenSystem extends JavaPlugin implements Listener {
	
	public static final String gilden_home = "\\plugins\\Gilden\\";
	
	/* Instanzen */
	public GildenManager gilden_manager = null;
	
	private static GildenSystem pl = null;
	public static GildenSystem getInstance() { return pl; }
	
	public GildenSystem() {
		return;
	}
	
	@Override
	public void onEnable() {
		preInit();
		init();
		postInit();
		super.onEnable();
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}
	
	private void preInit() {
		pl= this;
		gilden_manager = new GildenManager();
	}
	private void init() {
		// TODO Auto-generated method stub
		
	}
	private void postInit() {
		this.getServer().getPluginManager().registerEvents(this, this);
		new GildenListener();
	}
	
	public boolean createNewGilde(String name) {
		File file = new File(gilden_home+name+"\\save.yml");
		if(file.exists()) {
			System.out.println("File: "+file.getAbsolutePath());
			return false;
		} else return gilden_manager.registerGilde(new Gilde(name));
	}
	
	private boolean delete_success = false;
	public boolean deleteGilde(String name) {
		Gilde gilde = gilden_manager.getGilde(name);
		if(gilde != null) {
			delete_success = gilde.deleteData();
			if(delete_success) gilden_manager.unregisterGilde(gilde);
			return delete_success;
		} else return false;
	}
	
	
	//events
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		GildeCreateEvent event = null;
		if(createNewGilde(e.getBlock().getType().name())) {
			e.getPlayer().sendMessage("§aDie Gilde '"+e.getBlock().getType().name()+"' wurde erstellt");
			Gilde gilde = gilden_manager.getGilde(e.getBlock().getType().name());
			if(gilde != null && gilde.addMember(e.getPlayer().getUniqueId(), GildenRolle.LEADER)) 
				e.getPlayer().sendMessage("Du bist nun Leader von "+e.getBlock().getType().name());
			
			event = new GildeCreateEvent(e.getPlayer(), e.getBlock().getType().name(), true);
			Bukkit.getPluginManager().callEvent(event);
		}else {
			e.getPlayer().sendMessage("§cDie Gilde '"+e.getBlock().getType().name()+"' konnte nicht erstellt werden, da vorhanden");
			Gilde gilde = gilden_manager.getGilde(e.getBlock().getType().name());
			for(String s : gilde.getConvertedMembersAsHashMap().keySet()) e.getPlayer().sendMessage("Spieler: "+s+" Rolle: "+gilde.getConvertedMembersAsHashMap().get(s));
		}
	}
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(deleteGilde(e.getBlock().getType().name())) {
			e.getPlayer().sendMessage("§eDie Gilde '"+e.getBlock().getType().name()+"' wurde gelöscht");
		}else e.getPlayer().sendMessage("§cDie Gilde '"+e.getBlock().getType().name()+"' konnte nicht gelöscht werden, da nicht vorhanden");

	}
	
	@EventHandler
	public void onCreate(GildeCreateEvent e) {
		e.getCreator().sendMessage("§bEVENT: §fDas Event funktioniert!");
		if(e.wasSuccessful() && e.getGildenName().equals("Unbekannte Gilde") == false) {
			e.getCreator().sendMessage("§bEVENT: §fDie Gilde "+e.getGildenName()+" wurde erstellt");
			if(e.getCreator() instanceof Player) {
				//"§6§LDU BIST: §e"+this.gilden_manager.getGilde(e.getGildenName()).getMembers().get(((Player)e.getCreator()).getUniqueId()).toString()
				((Player)e.getCreator()).setPlayerListHeader("§dInfos:\n§6§L DEIN RANG: §e"+e.getGildenName());
				((Player)e.getCreator()).setPlayerListName("§8[§5"+e.getGildenName()+"§8] §4Admin §7"+((Player)e.getCreator()).getName());
				Gilde gilde = gilden_manager.getGilde(e.getGildenName());
				if(gilde == null) e.getCreator().sendMessage("== null");
				String uuid = ((Player)e.getCreator()).getUniqueId().toString();
				((Player)e.getCreator()).setPlayerListFooter("§6§LDU BIST: §e"+gilde.getMembers().get(UUID.fromString(uuid)).toString()+"\n§6§lDEIN KONTOSTAND: §e493\n§6§lDEINE WARNUNGEN: §e0\n§6§LDEINE KILLS: §e31\n§6§lDEINE DEATHS: §e4");
			}
		}
	}
	
	
}
