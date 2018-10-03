attribute vec3 inPosition;
attribute vec3 inNormal;

uniform mat4 g_WorldViewProjectionMatrix;
uniform mat4 g_WorldMatrix;
uniform mat3 g_WorldMatrixInverseTranspose;

#if NUM_PROJECTORS > 0
  uniform mat4 m_ProjectorViewProjectionMatrix0;

  #ifdef IS_PARALLEL_PROJECTION0
    uniform vec3 m_ProjectorDirection0;
  #else
    uniform vec3 m_ProjectorLocation0;
  #endif

  varying vec4 projCoord0;
  varying float cosAngle0;
#endif

#if NUM_PROJECTORS > 1
  uniform mat4 m_ProjectorViewProjectionMatrix1;

  #ifdef IS_PARALLEL_PROJECTION1
    uniform vec3 m_ProjectorDirection1;
  #else
    uniform vec3 m_ProjectorLocation1;
  #endif

  varying vec4 projCoord1;
  varying float cosAngle1;
#endif

#if NUM_PROJECTORS > 2
  uniform mat4 m_ProjectorViewProjectionMatrix2;

  #ifdef IS_PARALLEL_PROJECTION2
    uniform vec3 m_ProjectorDirection2;
  #else
    uniform vec3 m_ProjectorLocation2;
  #endif

  varying vec4 projCoord2;
  varying float cosAngle2;
#endif

#if NUM_PROJECTORS > 3
  uniform mat4 m_ProjectorViewProjectionMatrix3;

  #ifdef IS_PARALLEL_PROJECTION3
    uniform vec3 m_ProjectorDirection3;
  #else
    uniform vec3 m_ProjectorLocation3;
  #endif

  varying vec4 projCoord3;
  varying float cosAngle3;
#endif

#if NUM_PROJECTORS > 4
  uniform mat4 m_ProjectorViewProjectionMatrix4;

  #ifdef IS_PARALLEL_PROJECTION4
    uniform vec3 m_ProjectorDirection4;
  #else
    uniform vec3 m_ProjectorLocation4;
  #endif

  varying vec4 projCoord4;
  varying float cosAngle4;
#endif

#if NUM_PROJECTORS > 5
  uniform mat4 m_ProjectorViewProjectionMatrix5;

  #ifdef IS_PARALLEL_PROJECTION5
    uniform vec3 m_ProjectorDirection5;
  #else
    uniform vec3 m_ProjectorLocation5;
  #endif

  varying vec4 projCoord5;
  varying float cosAngle5;
#endif

#if NUM_PROJECTORS > 6
  uniform mat4 m_ProjectorViewProjectionMatrix6;

  #ifdef IS_PARALLEL_PROJECTION6
    uniform vec3 m_ProjectorDirection6;
  #else
    uniform vec3 m_ProjectorLocation6;
  #endif

  varying vec4 projCoord6;
  varying float cosAngle6;
#endif

#if NUM_PROJECTORS > 7
  uniform mat4 m_ProjectorViewProjectionMatrix7;

  #ifdef IS_PARALLEL_PROJECTION7
    uniform vec3 m_ProjectorDirection7;
  #else
    uniform vec3 m_ProjectorLocation7;
  #endif

  varying vec4 projCoord7;
  varying float cosAngle7;
#endif

void main() 
{
  gl_Position = g_WorldViewProjectionMatrix * vec4(inPosition, 1.0);
  vec4 wsPosition = g_WorldMatrix * vec4(inPosition, 1.0);
  vec3 wsNormal = g_WorldMatrixInverseTranspose * inNormal;

  #if NUM_PROJECTORS > 0
    projCoord0 = m_ProjectorViewProjectionMatrix0 * wsPosition;

    #ifdef IS_PARALLEL_PROJECTION0
      cosAngle0 = dot(wsNormal, -m_ProjectorDirection0);
    #else 
      cosAngle0 = dot(wsNormal, normalize(m_ProjectorLocation0 - vec3(wsPosition)));
    #endif
  #endif

  #if NUM_PROJECTORS > 1
    projCoord1 = m_ProjectorViewProjectionMatrix1 * wsPosition;

    #ifdef IS_PARALLEL_PROJECTION1
      cosAngle1 = dot(wsNormal, -m_ProjectorDirection1);
    #else 
      cosAngle1 = dot(wsNormal, normalize(m_ProjectorLocation1 - vec3(wsPosition)));
    #endif
  #endif

  #if NUM_PROJECTORS > 2
    projCoord2 = m_ProjectorViewProjectionMatrix2 * wsPosition;

    #ifdef IS_PARALLEL_PROJECTION2
      cosAngle2 = dot(wsNormal, -m_ProjectorDirection2);
    #else 
      cosAngle2 = dot(wsNormal, normalize(m_ProjectorLocation2 - vec3(wsPosition)));
    #endif
  #endif

  #if NUM_PROJECTORS > 3
    projCoord3 = m_ProjectorViewProjectionMatrix3 * wsPosition;

    #ifdef IS_PARALLEL_PROJECTION3
      cosAngle3 = dot(wsNormal, -m_ProjectorDirection3);
    #else 
      cosAngle3 = dot(wsNormal, normalize(m_ProjectorLocation3 - vec3(wsPosition)));
    #endif
  #endif

  #if NUM_PROJECTORS > 4
    projCoord4 = m_ProjectorViewProjectionMatrix4 * wsPosition;

    #ifdef IS_PARALLEL_PROJECTION4
      cosAngle4 = dot(wsNormal, -m_ProjectorDirection4);
    #else 
      cosAngle4 = dot(wsNormal, normalize(m_ProjectorLocation4 - vec3(wsPosition)));
    #endif
  #endif

  #if NUM_PROJECTORS > 5
    projCoord5 = m_ProjectorViewProjectionMatrix5 * wsPosition;

    #ifdef IS_PARALLEL_PROJECTION5
      cosAngle5 = dot(wsNormal, -m_ProjectorDirection5);
    #else 
      cosAngle5 = dot(wsNormal, normalize(m_ProjectorLocation5 - vec3(wsPosition)));
    #endif
  #endif

  #if NUM_PROJECTORS > 6
    projCoord6 = m_ProjectorViewProjectionMatrix6 * wsPosition;

    #ifdef IS_PARALLEL_PROJECTION6
      cosAngle6 = dot(wsNormal, -m_ProjectorDirection6);
    #else 
      cosAngle6 = dot(wsNormal, normalize(m_ProjectorLocation6 - vec3(wsPosition)));
    #endif
  #endif

  #if NUM_PROJECTORS > 7
    projCoord7 = m_ProjectorViewProjectionMatrix7 * wsPosition;

    #ifdef IS_PARALLEL_PROJECTION7
      cosAngle7 = dot(wsNormal, -m_ProjectorDirection7);
    #else 
      cosAngle7 = dot(wsNormal, normalize(m_ProjectorLocation7 - vec3(wsPosition)));
    #endif
  #endif
}
