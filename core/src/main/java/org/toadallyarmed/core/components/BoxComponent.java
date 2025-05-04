package org.toadallyarmed.core.components;

public class BoxComponent extends Component {
    float width; // Provided in world coordinates
    float height; // Provided in world coordinates

    public BoxComponent(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
