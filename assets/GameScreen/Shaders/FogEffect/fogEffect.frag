#ifdef GL_ES
    precision mediump float;
#endif
varying vec2 v_texCoords;
varying vec2 v_pos;

uniform vec2 u_canvaResolution;
uniform float u_guiHeight;
uniform float u_currentNanoTime;

float map(in float value, in float inMin, in float inMax, in float outMin, in float outMax) {
    return outMin + (outMax - outMin) * (value - inMin) / (inMax - inMin);
}

// 2D Random
float random (in vec2 st) {
    return fract(sin(dot(st.xy,
                         vec2(12.9898,78.233)))
                 * 43758.5453123);
}

// 2D Noise based on Morgan McGuire @morgan3d
// https://www.shadertoy.com/view/4dS3Wd
float noise (in vec2 st) {
    vec2 i = floor(st);
    vec2 f = fract(st);

    // Four corners in 2D of a tile
    float a = random(i);
    float b = random(i + vec2(1.0, 0.0));
    float c = random(i + vec2(0.0, 1.0));
    float d = random(i + vec2(1.0, 1.0));

    // Cubic Hermine Curve.  Same as SmoothStep()
    vec2 u = f*f*(3.0-2.0*f);

    // Mix 4 coorners percentages
    return mix(a, b, u.x) +
    (c - a)* u.y * (1.0 - u.x) +
    (d - b) * u.x * u.y;
}

// Source: https://thebookofshaders.com/13/
#define OCTAVES 6
float fbm (in vec2 st) {
    float value = 0.0;
    float amplitude = .5;
    float frequency = 0.;

    for (int i = 0; i < OCTAVES; i++) {
        value += amplitude * noise(st);
        st *= 2.;
        amplitude *= .5;
    }
    return value;
}

#define RELATIVE_BEG_X 0.4

void main() {
    vec2 relativePos = v_pos;
    vec2 worldPos = (relativePos + 1.0) / 2.0 * u_canvaResolution;
    float advancement = (relativePos.x - RELATIVE_BEG_X ) / (1.0 - RELATIVE_BEG_X);
    if (relativePos.x > RELATIVE_BEG_X && worldPos.y < u_canvaResolution.y - u_guiHeight) {
        float val = fbm(floor(worldPos / 4.0) * 10.0);
        float mult = 1.0 - advancement;
        mult = mult * mult;
        mult = 1.0 - mult;
        val *= mult;
        if (val < 0.4) val = 0.0;
        gl_FragColor = vec4(val, val, val, val);
    } else
        gl_FragColor = vec4(0);
};
