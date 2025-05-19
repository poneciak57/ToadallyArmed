package org.toadallyarmed.component;

import org.toadallyarmed.component.interfaces.ColliderActionEntry;
import org.toadallyarmed.component.interfaces.Component;

import java.util.List;

public class ColliderComponent implements Component {
    private final List<ColliderActionEntry> entries;

    public ColliderComponent(List<ColliderActionEntry> entries) {
        this.entries = entries;
    }

    public List<ColliderActionEntry> getEntries() {
        return entries;
    }
}
