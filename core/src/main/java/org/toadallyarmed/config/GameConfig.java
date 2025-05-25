package org.toadallyarmed.config;

public record GameConfig(
    // -- TickRates -- //
    float systemManagerTickRate,
    float globalIncomeSystemTickRate,
    float collisionSystemTickRate,
    float physicsSystemTickRate,
    float healthSystemTickRate,
    float bulletSystemTickRate,
    float actionSystemTickRate,
    float enemySpawnerSystemTickRate,
    int enemySpawnerSystemQuantity,

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
    int globalIncomeDelta
) {
    public static final float VELOCITY_SCALE = 0.005f;
}
