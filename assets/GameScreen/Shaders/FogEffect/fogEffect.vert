attribute vec4 a_position;
attribute vec2 a_texCoord0;

uniform mat4 u_projTrans;

varying vec2 v_texCoords;
varying vec2 v_pos;

void main() {
    v_texCoords = a_texCoord0;
    gl_Position = a_position;
    v_pos = vec2(a_position.x, a_position.y);
}
