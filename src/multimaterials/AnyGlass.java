package multimaterials;

import org.bukkit.Material;

public class AnyGlass extends MultiMaterial {

    public AnyGlass() {
    }

    @Override
    public boolean multiMatches(Material material) {
	return material == Material.GLASS || material == Material.THIN_GLASS
		|| material == Material.STAINED_GLASS
		|| material == Material.STAINED_GLASS_PANE;
    }
}
