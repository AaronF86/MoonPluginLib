package me.aaronfulton.debug.render;

import me.aaronfulton.debug.logger.ColorfulLogger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * A utility class to create and manage debug display entities that represent lines or directions.
 */
public class DisplayEntityDebug {
    private static final double OFFSET = 0.1;

    private BlockDisplay display;
    private Material material; // Material of the display
    private Location locationA; // Start point of the line (centered at back of display)
    private double length; // Length from locationA to end (locationB)
    private Vector vector; // Directional vector

    /**
     * Creates a debug line display entity between two locations.
     *
     * @param locA    The start location.
     * @param locB    The end location.
     * @param material The material to use for the display.
     */
    public DisplayEntityDebug(Location locA, Location locB, Material material) {
        this.locationA = adjustLocation(locA);
        this.material = material;
        this.vector = locB.toVector().subtract(locA.toVector());
        this.length = vector.length();
        init();
    }

    /**
     * Creates a debug line display entity representing a vector direction from a starting location.
     *
     * @param vector         The direction vector.
     * @param startLocation  The location to start the vector.
     * @param material       The material to use for the display.
     */
    public DisplayEntityDebug(Vector vector, Location startLocation, Material material) {
        this.vector = vector;
        this.locationA = adjustLocation(startLocation);
        this.material = material;
        this.length = vector.length();
        init();
    }

    /**
     * Creates a debug line display entity representing the normal of a plane at a given location.
     *
     * @param locA     The location to place the debug display.
     * @param material The material to use for the display.
     * @param length   The length of the debug line.
     */
    public DisplayEntityDebug(Location locA, Material material, double length) {
        this.vector = new Vector(0, 1, 0);
        this.locationA = adjustLocation(locA);
        this.material = material;
        this.length = length;
        init();
    }

    private void init() {
        this.display = locationA.getWorld().spawn(locationA, BlockDisplay.class, display -> {
            display.setBlock(Bukkit.createBlockData(material));
            display.setTransformation(getTransformation());
            display.setInterpolationDelay(0);
            display.setInterpolationDuration(1);
            display.setTeleportDuration(1);
        });
    }

    private Location adjustLocation(Location loc) {
        return loc.clone().add(-OFFSET, -OFFSET, 0);
    }

    private Transformation getTransformation() {
        Vector3f scale = new Vector3f(0.1f, 0.1f, (float) length);
        Quaternionf rotation = calculateRotation(vector);
        Vector3f translation = new Vector3f(
                (float) locationA.getX() + (float) OFFSET,
                (float) locationA.getY() + (float) OFFSET,
                (float) locationA.getZ()
        );

        return new Transformation(translation, rotation, scale, new Quaternionf());
    }

    private Quaternionf calculateRotation(Vector vector) {
        Vector3f direction = new Vector3f((float) vector.getX(), (float) vector.getY(), (float) vector.getZ()).normalize();
        return new Quaternionf().rotateTo(new Vector3f(0, 0, 1), direction);
    }

    private void update() {
        if (display != null) {
            display.setTransformation(getTransformation());
        }
    }

    /**
     * Updates the starting location of the display entity.
     *
     * @param location The new starting location.
     */
    public void updateLocationA(Location location) {
        if (display != null) {
            this.locationA = adjustLocation(location);
            this.vector = vector.clone().multiply(length);
            this.length = vector.length();
            update();
        }
    }

    /**
     * Updates the ending location of the debug line.
     *
     * @param location The new ending location.
     */
    public void updateLocationB(Location location) {
        if (display != null) {
            this.vector = location.toVector().subtract(locationA.toVector());
            this.length = vector.length();
            update();
        }
    }

    /**
     * Updates the vector direction of the debug line.
     *
     * @param vector The new direction vector.
     */
    public void updateVector(Vector vector) {
        if (display != null) {
            this.vector = vector;
            this.length = vector.length();
            update();
        }
    }

    /**
     * Sets the material of the display entity.
     *
     * @param material The material to set.
     */
    public void setMaterial(Material material) {
        if (display != null && this.material != material) {
            this.material = material;
            display.setBlock(Bukkit.createBlockData(material));
            update();
        }
    }

    /**
     * Discards the display entity, nullifying the reference.
     */
    public void discard() {
        if (display != null) {
            display.remove();
            display = null;
        }
    }

    /**
     * Checks if the display entity is valid (not null).
     *
     * @return true if the display entity is valid, false otherwise.
     */
    public boolean isValid() {
        return display != null;
    }
}
