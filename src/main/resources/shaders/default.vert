#version 430

 layout (location = 0) in vec3 position;
 layout (location = 1) in vec4 inputColor;

 uniform mat4 projection;
 uniform mat4 view;
 uniform mat4 model;

 out vec4 color;

 void main() {
     gl_Position = projection * view * model * vec4(position, 1.0);
     color = inputColor;
 }