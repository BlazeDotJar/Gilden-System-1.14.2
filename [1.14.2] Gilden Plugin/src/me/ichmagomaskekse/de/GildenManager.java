package me.ichmagomaskekse.de;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class GildenManager {
	
	public ArrayList<Gilde> gilden = new ArrayList<Gilde>();
	
	public GildenManager() {
		reloadGilden();
	}
	private Gilde gilde = null;
	public void reloadGilden() {
		gilden.clear();
		FileConfiguration cfg = null;
		File f = new File(GildenSystem.gilden_home);
		File[] files = f.listFiles();
		if(files != null && files.length > 0) {			
			for(File file : files) {
				if(file != null) {					
					File save = new File(file.getAbsoluteFile()+"\\save.yml");
					cfg = YamlConfiguration.loadConfiguration(save);
					System.out.println(save.getAbsolutePath()+" wird geladen");
					gilde = FileSystem.loadGilde(cfg.getString("Gilde.Name"));
					registerGilde(gilde);
				}
			}
		}
		System.out.println("Alle Gilden wurde neu gelesen");
	}
	
	/* - Gilde wird registriert(virtuell)
	 * - Vorteile von virtueller Registrierung:
	 * --> Die Gilden Datei muss nicht ständig geöffnet und ausgelesen werden,
	 *     sobald eine Aktion in dieser Gilde ausgeführt wird(z.B.: wenn man
	 *     abfragen möchte, ob ein bestimmter Spieler bereits ein Member ist)
	 * --> weniger Server-Laggs!*/
	public boolean registerGilde(Gilde gilde) {
		if(gilden.contains(gilde)) {
			System.out.println("Gilde wurde NICHT registriert");
			return false;
		}
		else gilden.add(gilde);
		System.out.println("Gilde wurde registriert");
		return true;
	}
	
	/* - Gilde wird vom virtuellem Speicher entfernt */
	public boolean unregisterGilde(Gilde gilde) {
		if(gilden.contains(gilde) == false) return false;
		else gilden.remove(gilde);
		return true;
	}
	
	/* - Gibt zurück, ob eine Gilde bereits registriert ist */
	public boolean containsGilde(Gilde gilde) {
		System.out.println("ContainsGilde1: "+gilde.getName());
		return containsGilde(gilde.getName());
	}
	public boolean containsGilde(String gilden_name) {
		if(gilden.isEmpty() == false) {
			for(Gilde gilde : gilden) {
				if(gilde.getName().equals(gilden_name)) {
					return true;			
				}
			}
		}
		return false;
	}
	
	public Gilde getGilde(String gilden_name) {
		if(containsGilde(gilden_name)) for(Gilde gilde : gilden) if(gilde.getName().equals(gilden_name)) return gilde; 
		return null;
	}
	
}
