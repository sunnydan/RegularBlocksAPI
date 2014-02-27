package util;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class SLocation implements Serializable, Comparable<SLocation> {

    private static final long serialVersionUID = 5449064255843848451L;
    private transient Location location;
    private int blockx, blocky, blockz;
    private double x, y, z;
    private String world;

    public SLocation(Location loc) {
	x = loc.getX();
	y = loc.getY();
	z = loc.getZ();
	blockx = loc.getBlockX();
	blocky = loc.getBlockY();
	blockz = loc.getBlockZ();
	world = loc.getWorld().getName();
    }

    public String blockCoords() {
	return String.format("(%d, %d, %d)", getBlockX(), getBlockY(),
		getBlockZ());
    }

    @Override
    public int compareTo(SLocation arg0) {
	if (equals(arg0)) {
	    return 0;
	}
	if (arg0.getBlockX() >= getBlockX()) {
	    return 1;
	} else {
	    return -1;
	}
    }

    public String coords() {
	return String.format("(%d, %d, %d)", getX(), getY(), getZ());
    }

    public boolean equals(Location loc) {
	SLocation arg0 = new SLocation(loc);
	boolean a = getBlockX() == arg0.getBlockX();
	boolean b = getBlockX() == arg0.getBlockY();
	boolean c = getBlockX() == arg0.getBlockZ();
	if (a && b && c) {
	    return true;
	}
	return false;
    }

    public boolean equals(SLocation arg0) {
	boolean a = getBlockX() == arg0.getBlockX();
	boolean b = getBlockX() == arg0.getBlockY();
	boolean c = getBlockX() == arg0.getBlockZ();
	if (a && b && c) {
	    return true;
	}
	return false;
    }

    public int getBlockX() {
	return blockx;
    }

    public int getBlockY() {
	return blocky;
    }

    public int getBlockZ() {
	return blockz;
    }

    public Location getLocation() {
	if (location == null) {
	    World w = Bukkit.getWorld("world");
	    if (w == null)
		return null;
	    Location toRet = new Location(w, x, y, z);
	    location = toRet;
	}
	return location;
    }

    public String getWorld() {
	return world;
    }

    public double getX() {
	return x;
    }

    public double getY() {
	return y;
    }

    public double getZ() {
	return z;
    }

    public String toString() {
	return blockCoords();
    }

}