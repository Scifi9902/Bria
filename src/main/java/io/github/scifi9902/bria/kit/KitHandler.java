package io.github.scifi9902.bria.kit;

import io.github.scifi9902.bria.handlers.IHandler;
import io.github.scifi9902.bria.kit.impl.PVPKit;
import lombok.Getter;

import java.util.*;

@Getter
public class KitHandler implements IHandler {

    private final Map<UUID, Kit> equiptMap = new HashMap<>();

    private final List<Kit> kits = new ArrayList<>();

    public Optional<Kit> getKit(String name) {
        return this.kits.stream().filter(kit -> kit.getName().equalsIgnoreCase(name)).findFirst();
    }

    @Override
    public void load() {
        this.kits.add(new PVPKit());
    }
}
