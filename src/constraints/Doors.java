package constraints;

import util.BlockArea;

public class Doors extends Constraint {

    public Doors(String s, Integer i, Boolean bool, Boolean max) {
	super(s, i, bool, max);
	if (i == null || bool != null)
	    throw new IllegalArgumentException();
    }

    @Override
    public Boolean isMetBy(BlockArea blockarea) {
	if (max == null)
	    return blockarea.getDoors().size() / 2 == i;
	else if (max)
	    return blockarea.getDoors().size() / 2 <= i;
	else
	    return blockarea.getDoors().size() / 2 >= i;
    }
}
