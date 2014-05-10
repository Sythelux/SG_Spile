#ifdef COLOR
    uniform vec4 m_Color;
#endif
varying vec3 vertexPosition;
/*
* fragment shader template
*/
void main() {

    vec4 color = vec4(vertexPosition, 1.0);

    #ifdef COLOR
        color = m_Color;
    #endif

    gl_FragColor = color;  
}

