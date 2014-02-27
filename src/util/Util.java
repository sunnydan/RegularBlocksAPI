package util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Util extends JavaPlugin implements Listener {

    private static File configFile = new File(
	    "plugins/RegularBlocks/config.config");
    private static Boolean speak = null;
    private static Integer space_per_capacity = null;

    public static void regBlokInfo(String message) {
	if (getSpeak())
	    utilInfo(message);
    }

    private static void utilInfo(String message) {
	Bukkit.getServer().getLogger().info("[RegularBlocks]: " + message);
    }

    private static boolean getSpeak() {
	if (speak == null)
	    init();
	return speak;
    }

    public static void init() {
	if (!configFile.exists()) {
	    try {
		configFile.getParentFile().mkdirs();
		configFile.createNewFile();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	try {
	    utilInfo("Loading Config File...");
	    Scanner scanner = new Scanner(configFile);
	    scanner.next();
	    speak = Boolean.parseBoolean(scanner.next());
	    utilInfo("speak: " + speak.toString());
	    scanner.next();
	    space_per_capacity = Integer.parseInt(scanner.next());
	    utilInfo("space per capacity: " + space_per_capacity.toString());
	    scanner.close();
	    utilInfo("Done");
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public static Integer getSpace_per_capacity() {
	if (space_per_capacity == null)
	    init();
	return space_per_capacity;
    }
}