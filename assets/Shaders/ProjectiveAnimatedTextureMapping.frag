varying vec4 projCoord;
varying float cosAngle;
 
uniform sampler2D m_ProjectiveMap;
uniform int m_NumTilesU;
uniform int m_NumTilesV;
uniform int m_SelectedTileU;
uniform int m_SelectedTileV;

#ifdef FALL_OFF
  uniform float m_FallOffDistance;
  uniform float m_FallOffPower;
#endif
 
const float SOFTNESS = 0.1;
const float SOFTNESS_INV = 1.0 / SOFTNESS;
 
void main()
{
  gl_FragColor = vec4(0.0);

  if ((projCoord.w > 0.0) && 
      (cosAngle > 0.0))
  {
    vec2 lowBounds = vec2(float(m_SelectedTileU) / float(m_NumTilesU),
                          float(m_SelectedTileV) / float(m_NumTilesV));

    vec2 highBounds = vec2(float(m_SelectedTileU+1) / float(m_NumTilesU),
                           float(m_SelectedTileV+1) / float(m_NumTilesV));

    if ((projCoord.x >= lowBounds.x) && (projCoord.x <= highBounds.x) &&
        (projCoord.y >= lowBounds.y) && (projCoord.y <= highBounds.y))
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
