package org.toadallyarmed.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.config.AnimationConfig;
import org.toadallyarmed.util.rendering.AnimatedSprite;

import java.util.Arrays;

public class AnimationFactory {
    final float FRAME_DURATION;
    final Vector2 OFFSET, BASE_DIMENSIONS;
    final int width, height;
    final boolean reverse;

    AnimationFactory(AnimationConfig config) {
        FRAME_DURATION=config.FRAME_DURATION();
        OFFSET=config.OFFSET();
        BASE_DIMENSIONS=config.BASE_DIMENSIONS();
        width=config.width();
        height=config.height();
        reverse=config.reversed();
    }

    AnimatedSprite Animation(Texture texture, int index, int from, int to){
        TextureRegion[][] framesGrid;
        TextureRegion[] frames;
        Animation<TextureRegion> animation;

        framesGrid = TextureRegion.split(texture,
            texture.getWidth() / width,
            texture.getHeight() / height);

        frames = Arrays.copyOfRange(framesGrid[index], from, to);
        if (reverse) reverseInPlace(frames);
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
