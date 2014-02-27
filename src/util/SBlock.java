package util;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class SBlock implements Serializable, Block {

    private static final long serialVersionUID = 6348404057801887498L;
    private transient Block block;
    private SLocation location;
    private byte blockData;
    private int blockId;

    @SuppressWarnings("deprecation")
    public SBlock(Block block) {
	this.location = new SLocation(block.getLocation());
	this.blockId = block.getTypeId();
	this.blockData = block.getData();
    }

    public String toString() {
	return String.format("%s", getType().toString().toLowerCase()
		.substring(0, 3));
    }

    public boolean blockIs(Material material) {
	return getBlock().getType() == material;
    }

    public boolean blockIsDoor() {
	boolean a = blockIs(Material.WOOD_DOOR);
	boolean b = blockIs(Material.WOODEN_DOOR);
	boolean c = blockIs(Material.IRON_DOOR);
	boolean d = blockIs(Material.IRON_DOOR_BLOCK);
	if (a || b || c || d) {
	    return true;
	}
	return false;
    }

    @Override
    public boolean breakNaturally() {
	return getBlock().breakNaturally();
    }

    @Override
    public boolean breakNaturally(ItemStack arg0) {
	return getBlock().breakNaturally(arg0);
    }

    public boolean equals(Block block) {
	return equals(new SBlock(block));
    }

    public boolean equals(SBlock sBlock) {
	if (getBlock().getLocation().equals(sBlock.getBlock().getLocation())) {
	    return true;
	}
	return false;
    }

    @Override
    public Biome getBiome() {
	return getBlock().getBiome();
    }

    public Block getBlock() {
	if (block == null) {
	    Location loc = location.getLocation();
	    block = loc.getBlock();
	}
	return block;
    }

    @Override
    public int getBlockPower() {
	return getBlock().getBlockPower();
    }

    @Override
    public int getBlockPower(BlockFace arg0) {
	return getBlock().getBlockPower(arg0);
    }

    @Override
    public Chunk getChunk() {
	return getBlock().getChunk();
    }

    @SuppressWarnings("deprecation")
    @Override
    public byte getData() {
	return getBlock().getData();
    }

    @Override
    public Collection<ItemStack> getDrops() {
	return getBlock().getDrops();
    }

    @Override
    public Collection<ItemStack> getDrops(ItemStack arg0) {
	return getBlock().getDrops(arg0);
    }

    @Override
    public BlockFace getFace(Block arg0) {
	return getBlock().getFace(arg0);
    }

    @Override
    public double getHumidity() {
	return getBlock().getHumidity();
    }

    @Override
    public byte getLightFromBlocks() {
	return getBlock().getLightFromBlocks();
    }

    @Override
    public byte getLightFromSky() {
	return getBlock().getLightFromSky();
    }

    @Override
    public byte getLightLevel() {
	return getBlock().getLightLevel();
    }

    @Override
    public Location getLocation() {
	return getBlock().getLocation();
    }

    @Override
    public Location getLocation(Location arg0) {
	return getBlock().getLocation(arg0);
    }

    @SuppressWarnings("deprecation")
    public Location getLocTotal() {
	Location loc = location.getLocation();
	loc.getBlock().setTypeId(blockId);
	loc.getBlock().setData(blockData);
	return loc;
    }

    @Override
    public List<MetadataValue> getMetadata(String arg0) {
	return getBlock().getMetadata(arg0);
    }

    @Override
    public PistonMoveReaction getPistonMoveReaction() {
	return getBlock().getPistonMoveReaction();
    }

    @Override
    public Block getRelative(BlockFace arg0) {
	return getBlock().getRelative(arg0);
    }

    @Override
    public Block getRelative(BlockFace arg0, int arg1) {
	return getBlock().getRelative(arg0, arg1);
    }

    public SBlock getRelative(int i, int j, int k) {
	if (i == 0 && j == 0 && k == 0)
	    return this;
	return new SBlock(getBlock().getRelative(i, j, k));
    }

    @Override
    public BlockState getState() {
	return getBlock().getState();
    }

    @Override
    public double getTemperature() {
	return getBlock().getTemperature();
    }

    public Material getType() {
	return getBlock().getType();
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getTypeId() {
	return getBlock().getTypeId();
    }

    @Override
    public World getWorld() {
	return getBlock().getWorld();
    }

    public int getX() {
	return getBlock().getX();
    }

    public int getY() {
	return getBlock().getY();
    }

    public int getZ() {
	return getBlock().getZ();
    }

    @Override
    public boolean hasMetadata(String arg0) {
	return getBlock().hasMetadata(arg0);
    }

    @Override
    public boolean isBlockFaceIndirectlyPowered(BlockFace arg0) {
	return getBlock().isBlockFaceIndirectlyPowered(arg0);
    }

    @Override
    public boolean isBlockFacePowered(BlockFace arg0) {
	return getBlock().isBlockFacePowered(arg0);
    }

    @Override
    public boolean isBlockIndirectlyPowered() {
	return getBlock().isBlockIndirectlyPowered();
    }

    @Override
    public boolean isBlockPowered() {
	return getBlock().isBlockPowered();
    }

    @Override
    public boolean isEmpty() {
	return getBlock().isEmpty();
    }

    @Override
    public boolean isLiquid() {
	return getBlock().isLiquid();
    }

    @Override
    public void removeMetadata(String arg0, Plugin arg1) {
	getBlock().removeMetadata(arg0, arg1);
    }

    @Override
    public void setBiome(Biome arg0) {
	getBlock().setBiome(arg0);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setData(byte arg0) {
	getBlock().setData(arg0);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setData(byte arg0, boolean arg1) {
	getBlock().setData(arg0, arg1);
    }

    @Override
    public void setMetadata(String arg0, MetadataValue arg1) {
	getBlock().setMetadata(arg0, arg1);
    }

    @Override
    public void setType(Material arg0) {
	getBlock().setType(arg0);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean setTypeId(int arg0) {
	return getBlock().setTypeId(arg0);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean setTypeId(int arg0, boolean arg1) {
	return getBlock().setTypeId(arg0, arg1);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean setTypeIdAndData(int arg0, byte arg1, boolean arg2) {
	return getBlock().setTypeIdAndData(arg0, arg1, arg2);
    }
}