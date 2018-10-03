#define BLEND_COLOR_ADD_ALPHA 0
#define BLEND_ALL 1

#if NUM_PROJECTORS > 0
  varying vec4 projCoord0;
  varying float cosAngle0;

  uniform sampler2D m_ProjectiveMap0;
  uniform float m_FallOffDistance0;
  uniform float m_FallOffPower0;
#endif

#if NUM_PROJECTORS > 1
  varying vec4 projCoord1;
  varying float cosAngle1;

  uniform sampler2D m_ProjectiveMap1;
  uniform float m_FallOffDistance1;
  uniform float m_FallOffPower1;
#endif

#if NUM_PROJECTORS > 2
  varying vec4 projCoord2;
  varying float cosAngle2;

  uniform sampler2D m_ProjectiveMap2;
  uniform float m_FallOffDistance2;
  uniform float m_FallOffPower2;
#endif

#if NUM_PROJECTORS > 3
  varying vec4 projCoord3;
  varying float cosAngle3;

  uniform sampler2D m_ProjectiveMap3;
  uniform float m_FallOffDistance3;
  uniform float m_FallOffPower3;
#endif

#if NUM_PROJECTORS > 4
  varying vec4 projCoord4;
  varying float cosAngle4;

  uniform sampler2D m_ProjectiveMap4;
  uniform float m_FallOffDistance4;
  uniform float m_FallOffPower4;
#endif

#if NUM_PROJECTORS > 5
  varying vec4 projCoord5;
  varying float cosAngle5;

  uniform sampler2D m_ProjectiveMap5;
  uniform float m_FallOffDistance5;
  uniform float m_FallOffPower5;
#endif

#if NUM_PROJECTORS > 6
  varying vec4 projCoord6;
  varying float cosAngle6;

  uniform sampler2D m_ProjectiveMap6;
  uniform float m_FallOffDistance6;
  uniform float m_FallOffPower6;
#endif

#if NUM_PROJECTORS > 7
  varying vec4 projCoord7;
  varying float cosAngle7;

  uniform sampler2D m_ProjectiveMap7;
  uniform float m_FallOffDistance7;
  uniform float m_FallOffPower7;
#endif

const float SOFTNESS = 0.1;
const float SOFTNESS_INV = 1.0 / SOFTNESS;

