#ifdef GL_ES
    precision mediump float;
#endif
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform float hurtRatio;
uniform float fadeRatio;

void main() {
    vec4 textureColor = texture2D(u_texture, v_texCoords);
    gl_FragColor = mix(textureColor, vec4(1, 1, 1, textureColor.a), hurtRatio);
    float modFadeRatio = 1.0 - fadeRatio;
    if (gl_FragColor.a > 0.0)
        gl_FragColor.a = mix(gl_FragColor.a, 0.0, fadeRatio);
};
