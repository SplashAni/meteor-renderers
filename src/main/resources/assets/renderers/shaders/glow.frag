#version 330 core

in vec2 v_TexCoord;

uniform sampler2D u_Depth;

out vec4 color;

void main() {
    float depth = texture(u_Depth, v_TexCoord).r;

    color = vec4(vec3(depth), 1.0); // debugging the depth bro
}