void main() 
{
  gl_FragColor = vec4(0.0);

  #if NUM_PROJECTORS > 0
    vec4 projColor;

    if (projCoord0.w > 0.0)
    {
      if (cosAngle0 > 0.0)
      {   
        projColor = clamp(texture2DProj(m_ProjectiveMap0, projCoord0), 0.0, 1.0);

        if (cosAngle0 < SOFTNESS)
        {
          projColor.a *= cosAngle0 * SOFTNESS_INV;
        }

        #ifdef FALL_OFF0
          if (projCoord0.w > m_FallOffDistance0)
          {
            float maxDist = m_FallOffDistance0 + 1.0;
            projColor.a *= clamp(pow(maxDist - projCoord0.w, m_FallOffPower0), 0.0, 1.0);
          }        
        #endif

        gl_FragColor = projColor;
      }
    }
  #endif

  #if NUM_PROJECTORS > 1
    if (projCoord1.w > 0.0)
    {
      if (cosAngle1 > 0.0)
      {   
        projColor = texture2DProj(m_ProjectiveMap1, projCoord1);

        if (cosAngle1 < SOFTNESS)
        {
          projColor.a *= cosAngle1 * SOFTNESS_INV;
        }

        #ifdef FALL_OFF1
          if (projCoord1.w > m_FallOffDistance1)
          {
            float maxDist = m_FallOffDistance1 + 1.0;
            projColor.a *= clamp(pow(maxDist - projCoord1.w, m_FallOffPower1), 0.0, 1.0);
          }        
        #endif

        #if COMBINE_MODE1 == BLEND_ALL
          gl_FragColor = mix(gl_FragColor, projColor, projColor.a);
        #else // BLEND_COLOR_ADD_ALPHA
          gl_FragColor.xyz = mix(gl_FragColor.xyz, projColor.xyz, projColor.a);
          gl_FragColor.a += projColor.a;
        #endif
      }
    }
  #endif

  #if NUM_PROJECTORS > 2
    if (projCoord2.w > 0.0)
    {
      if (cosAngle2 > 0.0)
      {   
        projColor = texture2DProj(m_ProjectiveMap2, projCoord2);

        if (cosAngle2 < SOFTNESS)
        {
          projColor.a *= cosAngle2 * SOFTNESS_INV;
        }

        #ifdef FALL_OFF2
          if (projCoord2.w > m_FallOffDistance2)
          {
            float maxDist = m_FallOffDistance2 + 1.0;
            projColor.a *= clamp(pow(maxDist - projCoord2.w, m_FallOffPower2), 0.0, 1.0);
          }        
        #endif

        #if COMBINE_MODE2 == BLEND_ALL
          gl_FragColor = mix(gl_FragColor, projColor, projColor.a);
        #else // BLEND_COLOR_ADD_ALPHA
          gl_FragColor.xyz = mix(gl_FragColor.xyz, projColor.xyz, projColor.a);
          gl_FragColor.a += projColor.a;
        #endif
      }
    }
  #endif

  #if NUM_PROJECTORS > 3
    if (projCoord3.w > 0.0)
    {
      if (cosAngle3 > 0.0)
      {   
        projColor = texture2DProj(m_ProjectiveMap3, projCoord3);

        if (cosAngle3 < SOFTNESS)
        {
          projColor.a *= cosAngle3 * SOFTNESS_INV;
        }

        #ifdef FALL_OFF3
          if (projCoord3.w > m_FallOffDistance3)
          {
            float maxDist = m_FallOffDistance3 + 1.0;
            projColor.a *= clamp(pow(maxDist - projCoord3.w, m_FallOffPower3), 0.0, 1.0);
          }        
        #endif

        #if COMBINE_MODE3 == BLEND_ALL
          gl_FragColor = mix(gl_FragColor, projColor, projColor.a);
        #else // BLEND_COLOR_ADD_ALPHA
          gl_FragColor.xyz = mix(gl_FragColor.xyz, projColor.xyz, projColor.a);
          gl_FragColor.a += projColor.a;
        #endif
      }
    }
  #endif

  #if NUM_PROJECTORS > 4
    if (projCoord4.w > 0.0)
    {
      if (cosAngle4 > 0.0)
      {   
        projColor = texture2DProj(m_ProjectiveMap4, projCoord4);

        if (cosAngle4 < SOFTNESS)
        {
          projColor.a *= cosAngle4 * SOFTNESS_INV;
        }

        #ifdef FALL_OFF4
          if (projCoord0.w > m_FallOffDistance4)
          {
            float maxDist = m_FallOffDistance4 + 1.0;
            projColor.a *= clamp(pow(maxDist - projCoord4.w, m_FallOffPower4), 0.0, 1.0);
          }        
        #endif

        #if COMBINE_MODE4 == BLEND_ALL
          gl_FragColor = mix(gl_FragColor, projColor, projColor.a);
        #else // BLEND_COLOR_ADD_ALPHA
          gl_FragColor.xyz = mix(gl_FragColor.xyz, projColor.xyz, projColor.a);
          gl_FragColor.a += projColor.a;
        #endif
      }
    }
  #endif

  #if NUM_PROJECTORS > 5
    if (projCoord5.w > 0.0)
    {
      if (cosAngle5 > 0.0)
      {   
        projColor = texture2DProj(m_ProjectiveMap5, projCoord5);

        if (cosAngle5 < SOFTNESS)
        {
          projColor.a *= cosAngle5 * SOFTNESS_INV;
        }

        #ifdef FALL_OFF5
          if (projCoord0.w > m_FallOffDistance5)
          {
            float maxDist = m_FallOffDistance5 + 1.0;
            projColor.a *= clamp(pow(maxDist - projCoord5.w, m_FallOffPower5), 0.0, 1.0);
          }        
        #endif

        #if COMBINE_MODE5 == BLEND_ALL
          gl_FragColor = mix(gl_FragColor, projColor, projColor.a);
        #else // BLEND_COLOR_ADD_ALPHA
          gl_FragColor.xyz = mix(gl_FragColor.xyz, projColor.xyz, projColor.a);
          gl_FragColor.a += projColor.a;
        #endif
      }
    }
  #endif

  #if NUM_PROJECTORS > 6
    if (projCoord6.w > 0.0)
    {
      if (cosAngle6 > 0.0)
      {   
        projColor = texture2DProj(m_ProjectiveMap6, projCoord6);

        if (cosAngle6 < SOFTNESS)
        {
          projColor.a *= cosAngle6 * SOFTNESS_INV;
        }

        #ifdef FALL_OFF6
          if (projCoord6.w > m_FallOffDistance6)
          {
            float maxDist = m_FallOffDistance6 + 1.0;
            projColor.a *= clamp(pow(maxDist - projCoord6.w, m_FallOffPower6), 0.0, 1.0);
          }        
        #endif

        #if COMBINE_MODE6 == BLEND_ALL
          gl_FragColor = mix(gl_FragColor, projColor, projColor.a);
        #else // BLEND_COLOR_ADD_ALPHA
          gl_FragColor.xyz = mix(gl_FragColor.xyz, projColor.xyz, projColor.a);
          gl_FragColor.a += projColor.a;
        #endif
      }
    }
  #endif

  #if NUM_PROJECTORS > 7
    if (projCoord7.w > 0.0)
    {
      if (cosAngle7 > 0.0)
      {   
        projColor = texture2DProj(m_ProjectiveMap7, projCoord7);

        if (cosAngle7 < SOFTNESS)
        {
          projColor.a *= cosAngle7 * SOFTNESS_INV;
        }

        #ifdef FALL_OFF7
          if (projCoord7.w > m_FallOffDistance7)
          {
            float maxDist = m_FallOffDistance7 + 1.0;
            projColor.a *= clamp(pow(maxDist - projCoord7.w, m_FallOffPower7), 0.0, 1.0);
          }        
        #endif

        #if COMBINE_MODE7 == BLEND_ALL
          gl_FragColor = mix(gl_FragColor, projColor, projColor.a);
        #else // BLEND_COLOR_ADD_ALPHA
          gl_FragColor.xyz = mix(gl_FragColor.xyz, projColor.xyz, projColor.a);
          gl_FragColor.a += projColor.a;
        #endif
      }
    }
  #endif

  gl_FragColor = clamp(gl_FragColor, 0.0, 1.0);
}
