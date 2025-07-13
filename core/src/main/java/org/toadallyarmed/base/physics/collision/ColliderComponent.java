package org.toadallyarmed.base.physics.collision;

import org.toadallyarmed.base.component.Component;

import java.util.List;

public record ColliderComponent(List<ColliderActionEntry> entries) implements Component { }
