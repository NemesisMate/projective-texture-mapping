attribute vec3 inPosition;
attribute vec3 inNormal;

uniform mat4 g_WorldViewProjectionMatrix;
uniform mat4 g_WorldMatrix;
uniform mat3 g_WorldMatrixInverseTranspose;
uniform mat4 m_ProjectorViewProjectionMatrix;

#ifdef IS_PARALLEL_PROJECTION
  uniform vec3 m_ProjectorDirection;
#else
  uniform vec3 m_ProjectorLocation;
#endif

varying vec4 projCoord;
varying float cosAngle;

void main() 
{
  gl_Position = g_WorldViewProjectionMatrix * vec4(inPosition, 1.0);
  vec4 wsPosition = g_WorldMatrix * vec4(inPosition, 1.0);
  vec3 wsNormal = g_WorldMatrixInverseTranspose * inNormal;

  projCoord = m_ProjectorViewProjectionMatrix * wsPosition;

  #ifdef IS_PARALLEL_PROJECTION
    cosAngle = dot(wsNormal, -m_ProjectorDirection);
  #else
    cosAngle = dot(wsNormal, normalize(m_ProjectorLocation - vec3(wsPosition)));
  #endif
}
