package me.aaronfulton.rendering;

import java.util.HashMap;
import java.util.Map;

public class RenderEngine {
    private Map<String, iRenderGroup> groups = new HashMap<>();
    private int tickRate = 1;

    public void addGroup(String key, iRenderGroup group) {
        groups.put(key, group);
    }

    public void removeGroup(String key) {
        groups.remove(key);
    }

    public iRenderGroup getGroup(String key) {
        return groups.get(key);
    }

    public int getTickRate() {
        return tickRate;
    }

    public void setTickRate(int tickRate) {
        this.tickRate = tickRate;
    }

    // update the entities in the gameWorld
    public void update() {
        for (iRenderGroup group : groups.values()) {
            group.update();
        }
    }

    public void render() {
        for (iRenderGroup group : groups.values()) {
            group.render();
        }
    }
}
