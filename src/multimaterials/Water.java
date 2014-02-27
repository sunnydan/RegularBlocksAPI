package multimaterials;

import org.bukkit.Material;

public class Water extends MultiMaterial {

    public Water() {
    }

    @Override
    public boolean multiMatches(Material material) {
	return material == Material.WATER
		|| material == Material.STATIONARY_WATER;
    }
}
