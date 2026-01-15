#version 330 core

in vec2 v_TexCoord;
in vec2 v_OneTexel;


uniform sampler2D u_Texture;
uniform sampler2D u_Depth;

layout (std140) uniform GlowUniforms {
    int radius;
    vec4 lineColor;
    vec4 fillColor;
};

out vec4 color;

bool isHand(vec2 uv) {
    return texture(u_Depth, uv).r < 1.0;
}

void main() {
    vec4 gameTexture = texture(u_Texture, v_TexCoord);
    float depth = texture(u_Depth, v_TexCoord).r;

    if (depth < 1.0) {
        color = fillColor;
        return;
    }

    bool outline = false;

    for (int x = -radius; x <= radius; x++) {
        for (int y = -radius; y <= radius; y++) {
            vec2 offset = vec2(x, y) * v_OneTexel;
            if (isHand(v_TexCoord + offset)) {
                outline = true;
                break;
            }
        }
        if (outline) break;
    }

    if (outline) {
        color =lineColor;
    } else {
        color = gameTexture;
    }
}
