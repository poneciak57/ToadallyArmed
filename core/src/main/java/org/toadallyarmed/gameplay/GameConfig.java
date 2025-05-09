package org.toadallyarmed.gameplay;

public record GameConfig(
    // -- TickRates -- //
    int SystemManagerTickRate,
    int GlobalIncomeTickRate,

    // -- Values -- //

    // how much player will earn periodically
    int GlobalIncomeDelta

){}
