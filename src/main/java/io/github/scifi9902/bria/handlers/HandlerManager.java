package io.github.scifi9902.bria.handlers;

import io.github.scifi9902.bria.BriaPlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HandlerManager {

    private Map<Class<? extends IHandler>, IHandler> map = new HashMap<>();

    /**
     * @param handler handler we want to register
     */
    public void registerHandler(IHandler handler) {
        //Check if the handlers null
        if (handler == null) {
            throw new IllegalArgumentException("Handler passed into the handler manager is null");
        }

        //Put the handler inside of the map
        map.put(handler.getClass(), handler);
        //Load the handler
        handler.load();
    }

    /**
     * @param tClass type of class to search for
     * @param <T>    type of handler
     * @return the found handler
     */
    public <T> T getHandler(Class<T> tClass) {
        return (T) this.map.get(tClass);
    }

    /**
     * @return all of the handler instances
     */
    public Collection<IHandler> getHandlers() {
        return this.map.values();
    }

}
