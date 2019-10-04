package me.ichmagomaskekse.de;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.ichmagomaskekse.de.events.GildeJoinEvent;

public class Gilde {
	
	private GildenSystem pl = GildenSystem.getInstance();
	private String name = "Neue Gruppe";
	private String prefix_color = "§a";
	private String suffix_color = "§a";
	
	private HashMap<UUID, GildenRolle> members = new HashMap<UUID, GildenRolle>();
	
	public Gilde() { }
	public Gilde(String name) {
		this.name = name;
		saveData(false);
		Bukkit.broadcastMessage("Gilde wurde erstellt");
	}
	
	//Füge einen Spieler zur Gilde hinzu mit entsprechender Rolle
	public boolean addMember(UUID uuid, GildenRolle rolle) {
		return addMember(uuid, rolle, true);
	}
	public boolean addMember(UUID uuid, GildenRolle rolle, boolean savedata) {
		if(members.containsKey(uuid) && members.get(uuid) != rolle) {
			members.remove(uuid);
			members.put(uuid, rolle);
			if(savedata)saveData();
			return true;
		} else if(members.containsKey(uuid) == false && members.get(uuid) != rolle){
			members.remove(uuid);
			members.put(uuid, rolle);
			if(savedata)saveData();
			Bukkit.getPluginManager().callEvent(new GildeJoinEvent(Bukkit.getPlayer(uuid), this, true));
			return true;
		} else {
			Bukkit.getPluginManager().callEvent(new GildeJoinEvent(Bukkit.getPlayer(uuid), this, false));
			return false;
		}
	}
	
	//Entfernt eien Spieler aus der Gilde
	public boolean removeMember(Player p) {
		if(members.containsKey(p.getUniqueId()) == false) return false; 
		else members.remove(p.getUniqueId());
		return true;
	}
	/* Löscht die gespeicherten Dateien dieser Gilde */
	private boolean delete_success = false;
	public boolean deleteData() {
		delete_success = FileSystem.deleteFile(GildenSystem.gilden_home+getName()+"\\save.yml");
		delete_success = FileSystem.deleteDirectory(GildenSystem.gilden_home+getName()+"\\");
		return delete_success;
	}
	/* Speichert die Gilden Information in Dateien */
	public boolean saveData(boolean override) {
		return FileSystem.saveGilde(this, override);
	}
	public boolean saveData() {
		return FileSystem.saveGilde(this, true);
	}
	
	public String getName() {
		return name;
	}
	public String getPrefixColor() {
		return prefix_color;
	}
	public String getSuffixColor() {
		return suffix_color;
	}
	public HashMap<UUID, GildenRolle> getMembers() {
		return members;
	}
	public HashMap<String, String> getConvertedMembersAsHashMap() {
		HashMap<String, String> mems = new HashMap<String, String>();
		for(UUID uuid : members.keySet()) {
			mems.put(uuid.toString(), members.get(uuid).toString());
		}
		return mems;
	}
	public ArrayList<String> getConvertedMembersAsArrayList() {
		ArrayList<String> mems = new ArrayList<String>();
		for(UUID uuid : members.keySet()) mems.add(uuid.toString()+"/"+members.get(uuid).toString());
		return mems;
	}
	public void translateData(HashMap<String, String> data) {
		this.prefix_color = data.get("prefix color");
		this.suffix_color = data.get("suffix color");
		this.name = data.get("name");
	}
	
	
}
