package util;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import multimaterials.MultiMaterial;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import regblocks.Blueprint;
import regblocks.ConstrainedCuboid;
import regblocks.RegularBlocks;
import constraints.Constraint;

public class BlockArea implements Serializable {

    private static final long serialVersionUID = 3045535766195021744L;
    private ArrayList<ArrayList<ArrayList<SBlock>>> sblocks = new ArrayList<ArrayList<ArrayList<SBlock>>>();
    private transient ArrayList<SBlock> allsblocks = null;
    private ArrayList<SBlock> exterior, chests, doors;
    private int dlx, dlz, dly;
    private BlockArea interior;
    public String world;

    public BlockArea() {
    }

    public BlockArea(Block b1, Block b2) {
	world = b2.getWorld().getName();
	int originy = (b1.getY() < b2.getY()) ? b1.getY() : b2.getY();
	int originz = (b1.getZ() < b2.getZ()) ? b1.getZ() : b2.getZ();
	int originx = (b1.getX() < b2.getX()) ? b1.getX() : b2.getX();
	int cornery = (b1.getY() >= b2.getY()) ? b1.getY() : b2.getY();
	int cornerz = (b1.getZ() >= b2.getZ()) ? b1.getZ() : b2.getZ();
	int cornerx = (b1.getX() >= b2.getX()) ? b1.getX() : b2.getX();
	dly = Math.abs(cornery - originy) + 1;
	dlz = Math.abs(cornerz - originz) + 1;
	dlx = Math.abs(cornerx - originx) + 1;
	for (int y = 0; y < dly; y++) {
	    for (int z = 0; z < dlz; z++) {
		for (int x = 0; x < dlx; x++) {
		    set(y, z, x, new SBlock(new Location(
			    Bukkit.getWorld(world), originx + x, originy + y,
			    originz + z).getBlock()));
		}
	    }
	}
    }

    public boolean contains(SBlock sBlock) {
	return getAllSBlocks().contains(sBlock);
    }

    public ArrayList<SBlock> getAllSBlocks() {
	if (allsblocks == null) {
	    allsblocks = new ArrayList<SBlock>();
	    for (ArrayList<ArrayList<SBlock>> ALAL : getSBlocks()) {
		for (ArrayList<SBlock> AL : ALAL) {
		    for (SBlock sb : AL) {
			allsblocks.add(sb);
		    }
		}
	    }
	}
	return allsblocks;
    }

    public Integer getCapacity() {
	Integer space = 0;
	for (SBlock sBlock : getInterior().getAllSBlocks()) {
	    if (!sBlock.getType().isSolid())
		space++;
	}
	return space / Util.getSpace_per_capacity();
    }

    public Location getCenter() {
	double x = (getSB(0, 0, 0).getX() + getSB(dly - 1, dlz - 1, dlx - 1)
		.getX()) / 2;
	double y = (getSB(0, 0, 0).getY() + getSB(dly - 1, dlz - 1, dlx - 1)
		.getY()) / 2;
	double z = (getSB(0, 0, 0).getZ() + getSB(dly - 1, dlz - 1, dlx - 1)
		.getZ()) / 2;
	Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
	return loc;
    }

    public ArrayList<SBlock> getChests() {
	if (chests == null) {
	    chests = new ArrayList<SBlock>();
	    for (SBlock sBlock : getInterior().getAllSBlocks()) {
		if (sBlock.getType() == Material.CHEST)
		    chests.add(sBlock);
	    }
	}
	return chests;
    }

    public ArrayList<SBlock> getDoors() {
	if (doors == null) {
	    doors = new ArrayList<SBlock>();
	    for (SBlock sBlock : getExterior()) {
		if (sBlock.getType().toString().toLowerCase().contains("door"))
		    doors.add(sBlock);
	    }
	}
	return doors;
    }

    public ArrayList<SBlock> getExterior() {
	if (exterior == null) {
	    exterior = new ArrayList<SBlock>();
	    for (SBlock sBlock : getAllSBlocks()) {
		if (!getInterior().contains(sBlock))
		    exterior.add(sBlock);
	    }
	}
	return exterior;
    }

    public BlockArea getInterior() {
	if (interior == null) {
	    interior = new BlockArea();
	    for (int y = 0; y < dly - 1; y++) {
		for (int z = 0; z < dlz - 1; z++) {
		    for (int x = 0; x < dlx - 1; x++) {
			if (!(y == 0 || z == 0 || x == 0)) {
			    interior.set(y - 1, z - 1, x - 1, getSB(y, z, x));
			}
		    }
		}
	    }
	}
	return interior;
    }

    public SBlock getSB(int y, int z, int x) {
	try {
	    return getSBlocks().get(y).get(z).get(x);
	} catch (IndexOutOfBoundsException e) {
	    return null;
	}
    }

