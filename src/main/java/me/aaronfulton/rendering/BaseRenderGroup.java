package me.aaronfulton.rendering;

import me.aaronfulton.rendering.entities.BaseBlockEntity;

import java.util.ArrayList;
import java.util.List;

public class BaseRenderGroup implements iRenderGroup {
    private final List<BaseBlockEntity> entities = new ArrayList<>();
    private int tickRate = 20; // Default tick rate (1 second for 20 ticks).
    private int tickCounter = 0;

    @Override
    public void addEntity(BaseBlockEntity entity) {
        entities.add(entity);
    }

    @Override
    public void removeEntity(BaseBlockEntity entity) {
        entities.remove(entity);
    }

    @Override
    public BaseBlockEntity getEntity(String key) {
        for (BaseBlockEntity entity : entities) {
            if (key.equals(entity.getIdentifier())) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public void setTickRate(int tickRate) {
        this.tickRate = tickRate;
    }

    @Override
    public int getTickRate() {
        return tickRate;
    }

    @Override
    public void update() {
        if (++tickCounter < tickRate) return;

        for (BaseBlockEntity entity : entities) {
            entity.update();
        }
        tickCounter = 0;
    }

    @Override
    public void render() {
        for (BaseBlockEntity entity : entities) {
            entity.render();
        }
    }

    public void murderAllEntities() {
        for (BaseBlockEntity entity : entities) {
            entity.murder();
        }
        entities.clear();
    }
}
