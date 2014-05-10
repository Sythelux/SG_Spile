attribute vec3 inPosition;
uniform mat4 g_WorldViewProjectionMatrix;
uniform mat4 g_WorldViewMatrix;
uniform float g_Time;

varying vec3 vertexPosition;
/*
* vertex shader template
*/
void main() { 
    // Vertex transformation 

    vec4 position = vec4(inPosition, 1.0);

    position.y = sin(inPosition.x + g_Time);
    position.y = sin(inPosition.z + g_Time);

    vertexPosition = position * g_WorldViewMatrix;

    gl_Position = g_WorldViewProjectionMatrix * position;
}
