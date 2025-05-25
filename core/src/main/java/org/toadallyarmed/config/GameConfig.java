package org.toadallyarmed.config;

public record GameConfig(
    // -- TickRates -- //
    float SystemManagerTickRate,
    float GlobalIncomeSystemTickRate,
    float CollisionSystemTickRate,
    float PhysicsSystemTickRate,
    float GarbageCollectorSystemTickRate,
    float BulletSystemTickRate,
    float ActionSystemTickRate,
    float EnemySpawnerSystemTickRate,
    float EnemySpawnerSystemSleep,
    int EnemySpawnerSystemQuantity,

    // -- Values -- //
    CharacterConfig knightFrog,
    CharacterConfig moneyFrog,
    CharacterConfig tankFrog,
    CharacterConfig wizardFrog,

    CharacterConfig basicHedgehog,
    CharacterConfig fastHedgehog,
    CharacterConfig strongHedgehog,
    CharacterConfig healthyHedgehog,

    // how much player will earn periodically
    int GlobalIncomeDelta
) {
    public static final float VELOCITY_SCALE = 0.05f;
    public static final float TILE_WIDTH = 1f;
    public static final float TILE_HEIGHT = 1f;
}
