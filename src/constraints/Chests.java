package constraints;

import util.BlockArea;

public class Chests extends Constraint {

    public Chests(String s, Integer i, Boolean bool, Boolean max) {
	super(s, i, bool, max);
	if (i == null || bool != null)
	    throw new IllegalArgumentException();
    }

    @Override
    public Boolean isMetBy(BlockArea blockarea) {
	if (max == null)
	    return blockarea.getChests().size() == i;
	else if (max)
	    return blockarea.getChests().size() <= i;
	else
	    return blockarea.getChests().size() >= i;
    }
}