    public ArrayList<ArrayList<ArrayList<SBlock>>> getSBlocks() {
	return sblocks;
    }

    public int getVolume() {
	return dly * dlz * dlx;
    }

    public ArrayList<BlockArea> getXRotations() {
	ArrayList<BlockArea> bas = new ArrayList<BlockArea>();
	BlockArea ba = this;
	for (int i = 0; i < 4; i++) {
	    ba = ba.rotateAroundX();
	    bas.add(ba);
	}
	return bas;
    }

    public ArrayList<BlockArea> getYRotations() {
	ArrayList<BlockArea> bas = new ArrayList<BlockArea>();
	BlockArea ba = this;
	for (int i = 0; i < 4; i++) {
	    ba = ba.rotateAroundY();
	    bas.add(ba);
	}
	return bas;
    }

    public ArrayList<BlockArea> getZRotations() {
	ArrayList<BlockArea> bas = new ArrayList<BlockArea>();
	BlockArea ba = this;
	for (int i = 0; i < 4; i++) {
	    ba = ba.rotateAroundZ();
	    bas.add(ba);
	}
	return bas;
    }

    public boolean hasInterference(List<? extends BlockArea> regions) {
	for (BlockArea s : regions) {
	    for (SBlock b : s.getAllSBlocks()) {
		if (this.contains(b)) {
		    return true;
		}
	    }
	}
	return false;
    }

    public boolean matches(File f) {
	Util.regBlokInfo("Attempting to match to file...");
	RegularBlocks rb = RegularBlocks.newRB(f);
	rb.print();
	if (matchesSimple(rb))
	    return true;
	if (rb.rotateX && rb.rotateY && rb.rotateZ) {
	    for (BlockArea bp1 : getXRotations()) {
		for (BlockArea bp2 : bp1.getYRotations()) {
		    if (bp2.matchRZ(rb))
			return true;
		}
	    }
	} else if (rb.rotateX && rb.rotateY) {
	    for (BlockArea bp1 : getXRotations()) {
		if (bp1.matchRY(rb))
		    return true;
	    }
	} else if (rb.rotateY && rb.rotateZ) {
	    for (BlockArea bp1 : getYRotations()) {
		if (bp1.matchRZ(rb))
		    return true;
	    }
	} else if (rb.rotateZ && rb.rotateX) {
	    for (BlockArea bp1 : getZRotations()) {
		if (bp1.matchRX(rb))
		    return true;
	    }
	} else if (rb.rotateX) {
	    if (matchRX(rb))
		return true;
	} else if (rb.rotateY) {
	    Util.regBlokInfo("Matching Y rotations...");
	    if (matchRY(rb))
		return true;
	} else if (rb.rotateZ) {
	    if (matchRZ(rb))
		return true;
	}
	return false;
    }

    public boolean matchesBPSimple(Blueprint bp) {
	boolean match = true;
	String s;
	for (int y = 0; y < getSBlocks().size(); y++) {
	    for (int z = 0; z < getSBlocks().get(y).size(); z++) {
		s = "";
		for (int x = 0; x < getSBlocks().get(y).get(z).size(); x++) {
		    SBlock sb = getSB(y, z, x);
		    MultiMaterial mm = bp.getMM(y, z, x);
		    if (mm.multiMatches(sb)) {
			if (sb == null)
			    s = s + "nully ";
			else
			    s = s + sb.toString() + "y ";
		    } else {
			if (sb == null)
			    s = s + "nulln ";
			else
			    s = s + sb.toString() + "n ";
			match = false;
		    }
		}
		Util.regBlokInfo(s);
	    }
	    Util.regBlokInfo("---");
	}
	Util.regBlokInfo("-------------");
	return match;
    }

    public boolean matchesCCCorners(ConstrainedCuboid cc) {
	if ((!cc.getCorners().get(0).multiMatches(getSB(0, 0, 0)))) {
	    Util.regBlokInfo(cc.getCorners().get(0).toString());
	    Util.regBlokInfo("0");
	    return false;
	}
	if ((!cc.getCorners().get(1).multiMatches(getSB(0, 0, dlx - 1)))) {
	    Util.regBlokInfo("1");
	    return false;
	}
	if ((!cc.getCorners().get(2).multiMatches(getSB(0, dlz - 1, 0)))) {
	    Util.regBlokInfo("2");
	    return false;
	}
	if ((!cc.getCorners().get(3).multiMatches(getSB(0, dlz - 1, dlx - 1)))) {
	    Util.regBlokInfo("3");
	    return false;
	}
	if ((!cc.getCorners().get(4).multiMatches(getSB(dly - 1, 0, 0)))) {
	    Util.regBlokInfo("4");
	    return false;
	}
	if ((!cc.getCorners().get(5).multiMatches(getSB(dly - 1, 0, dlx - 1)))) {
	    Util.regBlokInfo("5");
	    return false;
	}
	if ((!cc.getCorners().get(6).multiMatches(getSB(dly - 1, dlz - 1, 0)))) {
	    Util.regBlokInfo("6");
	    return false;
	}
	if ((!cc.getCorners().get(7)
		.multiMatches(getSB(dly - 1, dlz - 1, dlx - 1)))) {
	    Util.regBlokInfo("7");
	    return false;
	}
	return true;
    }

