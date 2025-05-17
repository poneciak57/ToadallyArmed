package org.toadallyarmed.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.util.rendering.AnimatedSprite;

import java.util.Arrays;

public class HedgehogAnimationFactory {
    final float FRAME_DURATION          = 0.12f;
    final Vector2 OFFSET                = new Vector2(0, 0.1F);
    final Vector2 BASE_DIMENSIONS       = new Vector2(1.5F, 1.5F);


    HedgehogAnimationFactory() {}

    AnimatedSprite Animation(Texture texture, int index, int from, int to){
        TextureRegion[][] framesGrid;
        TextureRegion[] frames;
        Animation<TextureRegion> animation;

        framesGrid = TextureRegion.split(texture,
            texture.getWidth() / 6,
            texture.getHeight() / 4);

        frames = Arrays.copyOfRange(framesGrid[index], from, to);
        reverseInPlace(frames);
        animation = new Animation<>(FRAME_DURATION, frames);
        return new AnimatedSprite(animation, OFFSET, BASE_DIMENSIONS);
    }

    private void reverseInPlace(TextureRegion[] array) {
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            TextureRegion temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }
    }
}
