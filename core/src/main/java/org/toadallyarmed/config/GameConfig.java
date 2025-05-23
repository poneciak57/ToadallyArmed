package org.toadallyarmed.config;

public record GameConfig(
    // -- TickRates -- //
    float SystemManagerTickRate,
    float GlobalIncomeSystemTickRate,
    float CollisionSystemTickRate,
    float PhysicsSystemTickRate,
    float HealthSystemTickRate,
    float BulletSystemTickRate,
    float ActionSystemTickRate,
    float EnemyRenderSystemTickRate,
    int EnemyRenderSystemQuantity,

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
    public static final float VELOCITY_SCALE = 0.005f;
}
