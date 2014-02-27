package multimaterials;

import org.bukkit.Material;

public class Occluding extends MultiMaterial {

    public Occluding() {
    }

    @Override
    public boolean multiMatches(Material material) {
	return material.isOccluding();
    }
}
