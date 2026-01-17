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

void main() {
    vec4 gameTexture = texture(u_Texture, v_TexCoord);

    if (isInnerRegion(v_TexCoord)) {
        color = fillColor;
        return;
    }

    float best = 0.0;
    float maxDist = float(radius);

    float closestDist = maxDist + 1.0;

    for (int x = -radius; x <= radius; x++) {
        for (int y = -radius; y <= radius; y++) {

            if (x*x + y*y > radius*radius) continue;

            vec2 offset = vec2(x, y) * v_OneTexel;

            if (isInnerRegion(v_TexCoord + offset)) {
                float dist = length(vec2(x, y));
                closestDist = min(closestDist, dist);
            }
        }
    }

    if (closestDist <= maxDist) {
        float glowIntensity = 1.0 - (closestDist / maxDist);
        color = mix(gameTexture, lineColor, glowIntensity);
        return;
    }

    color = gameTexture;
}
