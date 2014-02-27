package multimaterials;

import org.bukkit.Material;

public class AnyFence extends MultiMaterial {

    public AnyFence() {
    }

    @Override
    public boolean multiMatches(Material material) {
	return material == Material.FENCE || material == Material.NETHER_FENCE
		|| material == Material.FENCE_GATE;
    }
}
