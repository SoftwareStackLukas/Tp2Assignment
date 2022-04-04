package simulator.view;

public enum Locations {
	ICONS("/resources/icons/");
	private String dir;
	
	Locations(String dir) {
		this.dir = System.getProperty("user.dir") + dir;
	}
	
	public static String getIconsDir() {
		return Locations.ICONS.getDir();
	}

	String getDir() {
		return dir;
	}
}
