package me.aaronfulton.rendering;

import me.aaronfulton.rendering.entities.BaseBlockEntity;

public interface iRenderGroup {

    /**
     * Adds an entity to the render group.
     *
     * @param entity The entity to add.
     */
    void addEntity(BaseBlockEntity entity);

    /**
     * Removes an entity from the render group.
     *
     * @param entity The entity to remove.
     */
    void removeEntity(BaseBlockEntity entity);

    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param key The unique identifier of the entity.
     * @return The entity, or null if not found.
     */
    BaseBlockEntity getEntity(String key);

    /**
     * Gets the tick rate for this group.
     *
     * @return The tick rate in ticks.
     */
    int getTickRate();

    /**
     * Sets the tick rate for this group.
     * <p>
     * Tick rate determines how often the update() method is executed.
     *
     * @param tickRate The tick rate in ticks.
     */
    void setTickRate(int tickRate);

    /**
     * Updates all entities in the group based on the tick rate.
     */
    void update();

    /**
     * Renders all visible entities in the group.
     */
    void render();
}
