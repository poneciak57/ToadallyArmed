package org.toadallyarmed.config;

import org.toadallyarmed.system.level.Level;

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
    int EnemySpawnerSystemWaveQuantity,
    int EnemySpawnerSystemHedgehogsPerWave,

    // -- Values -- //
    CharacterConfig knightFrog,
    CharacterConfig bardFrog,
    CharacterConfig tankFrog,
    CharacterConfig wizardFrog,

    CharacterConfig basicHedgehog,
    CharacterConfig fastHedgehog,
    CharacterConfig strongHedgehog,
    CharacterConfig healthyHedgehog,

    // how much player will earn periodically
    int StartingMoney,
    int GlobalIncomeDelta,

    Level level
) {
    public static final float VELOCITY_SCALE = 0.05f;
    public static final float TILE_WIDTH = 1f;
    public static final float TILE_HEIGHT = 1f;
    public static final int BOARD_WIDTH = 11;
    public static final int BOARD_HEIGHT = 5;
}
