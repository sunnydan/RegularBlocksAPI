package multimaterials;

import org.bukkit.Material;

public class NotGravity extends MultiMaterial {

    public NotGravity() {
    }

    @Override
    public boolean multiMatches(Material material) {
	return !material.hasGravity();
    }
}
