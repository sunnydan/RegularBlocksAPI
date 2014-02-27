package multimaterials;

import org.bukkit.Material;

public class Transparent extends MultiMaterial {

    public Transparent() {
    }

    @Override
    public boolean multiMatches(Material material) {
	return material.isTransparent();
    }
}
