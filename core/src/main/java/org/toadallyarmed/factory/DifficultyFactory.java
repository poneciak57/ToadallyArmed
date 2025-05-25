package org.toadallyarmed.factory;

import org.toadallyarmed.config.BulletConfig;
import org.toadallyarmed.config.CharacterConfig;
import org.toadallyarmed.config.GameConfig;

public class DifficultyFactory {
    public static GameConfig defaultGameConfig() {
        return new GameConfig(
            60,
            1f, //change to 0.1
            5f,
            5f,
            5,
            10,
            5,
            0.1f,
            2,
            15,
            new CharacterConfig( //knight
                300, 20, 1, 0, 75, 1, null
            ), new CharacterConfig( //money
                50, 25 /*he gives money not damage*/, 7, 0, 50, 0, null
            ), new CharacterConfig(//tank
                1000, 0, 0, 0, 50, 0, null
            ), new CharacterConfig(//wizard
                50, 25, 0.5f, 0, 125, 10, new BulletConfig(0.5f, 0f, 30)
            ), new CharacterConfig(//basic
                100, 25, 1.5f, -15, 0, -1, null
            ), new CharacterConfig(//fast
                60, 25, 1, -7.5f, 0, -1, null
            ), new CharacterConfig(//strong
                100, 100, 1, -20, 0, -1, null
            ), new CharacterConfig(//healthy
                300, 20, 2, -10, 0, -1, null
            ),
            25
        );
    }
}
