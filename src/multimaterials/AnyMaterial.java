package multimaterials;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class AnyMaterial extends MultiMaterial {

    public boolean materialMatches(Material material) {
	return true;
    }

    public boolean multiMatches(Block block) {
	return true;
    }

    public boolean multiMatches(Material material) {
	return true;
    }
    
    @Override
    public String toString() {
	return this.getClass().getSimpleName();
    }

}
