package regblocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import util.Util;

public abstract class RegularBlocks {

    public boolean rotateX = false, rotateY = false, rotateZ = false,
	    reflectX = false, reflectY = false, reflectZ = false;

    public static RegularBlocks newRB(File f) {
	Scanner scanner;
	try {
	    scanner = new Scanner(f);
	    String s = scanner.next();
	    scanner.close();
	    if (s.equalsIgnoreCase("blueprint"))
		return new Blueprint(f);
	    else if (s.equalsIgnoreCase("constrainedcuboid"))
		return new ConstrainedCuboid(f);
	    else
		throw new IllegalArgumentException();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public abstract ArrayList<String> toStrings();

    public void print() {
	for (String s : toStrings()) {
	    Util.regBlokInfo(s);
	}
    }

}
