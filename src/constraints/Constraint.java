package constraints;

import java.lang.reflect.InvocationTargetException;

import util.BlockArea;
import util.Util;

public abstract class Constraint {

    public static Constraint newConstraint(String[] args) {
	if (args.length == 2) {
	    String s = "";
	    Integer i = null;
	    Boolean bool = null;
	    Boolean max = null;
	    if (args[0].substring(0, 3).equalsIgnoreCase("max"))
		max = true;
	    else if (args[0].substring(0, 3).equalsIgnoreCase("min"))
		max = false;
	    else
		max = null;
	    try {
		i = Integer.parseInt(args[1]);
	    } catch (NumberFormatException e) {
		bool = Boolean.parseBoolean(args[1]);
	    }
	    if (max == null)
		s = args[0].substring(0, args[0].length() - 1);
	    else
		s = args[0].substring(3, args[0].length() - 1);
	    for (Class<?> con : Constraint.subclasses()) {
		if (s.equalsIgnoreCase(con.getSimpleName())) {
		    try {
			return (Constraint) con.getConstructor(String.class,
				Integer.class, Boolean.class, Boolean.class)
				.newInstance(s, i, bool, max);
		    } catch (InstantiationException | IllegalAccessException
			    | IllegalArgumentException
			    | InvocationTargetException | NoSuchMethodException
			    | SecurityException e) {
			e.printStackTrace();
		    }
		}
	    }
	    Util.regBlokInfo(s);
	    throw new IllegalArgumentException();
	}
	throw new IllegalArgumentException();
    }

    @SuppressWarnings("rawtypes")
    public static Class[] subclasses() {
	Class[] classes = { Doors.class, Chests.class, InteriorSpace.class,
		Capacity.class };
	return classes;
    }

    protected String s = null;
    protected Integer i = null;
    protected Boolean bool = null;
    protected Boolean max = null;

    public Constraint(String s, Integer i, Boolean bool, Boolean max) {
	this.s = s;
	this.i = i;
	this.bool = bool;
	this.max = max;
    }

    public abstract Boolean isMetBy(BlockArea blockarea);

    public String toString() {
	String max;
	if (this.max == null)
	    max = "";
	else if (this.max)
	    max = "max";
	else
	    max = "min";
	String sI = (i == null) ? bool.toString() : i.toString();
	return String.format("%s%s: %s", max, s, sI);
    }
}
