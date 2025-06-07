package org.toadallyarmed.factory;

import org.toadallyarmed.Main;
import org.toadallyarmed.config.BulletConfig;
import org.toadallyarmed.config.CharacterConfig;
import org.toadallyarmed.config.GameConfig;

public class DifficultyFactory {
    public static GameConfig defaultGameConfig(){
        return easy();
    }

    public static GameConfig easy(){
        return new GameConfig(
            60,
            0.1f,
            5f,
            1f,
            5,
            10,
            5,
            0.1f,
            3,
            3,
            new CharacterConfig( //knight
                300, 20, 4, 0, 75, 2, new BulletConfig(0.5f, 0f, 30)
            ), new CharacterConfig( //money
            50, 25 /*he gives money not damage*/, 0.1f, 0, 50, 0, null
        ), new CharacterConfig(//tank
            1000, 0, 0, 0, 50, 0, null
        ), new CharacterConfig(//wizard
            50, 30, 0.5f, 0, 125, 10, new BulletConfig(0.5f, 0f, 30)
        ), new CharacterConfig(//basic
            100, 25, 1.5f, -15, 0, -1, null
        ), new CharacterConfig(//fast
            60, 25, 1, -7.5f, 0, -1, null
        ), new CharacterConfig(//strong
            100, 100, 1, -20, 0, -1, null
        ), new CharacterConfig(//healthy
            300, 20, 2, -10, 0, -1, null
        ),
            50, 25
        );
    }
    public static GameConfig medium(){
        return new GameConfig(
            60,
            0.1f,
            5f,
            1f,
            5,
            10,
            5,
            0.3f,
            4,
            8,
            new CharacterConfig( //knight
                500, 20, 4, 0, 75, 2, new BulletConfig(0.5f, 0f, 30)
            ), new CharacterConfig( //bard
            100, 25 /*he gives money not damage*/, 0.1f, 0, 50, 0, null
        ), new CharacterConfig(//tank
            1000, 0, 0, 0, 50, 0, null
        ), new CharacterConfig(//wizard
            200, 30, 0.5f, 0, 125, 11, new BulletConfig(0.5f, 0f, 30)
        ), new CharacterConfig(//basic
            100, 25, 1.5f, -15, 0, -1, null
        ), new CharacterConfig(//fast
            60, 25, 1, -7.5f, 0, -1, null
        ), new CharacterConfig(//strong
            100, 100, 1, -20, 0, -1, null
        ), new CharacterConfig(//healthy
            300, 20, 1, -10, 0, -1, null
        ),
            25, 25
        );
    }
    public static GameConfig hard() {
        return new GameConfig(
            60,
            0.1f,
            5f,
            1f,
            5,
            10,
            5,
            0.3f,
            5,
            12,
            new CharacterConfig( //knight
                300, 20, 4, 0, 75, 2, new BulletConfig(0.5f, 0f, 30)
            ), new CharacterConfig( //money
            50, 25 /*he gives money not damage*/, 0.1f, 0, 50, 0, null
        ), new CharacterConfig(//tank
            1000, 0, 0, 0, 50, 0, null
        ), new CharacterConfig(//wizard
            50, 30, 0.5f, 0, 125, 10, new BulletConfig(0.5f, 0f, 30)
        ), new CharacterConfig(//basic
            100, 25, 1.5f, -15, 0, -1, null
        ), new CharacterConfig(//fast
            60, 30, 1, -7.5f, 0, -1, null
        ), new CharacterConfig(//strong
            100, 120, 1, -20, 0, -1, null
        ), new CharacterConfig(//healthy
            300, 30, 2, -10, 0, -1, null
        ),
            25, 25
        );
    }
    public static GameConfig devilish() {
        return new GameConfig(
            60,
            0.1f,
            5f,
            1f,
            5,
            10,
            5,
            0.1f,
            0,
            0,
            new CharacterConfig( //knight
                300, 20, 4, 0, 75, 2, new BulletConfig(0.5f, 0f, 30)
            ), new CharacterConfig( //money
            50, 25 /*he gives money not damage*/, 0.1f, 0, 50, 0, null
        ), new CharacterConfig(//tank
            1000, 0, 0, 0, 50, 0, null
        ), new CharacterConfig(//wizard
            50, 30, 0.5f, 0, 125, 10, new BulletConfig(0.5f, 0f, 30)
        ), new CharacterConfig(//basic
            100, 25, 1.5f, -15, 0, -1, null
        ), new CharacterConfig(//fast
            60, 25, 1, -7.5f, 0, -1, null
        ), new CharacterConfig(//strong
            100, 100, 1, -20, 0, -1, null
        ), new CharacterConfig(//healthy
            300, 20, 2, -10, 0, -1, null
        ),
            25, 25
        );
    }
}
