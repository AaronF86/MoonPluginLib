package me.aaronfulton.rendering.entities;

import me.aaronfulton.rendering.IRenderEntity;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.util.Vector;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseBlockEntity implements IRenderEntity {
    private final BlockDisplay display;
    private final List<BaseBlockEntity> children = new ArrayList<>();
    private final String identifier;
    private BaseBlockEntity parent;
    private Location location;
    private Vector velocity;
    private BlockData blockData;
    private double scale;

    public BaseBlockEntity(String identifier, Location location, BlockData blockData, Vector velocity, double scale) {
        this.identifier = identifier;
        this.location = location;
        this.blockData = blockData;
        this.velocity = velocity;
        this.scale = scale;

        this.display = location.getWorld().spawn(location, BlockDisplay.class);
        this.display.setBlock(blockData);
        this.display.setTransformationMatrix(createInitialMatrix());
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setParent(BaseBlockEntity parent) {
        this.parent = parent;
    }

    public void addChild(BaseBlockEntity child) {
        children.add(child);
        child.setParent(this);
    }

    public void removeChild(BaseBlockEntity child) {
        children.remove(child);
        child.setParent(null);
    }

    @Override
    public void update() {
        onUpdate();
        for (BaseBlockEntity child : children) {
            child.update();
        }
    }

    @Override
    public void render() {
        if (!isVisible()) return;

        onRender();
        for (BaseBlockEntity child : children) {
            child.render();
        }
    }

    public boolean isVisible() {
        return true;
    }

    protected abstract void onUpdate();

    protected abstract void onRender();

    private Matrix4f createInitialMatrix() {
        Matrix4f matrix = new Matrix4f();
        matrix.translate((float) location.getX(), (float) location.getY(), (float) location.getZ());
        matrix.scale((float) scale);
        return matrix;
    }

    public void murder() {
        display.remove();
        parent.removeChild(this);
        for (BaseBlockEntity child : children) {
            child.murder();
        }
        children.clear();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public BlockData getBlockData() {
        return blockData;
    }

    public void setBlockData(BlockData blockData) {
        this.blockData = blockData;
        display.setBlock(blockData);
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
}
