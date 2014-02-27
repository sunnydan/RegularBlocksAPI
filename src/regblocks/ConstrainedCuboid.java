package regblocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import constraints.Constraint;
import multimaterials.MultiMaterial;

public class ConstrainedCuboid extends RegularBlocks {

    ArrayList<Constraint> constraints = new ArrayList<Constraint>();
    ArrayList<MultiMaterial> corners = new ArrayList<MultiMaterial>();
    ArrayList<MultiMaterial> edges = new ArrayList<MultiMaterial>();
    ArrayList<MultiMaterial> faces = new ArrayList<MultiMaterial>();
    MultiMaterial Interior;
    private Scanner sca;
    private String str = "";

    public ConstrainedCuboid(File f) {
	try {
	    sca = new Scanner(f);
	    if (!sca.next().equals("constrainedcuboid"))
		throw new IllegalArgumentException();
	    sca.next();
	    if (sca.next().equalsIgnoreCase("[x]"))
		rotateX = true;
	    if (sca.next().equalsIgnoreCase("[y]"))
		rotateY = true;
	    if (sca.next().equalsIgnoreCase("[z]"))
		rotateZ = true;
	    sca.next();
	    if (sca.next().equalsIgnoreCase("[x]"))
		reflectX = true;
	    if (sca.next().equalsIgnoreCase("[y]"))
		reflectY = true;
	    if (sca.next().equalsIgnoreCase("[z]"))
		reflectZ = true;
	    if (ups().equalsIgnoreCase("[constraints]")) {
		while (!ups().equalsIgnoreCase("[constraints]")) {
		    String[] args = new String[2];
		    args[0] = str;
		    args[1] = ups();
		    constraints.add(Constraint.newConstraint(args));
		}
	    }
	    corners.add(MultiMaterial.newMM(ups()));
	    edges.add(MultiMaterial.newMM(ups()));
	    corners.add(MultiMaterial.newMM(ups()));
	    edges.add(MultiMaterial.newMM(ups()));
	    faces.add(MultiMaterial.newMM(ups()));
	    edges.add(MultiMaterial.newMM(ups()));
	    corners.add(MultiMaterial.newMM(ups()));
	    edges.add(MultiMaterial.newMM(ups()));
	    corners.add(MultiMaterial.newMM(ups()));
	    ups();
	    edges.add(MultiMaterial.newMM(ups()));
	    faces.add(MultiMaterial.newMM(ups()));
	    edges.add(MultiMaterial.newMM(ups()));
	    faces.add(MultiMaterial.newMM(ups()));
	    Interior = MultiMaterial.newMM(ups());
	    faces.add(MultiMaterial.newMM(ups()));
	    edges.add(MultiMaterial.newMM(ups()));
	    faces.add(MultiMaterial.newMM(ups()));
	    edges.add(MultiMaterial.newMM(ups()));
	    ups();
	    corners.add(MultiMaterial.newMM(ups()));
	    edges.add(MultiMaterial.newMM(ups()));
	    corners.add(MultiMaterial.newMM(ups()));
	    edges.add(MultiMaterial.newMM(ups()));
	    faces.add(MultiMaterial.newMM(ups()));
	    edges.add(MultiMaterial.newMM(ups()));
	    corners.add(MultiMaterial.newMM(ups()));
	    edges.add(MultiMaterial.newMM(ups()));
	    corners.add(MultiMaterial.newMM(ups()));

	} catch (FileNotFoundException e) {
	}
    }

    public ArrayList<Constraint> getConstraints() {
	ArrayList<Constraint> tbr = new ArrayList<Constraint>();
	for (Constraint constraint : constraints) {
	    if (constraint == null)
		tbr.add(constraint);
	}
	constraints.removeAll(tbr);
	return constraints;
    }

    public MultiMaterial getCorner(int i) {
	return getCorners().get(i);
    }

    public ArrayList<MultiMaterial> getCorners() {
	return corners;
    }

    public MultiMaterial getEdge(int i) {
	return getEdges().get(i);
    }

    public ArrayList<MultiMaterial> getEdges() {
	return edges;
    }

    public MultiMaterial getFace(int i) {
	return getFaces().get(i);
    }

    public ArrayList<MultiMaterial> getFaces() {
	return faces;
    }

    public MultiMaterial getInterior() {
	return Interior;
    }

    public void setConstraints(ArrayList<Constraint> constraints) {
	this.constraints = constraints;
    }

    public void setCorners(ArrayList<MultiMaterial> corners) {
	this.corners = corners;
    }

    public void setEdges(ArrayList<MultiMaterial> edges) {
	this.edges = edges;
    }

    public void setFaces(ArrayList<MultiMaterial> faces) {
	this.faces = faces;
    }

    public void setInterior(MultiMaterial interior) {
	Interior = interior;
    }

    public ArrayList<String> toStrings() {
	ArrayList<String> ss = new ArrayList<String>();
	String s = "%s %s %s";
	ss.add("[constraints]");
	for (Constraint constraint : getConstraints()) {
	    ss.add(constraint.toString());
	}
	ss.add("[constraints]");
	int corner = 0;
	int edge = 0;
	int face = 0;
	ss.add(String.format(s, getCorner(corner++).toString(), getEdge(edge++)
		.toString(), getCorner(corner++).toString()));
	ss.add(String.format(s, getEdge(edge++).toString(), getFace(face++)
		.toString(), getEdge(edge++).toString()));
	ss.add(String.format(s, getCorner(corner++).toString(), getEdge(edge++)
		.toString(), getCorner(corner++).toString()));
	ss.add(String.format(s, getEdge(edge++).toString(), getFace(face++)
		.toString(), getEdge(edge++).toString()));
	ss.add(String.format(s, getFace(face++).toString(),
		Interior.toString(), getFace(face++).toString()));
	ss.add(String.format(s, getEdge(edge++).toString(), getFace(face++)
		.toString(), getEdge(edge++).toString()));
	ss.add(String.format(s, getCorner(corner++).toString(), getEdge(edge++)
		.toString(), getCorner(corner++).toString()));
	ss.add(String.format(s, getEdge(edge++).toString(), getFace(face++)
		.toString(), getEdge(edge++).toString()));
	ss.add(String.format(s, getCorner(corner++).toString(), getEdge(edge++)
		.toString(), getCorner(corner++).toString()));
	return ss;
    }

    private String ups() {
	str = sca.next();
	return str;
    }

}
