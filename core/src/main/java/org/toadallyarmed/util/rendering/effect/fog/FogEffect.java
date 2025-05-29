package org.toadallyarmed.util.rendering.effect.fog;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.util.rendering.Renderer;


public class FogEffect {
    final private Mesh mesh;
    final Vector2 canvaResolution;
    final float guiHeight;

    public FogEffect(Vector2 canvaResolution, float guiHeight) {
        this.canvaResolution = canvaResolution;
        this.guiHeight = guiHeight;

        mesh = new Mesh(true, 4, 6, VertexAttribute.Position());
        mesh.setVertices(new float[] {
            -1.0f, -1.0f, 0, // Bottom left
            1.0f, -1.0f, 0,  // Bottom right
            1.0f, 1.0f, 0,   // Top right
            -1.0f, 1.0f, 0,  // Top left
        });
        mesh.setIndices(new short[] {0, 1, 2, 2, 3, 0});
    }

    public void render(Renderer renderer) {
        Matrix4 mat = new Matrix4();

        ShaderProgram.pedantic = false;
        ShaderProgram shader = renderer.getFogEffectShader();
        shader.bind();
        shader.setUniformf("u_canvaResolution", canvaResolution);
        shader.setUniformf("u_guiHeight", guiHeight);
        mesh.render(shader, GL20.GL_TRIANGLES);
        renderer.getDefaultShader().bind();
    }
}
