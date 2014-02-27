package multimaterials;

import org.bukkit.Material;

public class AnyStone extends MultiMaterial {

    public AnyStone() {
    }

    @Override
    public boolean multiMatches(Material material) {
	return material == Material.STONE || material == Material.COBBLESTONE
		|| material == Material.BRICK || material == Material.BEDROCK
		|| material == Material.CLAY_BRICK
		|| material == Material.SMOOTH_BRICK;

    }
}
