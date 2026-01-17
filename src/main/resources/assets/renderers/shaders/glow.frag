#version 330 core

in vec2 v_TexCoord;
in vec2 v_OneTexel;

uniform sampler2D u_Texture;
uniform sampler2D u_Depth;

layout (std140) uniform GlowUniforms {
    int target;
    int radius;
    vec4 lineColor;
    vec4 fillColor;
};

out vec4 color;

bool isInnerRegion(vec2 uv) {
    if (target == 0) {
        return texture(u_Depth, uv).r < 1.0;
    } else {
        return texture(u_Texture, uv).a != 0.0;
    }
}

// boolean checker only
bool isOutlineRegion(vec2 uv) {
    return isInnerRegion(uv);
}

void main() {
    vec4 gameTexture = texture(u_Texture, v_TexCoord);

    if (isInnerRegion(v_TexCoord)) {
        color = fillColor;
        return;
    }

    float best = 0.0;

    for (int x = -radius; x <= radius; x++) {
        for (int y = -radius; y <= radius; y++) {
            vec2 offset = vec2(x, y) * v_OneTexel;

            if (isOutlineRegion(v_TexCoord + offset)) {
                float dist = length(vec2(x, y));
                float t = 1.0 - (dist / float(radius));
                best = max(best, t);
            }
        }
    }

    if (best > 0.0) {
        color = mix(gameTexture, lineColor, best);
        return;
    }

    color = gameTexture;
}
