package org.toadallyarmed.config;

public record GameConfig(
    // -- TickRates -- //
    int SystemManagerTickRate,
    int GlobalIncomeSystemTickRate,
    int CollisionSystemTickRate,
    int PhysicsSystemTickRate,
    int HealthSystemTickRate,
    int BulletSystemTickRate,

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
