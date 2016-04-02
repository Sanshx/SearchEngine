package classes;

import java.io.File;

public class LocationCheck {
	
	public static boolean locationCheck(String seeds) {
		if(!new File(seeds).exists()) {
			System.out.println("Error: The location of seeds.xml is wrong.");
			return false;
		}
		return true;
	}
}
