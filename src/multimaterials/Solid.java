package multimaterials;

import org.bukkit.Material;

public class Solid extends MultiMaterial {

    public Solid() {
    }

    @Override
    public boolean multiMatches(Material material) {
	return material.isSolid();
    }
}
