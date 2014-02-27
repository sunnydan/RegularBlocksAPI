package multimaterials;

import org.bukkit.Material;

public class SM extends MultiMaterial {

    String s = "Nothing will match this";

    public SM(Material material) {
	this.material = material;
    }

    public SM(String string) {
	s = string;
    }

    public boolean multiMatches(Material material) {
	return material == this.material
		|| material.toString().equalsIgnoreCase(s);
    }

    @Override
    public String toString() {
	if (material != null)
	    return material.toString();
	return s;
    }

}
