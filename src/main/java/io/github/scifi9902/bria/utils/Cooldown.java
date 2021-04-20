package io.github.scifi9902.bria.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cooldown {

    private final Map<UUID, Long> map = new HashMap<>();

    public void placeOnCooldown(UUID uuid, long duration) {
        this.map.put(uuid, (System.currentTimeMillis() + duration));
    }

    public boolean isActive(UUID uuid) {
        return this.map.containsKey(uuid) && this.map.get(uuid) >= System.currentTimeMillis();
    }

    public long getRemaining(UUID uuid) {
        return this.map.get(uuid) - System.currentTimeMillis();
    }

}
