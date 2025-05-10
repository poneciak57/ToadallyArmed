package org.toadallyarmed.config;

public record GameConfig(
    // -- TickRates -- //
    int SystemManagerTickRate,
    int GlobalIncomeTickRate,
    int HealthTickRate,

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
){}
