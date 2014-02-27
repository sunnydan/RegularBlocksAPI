package multimaterials;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class MultiMaterial {

    public static MultiMaterial newMM(String s) {
	if (s.contains(",")) {
	    String[] strs = s.split(",");
	    ArrayList<MultiMaterial> mm = new ArrayList<MultiMaterial>();
	    for (String str : strs) {
		str.replaceAll(",", " ");
		str.trim();
		mm.add(MultiMaterial.newMM(str));
	    }
	    return new MultiMaterial(mm);
	} else {
	    for (Class<?> multimaterial : MultiMaterial.subclasses()) {
		if (s.equalsIgnoreCase(multimaterial.getSimpleName())) {
		    try {
			return (MultiMaterial) multimaterial.newInstance();
		    } catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		    }
		}
	    }
	    for (Material m : Material.values()) {
		if (s.equalsIgnoreCase(m.toString()))
		    return new SM(s);
	    }
	    
	    // didja add the new classes to the subclasses list?
	    throw new IllegalArgumentException();
	}
    }

    @SuppressWarnings("rawtypes")
    public static Class[] subclasses() {
	Class[] classes = { AnyFence.class, AnyGlass.class, AnyMaterial.class,
		AnyStone.class, Water.class, NotGravity.class,
		Occluding.class, Solid.class, Transparent.class };
	return classes;
    }

    Material material = null;
    ArrayList<MultiMaterial> multiMaterials = new ArrayList<MultiMaterial>();

    public MultiMaterial() {
    };

    public MultiMaterial(ArrayList<MultiMaterial> multiMaterials) {
	this.multiMaterials = multiMaterials;
    }

    public boolean materialMatches(Material material) {
	return material == this.material;
    }

    public boolean multiMatches(Block block) {
	try {
	    return multiMatches(block.getType());
	} catch (NullPointerException e) {
	    return multiMatches(Material.AIR);
	}
    }

    public boolean multiMatches(Material material) {
	for (MultiMaterial m : multiMaterials) {
	    if (!m.multiMatches(material))
		return false;
	}
	return true;
    }

    public String toString() {
	if (this.getClass().getSimpleName().equalsIgnoreCase("MultiMaterial")) {
	    String s = "";
	    for (MultiMaterial mm : multiMaterials) {
		s = s + mm.toString().toLowerCase().substring(0, 3) + ",";
	    }
	    return s;
	} else {
	    return this.getClass().getSimpleName().toLowerCase();
	}
    }
}
