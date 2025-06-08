package org.toadallyarmed.entity;

public enum EntityType {
    FROG(1),
    BULLET(1),
    HEDGEHOG(0),
    COIN(1),
    WINNING,
    LOSING,
    OTHER;

    EntityType() { this(0); }
    EntityType(int renderOrder) { this.renderOrder = renderOrder; }
    public int renderOrder() { return renderOrder; }
    private final int renderOrder;
}
