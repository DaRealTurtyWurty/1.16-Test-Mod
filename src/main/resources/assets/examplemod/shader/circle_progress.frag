uniform vec3      iResolution;           // viewport resolution (in pixels)
uniform float     iTime;                 // shader playback time (in seconds)

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    float duration = 50.0;
    float progress = fract(iTime / duration);
    
    float innerRadius = 100.0;
    float outerRadius = 200.0;
    
    float middleRadius = 0.5 * (innerRadius + outerRadius);
    float halfWidth = 0.5 * (outerRadius - innerRadius);
    
    vec2 pos = fragCoord.xy - 0.5 * iResolution.xy;
    float radius = length(pos.xy);
	
	float fr = halfWidth - abs(radius - middleRadius) + 1.0; // radius double edge function
	//if(fr < 0.0) discard;
    fr = clamp(fr, 0.0, 1.0);
	
	float angle = degrees(atan(-pos.x, -pos.y)) + 180.0; // angle clockwise from the top
	float fa = radians(angle - progress * 360.0) * radius + 1.0; // angle edge function
    fa = clamp(fa, 0.0, 1.0);
	
	// Compute mixed bar color with antialiasing
    vec4 color = vec4(0.8,0.9,1,1);
    vec4 color2 = vec4(0.7,0.6,0.5,1);
    vec4 col = mix(color, color2, fa);
	col.a *= fr;
    
    // Blend with background
    vec4 bgColor = vec4(0.1,0.3,0.2,1);
    col = mix(bgColor, col, col.a);
	
	fragColor = col;
}