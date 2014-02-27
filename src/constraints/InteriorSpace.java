package constraints;

import org.bukkit.Material;

import util.BlockArea;
import util.SBlock;

public class InteriorSpace extends Constraint {

    public InteriorSpace(String s, Integer i, Boolean bool, Boolean max) {
	super(s, i, bool, max);
	if (i == null || bool != null)
	    throw new IllegalArgumentException();
    }

    @Override
    public Boolean isMetBy(BlockArea blockarea) {
	int interiorSpace = 0;
	for (SBlock sblock : blockarea.getInterior().getAllSBlocks()) {
	    if (sblock.blockIs(Material.AIR))
		interiorSpace++;
	}
	if (max == null)
	    return interiorSpace == i;
	else if (max)
	    return interiorSpace <= i;
	else
	    return interiorSpace >= i;
    }

}
