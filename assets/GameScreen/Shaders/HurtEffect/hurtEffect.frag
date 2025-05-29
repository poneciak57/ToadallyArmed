#ifdef GL_ES
    precision mediump float;
#endif
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform float ratio;

void main() {
    vec4 textureColor = texture2D(u_texture, v_texCoords);
    gl_FragColor = mix(textureColor, vec4(1, 1, 1, textureColor.a), ratio);
};
