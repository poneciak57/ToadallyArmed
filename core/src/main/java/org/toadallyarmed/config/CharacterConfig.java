package org.toadallyarmed.config;

public record CharacterConfig(
    int hp,
    int damage,
    float action_speed,
    float speed,
    int cost,

    // range for attacks for most of the characters
    float attackRange,
    BulletConfig bulletConfig
) {
}
