package me.ichmagomaskekse.de;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileSystem {
	
	public static boolean saveGilde(Gilde gilde, boolean override) {
		File file = new File(GildenSystem.gilden_home+gilde.getName()+"\\save.yml");
		if(override == false && file.exists()) return false;
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		//Neuen Werte werden gesetzt
		cfg.set("Gilde.Name", gilde.getName());
		cfg.set("Gilde.Prefix Color", gilde.getPrefixColor());
		cfg.set("Gilde.Suffix Color", gilde.getSuffixColor());
		cfg.set("Gilde.Members", gilde.getConvertedMembersAsArrayList());
		
		//Cfg wird gespeichert
		try {
			cfg.save(file);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static HashMap<String, String> data = new HashMap<String, String>();
	private static ArrayList<String> list = new ArrayList<String>();
	private static String[] array = null;
	public static Gilde loadGilde(String gilden_name) {
		File file = new File(GildenSystem.gilden_home+gilden_name+"\\save.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		Gilde gilde = new Gilde(gilden_name);
		
		list = (ArrayList<String>) cfg.getStringList("Gilde.Members");
		data.put("name", cfg.getString("Gilde.Name"));
		data.put("prefix color", cfg.getString("Gilde.Prefix Color"));
		data.put("suffix color", cfg.getString("Gilde.Suffix Color"));
		
		for(String s : list) {
			s = s.replace(" ", "");
			array = s.split("/");
			gilde.addMember(UUID.fromString(array[0]), GildenRolle.valueOf(array[1]), false);
			System.out.println("§aDer Spieler §e"+array[0]+" §awurde als §e"+array[1]+" §aaus der Datei gelesen");
		}
		
		gilde.translateData(data);
		return gilde;
	}
	
	public static boolean deleteFile(String path) {
		File file = new File(path);
		return file.delete();
	}

	public static boolean deleteDirectory(String path) {
		File file = new File(path);
		return file.delete();
	}
	
}
