package me.aaronfulton.rendering;

public interface IRenderEntity {

    /**
     * Performs logic updates for this entity.
     */
    void update();

    /**
     * Renders the entity, if it is visible.
     */
    void render();

    /**
     * Checks if the entity is currently visible.
     *
     * @return True if the entity is visible, otherwise false.
     */
    boolean isVisible();

    /**
     * Destroys the entity and releases associated resources.
     */
    void murder();
}
