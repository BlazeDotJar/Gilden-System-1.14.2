package me.ichmagomaskekse.de;

public enum GildenRolle {
	
	DEFAULT("normal"),
	NEWBY("neuling"),
	TRUSTED("vertrauter"),
	CO_LEADER("vertretender leiter"),
	LEADER("leiter");
	
	String description = "";
	
	GildenRolle(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
