package org.toadallyarmed.gameplay;

public record GameConfig(
    // -- TickRates -- //
    int SystemManagerTickRate,
    int GlobalIncomeTickRate,
    int CollisionSystemTickRate,
    int HealthTickRate,

    // -- Values -- //
    FrogConfig knightFrog,
    FrogConfig moneyFrog,
    FrogConfig tankFrog,
    FrogConfig wizardFrog,

    HedgehogConfig basicHedgehog,
    HedgehogConfig fastHedgehog,
    HedgehogConfig strongHedgehog,
    HedgehogConfig healthyHedgehog,

    // how much player will earn periodically
    int GlobalIncomeDelta

){}