    private boolean matchesCCEdges(ConstrainedCuboid cc) {
	for (int x = 1; x < dlx - 1; x++) {
	    if (!cc.getEdge(0).multiMatches(getSB(0, 0, x)))
		return false;
	}
	for (int z = 1; z < dlz - 1; z++) {
	    if (!cc.getEdge(1).multiMatches(getSB(0, z, 0)))
		return false;
	}
	for (int z = 1; z < dlz - 1; z++) {
	    if (!cc.getEdge(2).multiMatches(getSB(0, z, dlx - 1)))
		return false;
	}
	for (int x = 1; x < dlx - 1; x++) {
	    if (!cc.getEdge(3).multiMatches(getSB(0, dlz - 1, x)))
		return false;
	}
	for (int y = 1; y < dly - 1; y++) {
	    if (!cc.getEdge(4).multiMatches(getSB(y, 0, 0)))
		return false;
	}
	for (int y = 1; y < dly - 1; y++) {
	    if (!cc.getEdge(5).multiMatches(getSB(y, 0, dlx - 1)))
		return false;
	}
	for (int y = 1; y < dly - 1; y++) {
	    if (!cc.getEdge(6).multiMatches(getSB(y, dlz - 1, 0)))
		return false;
	}
	for (int y = 1; y < dly - 1; y++) {
	    if (!cc.getEdge(7).multiMatches(getSB(y, dlz - 1, dlx - 1)))
		return false;
	}
	for (int x = 1; x < dlx - 1; x++) {
	    if (!cc.getEdge(8).multiMatches(getSB(dly - 1, 0, x)))
		return false;
	}
	for (int z = 1; z < dlz - 1; z++) {
	    if (!cc.getEdge(9).multiMatches(getSB(dly - 1, z, 0)))
		return false;
	}
	for (int z = 1; z < dlz - 1; z++) {
	    if (!cc.getEdge(10).multiMatches(getSB(dly - 1, z, dlx - 1)))
		return false;
	}
	for (int x = 1; x < dlx - 1; x++) {
	    if (!cc.getEdge(11).multiMatches(getSB(dly - 1, dlz - 1, x)))
		return false;
	}

	return true;
    }

    public boolean matchesCCFaces(ConstrainedCuboid cc) {
	for (int z = 1; z < dlz - 1; z++) {
	    for (int x = 1; x < dlx - 1; x++) {
		if (!cc.getFaces().get(0).multiMatches(getSB(0, z, x)))
		    return false;
	    }
	}
	for (int y = 1; y < dly - 1; y++) {
	    for (int x = 1; x < dlx - 1; x++) {
		if (!cc.getFaces().get(1).multiMatches(getSB(y, 0, x))) {
		    return false;
		}
	    }
	}
	for (int y = 1; y < dly - 1; y++) {
	    for (int z = 1; z < dlz - 1; z++) {
		if (!cc.getFaces().get(2).multiMatches(getSB(y, z, 0))) {
		    return false;
		}
	    }
	}
	for (int y = 1; y < dly - 1; y++) {
	    for (int z = 1; z < dlz - 1; z++) {
		if (!cc.getFaces().get(3).multiMatches(getSB(y, z, dlx - 1))) {
		    return false;
		}
	    }
	}
	for (int y = 1; y < dly - 1; y++) {
	    for (int x = 1; x < dlx - 1; x++) {
		if (!cc.getFaces().get(4).multiMatches(getSB(y, dlz - 1, x))) {
		    return false;
		}
	    }
	}
	for (int z = 1; z < dlz - 1; z++) {
	    for (int x = 1; x < dlx - 1; x++) {
		if (!cc.getFaces().get(5).multiMatches(getSB(dly - 1, z, x)))
		    return false;
	    }
	}
	return true;
    }

    public boolean matchesCCInterior(ConstrainedCuboid cc) {
	for (SBlock sb : getInterior().getAllSBlocks()) {
	    if (!cc.getInterior().multiMatches(sb))
		return false;
	}
	return true;
    }

