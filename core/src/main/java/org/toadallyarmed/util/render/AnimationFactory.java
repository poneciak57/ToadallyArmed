package org.toadallyarmed.util.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;

public class AnimationFactory {
    final float frameDuration;
    final Vector2 offset, baseDimensions;
    final int width, height;
    final boolean reverse;

    public AnimationFactory(AnimationConfig config) {
        frameDuration=config.frameDuration();
        offset=config.offset();
        baseDimensions=config.baseDimensions();
        width=config.width();
        height=config.height();
        reverse=config.reversed();
    }

    public AnimatedSprite Animation(Texture texture, int index, int from, int to){
        TextureRegion[][] framesGrid;
        TextureRegion[] frames;
        Animation<TextureRegion> animation;

        framesGrid = TextureRegion.split(texture,
            texture.getWidth() / width,
            texture.getHeight() / height);

        frames = Arrays.copyOfRange(framesGrid[index], from, to);
        if (reverse) reverseInPlace(frames);
        animation = new Animation<>(frameDuration, frames);
        return new AnimatedSprite(animation, offset, baseDimensions);
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
