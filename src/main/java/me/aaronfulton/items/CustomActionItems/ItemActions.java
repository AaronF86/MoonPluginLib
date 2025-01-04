package me.aaronfulton.items.CustomActionItems;

import me.aaronfulton.EventHandlers.ItemEventHanders.ActionHandler;
import me.aaronfulton.EventHandlers.ItemEventHanders.RightClickHandler;

import java.util.Optional;

public enum ItemActions {
    RIGHT_CLICK(RightClickHandler.class),
    LEFT_CLICK(null),
    SHIFT_RIGHT_CLICK(RightClickHandler.class),
    SHIFT_LEFT_CLICK(null),
    HOTBAR_SWAP(null),
    SHIFT_HOTBAR_SWAP(null),
    DROP_ITEM(null),
    SHIFT_DROP_ITEM(null),
    PASSIVE_HOLD(null),
    PASSIVE_INV(null);

    private final Class<? extends ActionHandler> handlerClass;

    ItemActions(Class<? extends ActionHandler> handlerClass) {
        this.handlerClass = handlerClass;
    }

    public Optional<Class<? extends ActionHandler>> getHandlerClass() {
        return Optional.ofNullable(handlerClass);
    }
}
