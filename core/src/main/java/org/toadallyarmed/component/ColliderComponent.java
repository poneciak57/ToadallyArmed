package org.toadallyarmed.component;

import org.toadallyarmed.component.interfaces.ColliderActionEntry;
import org.toadallyarmed.component.interfaces.Component;

import java.util.List;

public record ColliderComponent(List<ColliderActionEntry> entries) implements Component { }
