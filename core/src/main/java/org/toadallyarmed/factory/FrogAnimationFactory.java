package org.toadallyarmed.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.util.rendering.AnimatedSprite;

import java.util.Arrays;

public class FrogAnimationFactory {
    final float FRAME_DURATION          = 0.08f;
    final Vector2 OFFSET                = new Vector2(-0.4f, -0.53f);
    final Vector2 BASE_DIMENSIONS       = new Vector2(2F, 2F);


    FrogAnimationFactory() {}

    AnimatedSprite Animation(Texture texture, int index, int from, int to){
        TextureRegion[][] framesGrid;
        TextureRegion[] frames;
        Animation<TextureRegion> animation;

        framesGrid = TextureRegion.split(texture,
            texture.getWidth() / 9,
            texture.getHeight() / 5);

        frames = Arrays.copyOfRange(framesGrid[index], from, to);
        animation = new Animation<>(FRAME_DURATION, frames);
        return new AnimatedSprite(animation, OFFSET, BASE_DIMENSIONS);
    }
}
