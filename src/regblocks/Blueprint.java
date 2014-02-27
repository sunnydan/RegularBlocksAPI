package regblocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import multimaterials.AnyMaterial;
import multimaterials.MultiMaterial;

public class Blueprint extends RegularBlocks {

    private ArrayList<ArrayList<ArrayList<MultiMaterial>>> multiMaterials = new ArrayList<ArrayList<ArrayList<MultiMaterial>>>();
    String str;

    Scanner sca;

    public Blueprint(File f) {
	try {
	    sca = new Scanner(f);
	    if (!sca.next().equals("blueprint"))
		throw new IllegalArgumentException();
	    sca.next();
	    if (sca.next().equalsIgnoreCase("[x]"))
		rotateX = true;
	    if (sca.next().equalsIgnoreCase("[y]"))
		rotateY = true;
	    if (sca.next().equalsIgnoreCase("[z]"))
		rotateZ = true;
	    sca.next();
	    if (sca.next().equalsIgnoreCase("[x]"))
		reflectX = true;
	    if (sca.next().equalsIgnoreCase("[y]"))
		reflectY = true;
	    if (sca.next().equalsIgnoreCase("[z]"))
		reflectZ = true;
	    int x = 0, z = 0, y = 0;
	    while (sca.hasNext()) {
		ups();
		if (str.equalsIgnoreCase("-")) {
		    x = 0;
		    z++;
		} else if (str.equalsIgnoreCase("---")) {
		    x = 0;
		    z = 0;
		    y++;
		} else {
		    set(y, z, x, MultiMaterial.newMM(str));
		    x++;
		}
	    }
	} catch (FileNotFoundException e) {
	}
    }

    public ArrayList<MultiMaterial> getAllMultiMaterials() {
	ArrayList<MultiMaterial> mms = new ArrayList<MultiMaterial>();
	for (ArrayList<ArrayList<MultiMaterial>> ALALMM : getMultiMaterials()) {
	    for (ArrayList<MultiMaterial> ALMM : ALALMM) {
		for (MultiMaterial MM : ALMM) {
		    mms.add(MM);
		}
	    }
	}
	return mms;
    }

    public MultiMaterial getMM(int y, int z, int x) {
	MultiMaterial mm;
	try {
	    mm = getMultiMaterials().get(y).get(z).get(x);
	    if (mm == null)
		mm = new AnyMaterial();
	} catch (IndexOutOfBoundsException e) {
	    mm = new AnyMaterial();
	}
	return mm;
    }

    public ArrayList<ArrayList<ArrayList<MultiMaterial>>> getMultiMaterials() {
	return multiMaterials;
    }

    public void set(int y, int z, int x, MultiMaterial multiMaterial) {
	while (multiMaterials.size() - 1 < y) {
	    multiMaterials.add(new ArrayList<ArrayList<MultiMaterial>>());
	}
	while (multiMaterials.get(y).size() - 1 < z) {
	    multiMaterials.get(y).add(new ArrayList<MultiMaterial>());
	}
	while (multiMaterials.get(y).get(z).size() - 1 < x) {
	    multiMaterials.get(y).get(z).add(new AnyMaterial());
	}
	multiMaterials.get(y).get(z).set(x, multiMaterial);
    }

    public ArrayList<String> toStrings() {
	ArrayList<String> ss = new ArrayList<String>();
	for (ArrayList<ArrayList<MultiMaterial>> ALALMM : getMultiMaterials()) {
	    for (ArrayList<MultiMaterial> ALMM : ALALMM) {
		String s = "";
		for (MultiMaterial MM : ALMM) {
		    s = s + " " + MM.toString();
		}
		ss.add(s);
	    }
	}
	return ss;
    }

    private void ups() {
	str = sca.next();
    }

}
