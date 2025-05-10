package org.toadallyarmed.factory;

import org.toadallyarmed.config.CharacterConfig;
import org.toadallyarmed.config.GameConfig;

public class DifficultyFactory {
    public GameConfig defaultGameConfig() {
        return new GameConfig(
            60,
            25,
            5,
            5,
            5,
            new CharacterConfig( //knight
                300, 20, 1, 0, 75
            ), new CharacterConfig( //money
                50, 25 /*he gives money not damage*/, 7, 0, 50
            ), new CharacterConfig(//tank
                1000, 0, 0, 0, 50
            ), new CharacterConfig(//wizard
                50, 25, 1.5f, 0, 125
            ), new CharacterConfig(//basic
                100, 25, 1.5f, 15, 0
            ), new CharacterConfig(//fast
                60, 25, 1, 7.5f, 0
            ), new CharacterConfig(//strong
                100, 100, 1, 20, 0
            ), new CharacterConfig(//healthy
                300, 20, 2, 10, 0
            ), 10
        );
    }
}