    public boolean matchesCCSimple(ConstrainedCuboid cc) {
	Util.regBlokInfo("---A---");
	Boolean match = true;
	Util.regBlokInfo("capacity: " + getCapacity().toString());
	Util.regBlokInfo("doors: " + Integer.toString(getDoors().size() / 2));
	for (ArrayList<ArrayList<SBlock>> ALAL : getSBlocks()) {
	    for (ArrayList<SBlock> AL : ALAL) {
		String s = "";
		for (SBlock sb : AL) {
		    if (sb == null)
			s = s + "null ";
		    else
			s = s + sb.toString() + " ";
		}
		Util.regBlokInfo(s);
	    }
	    Util.regBlokInfo("---");
	}
	for (Constraint constraint : cc.getConstraints()) {
	    if (!constraint.isMetBy(this)) {
		Util.regBlokInfo("Not all constraints met.");
		match = false;
	    }
	}
	if (!matchesCCCorners(cc)) {
	    Util.regBlokInfo("Corners not matched");
	    match = false;
	}
	if (!matchesCCEdges(cc)) {
	    Util.regBlokInfo("Edges not matched");
	    match = false;
	}
	if (!matchesCCFaces(cc)) {
	    Util.regBlokInfo("Faces not matched");
	    match = false;
	}
	if (!matchesCCInterior(cc)) {
	    Util.regBlokInfo("Interior not matched");
	    match = false;
	}
	return match;
    }

    private boolean matchesSimple(RegularBlocks rb) {
	if (rb instanceof Blueprint)
	    return matchesBPSimple((Blueprint) rb);
	else
	    return matchesCCSimple((ConstrainedCuboid) rb);
    }

    public boolean matchRX(RegularBlocks bp) {
	for (BlockArea ba : getXRotations()) {
	    if (ba.matchesSimple(bp))
		return true;
	}
	return false;
    }

    public boolean matchRY(RegularBlocks bp) {
	for (BlockArea ba : getYRotations()) {
	    if (ba.matchesSimple(bp))
		return true;
	}
	return false;
    }

    public boolean matchRZ(RegularBlocks bp) {
	for (BlockArea ba : getZRotations()) {
	    if (ba.matchesSimple(bp))
		return true;
	}
	return false;
    }

    public void print() {
	for (ArrayList<ArrayList<SBlock>> ALALMM : getSBlocks()) {
	    for (ArrayList<SBlock> ALMM : ALALMM) {
		String s = "";
		for (SBlock MM : ALMM) {
		    s = s + " " + MM.toString();
		}
		Util.regBlokInfo(s);
	    }
	}
    }

    public BlockArea rotateAroundX() {
	BlockArea blockarea = new BlockArea();
	for (int y = 0; y < getSBlocks().size(); y++) {
	    for (int z = 0; z < getSBlocks().get(y).size(); z++) {
		for (int x = 0; x < getSBlocks().get(y).get(z).size(); x++) {
		    int newy = -z + getSBlocks().get(y).size() - 1;
		    int newz = y;
		    blockarea.set(newy, newz, x, getSB(y, z, x));
		}
	    }
	}
	return blockarea;
    }

    public BlockArea rotateAroundY() {
	BlockArea blockarea = new BlockArea();
	for (int y = 0; y < getSBlocks().size(); y++) {
	    for (int z = 0; z < getSBlocks().get(y).size(); z++) {
		for (int x = 0; x < getSBlocks().get(y).get(z).size(); x++) {
		    int newz = -x + getSBlocks().get(y).get(z).size() - 1;
		    int newx = z;
		    blockarea.set(y, newz, newx, getSB(y, z, x));
		}
	    }
	}
	return blockarea;
    }

    public BlockArea rotateAroundZ() {
	BlockArea blockarea = new BlockArea();
	for (int y = 0; y < getSBlocks().size(); y++) {
	    for (int z = 0; z < getSBlocks().get(y).size(); z++) {
		for (int x = 0; x < getSBlocks().get(y).get(z).size(); x++) {
		    int newy = -x + getSBlocks().get(y).get(z).size() - 1;
		    int newx = y;
		    blockarea.set(newy, z, newx, getSB(y, z, x));
		}
	    }
	}
	return blockarea;
    }

    public void set(int ny, int nz, int nx, SBlock sblock) {
	while (getSBlocks().size() < ny + 1)
	    getSBlocks().add(new ArrayList<ArrayList<SBlock>>());
	while (getSBlocks().get(ny).size() < nz + 1)
	    getSBlocks().get(ny).add(new ArrayList<SBlock>());
	while (getSBlocks().get(ny).get(nz).size() < nx + 1)
	    getSBlocks().get(ny).get(nz).add(null);
	getSBlocks().get(ny).get(nz).set(nx, sblock);
    }
}
