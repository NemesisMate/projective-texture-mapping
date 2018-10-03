varying vec4 projCoord;
varying float cosAngle;

uniform sampler2D m_ProjectiveMap;

#ifdef FALL_OFF
  uniform float m_FallOffDistance;
  uniform float m_FallOffPower;
#endif

const float SOFTNESS = 0.1;
const float SOFTNESS_INV = 1.0 / SOFTNESS;

void main() 
{
  gl_FragColor = vec4(0.0);

  if (projCoord.w > 0.0)
  {
    if (cosAngle > 0.0)
    {   
      vec4 projColor = texture2DProj(m_ProjectiveMap, projCoord);

      if (cosAngle < SOFTNESS)
      {
        projColor.a *= cosAngle * SOFTNESS_INV;
      }
      
      #ifdef FALL_OFF
        if (projCoord.w > m_FallOffDistance)
        {
          float maxDist = m_FallOffDistance + 1.0;
          projColor.a *= clamp(pow(maxDist - projCoord.w, m_FallOffPower), 0.0, 1.0);
        }        
      #endif

      gl_FragColor = projColor;
    }
  }  
}
