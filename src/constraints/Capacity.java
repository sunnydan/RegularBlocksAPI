package constraints;

import util.BlockArea;

public class Capacity extends Constraint {

    public Capacity(String s, Integer i, Boolean bool, Boolean max) {
	super(s, i, bool, max);
	if (i == null || bool != null)
	    throw new IllegalArgumentException();
    }

    @Override
    public Boolean isMetBy(BlockArea blockarea) {
	if (max == null)
	    return blockarea.getCapacity() == i;
	else if (max)
	    return blockarea.getCapacity() <= i;
	else
	    return blockarea.getCapacity() >= i;
    }
}
