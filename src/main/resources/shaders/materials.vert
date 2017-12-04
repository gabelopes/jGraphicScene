#version 430 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 normalVector;
layout (location = 2) in vec2 texturePosition;

out vec3 fragmentPosition;
out vec3 normal;
out vec2 textureCoordinates;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main()
{
    fragmentPosition = vec3(model * vec4(position, 1.0));
    normal = mat3(transpose(inverse(model))) * normalVector;
    textureCoordinates = texturePosition;

    gl_Position = projection * view * vec4(fragmentPosition, 1.0);
}