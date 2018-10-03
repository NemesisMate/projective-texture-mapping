/*
 * Copyright (c) 2009-2010 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jme3.ext.projectivetexturemapping;

import com.jme3.math.Matrix4f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.GeometryList;
import com.jme3.texture.Texture2D;

/**
 * A TextureProjector provides the minimum information needed by the 
 * TextureProjectorRenderer. Could be implemented as a Spatial for example.
 * 
 * @author survivor
 */
public interface TextureProjector {
  /**
   * @return The location of this TextureProjector.
   */  
  Vector3f getProjectorLocation();

  /**
   * @return The projection direction of this TextureProjector.
   */  
  Vector3f getProjectorDirection();

  /**
   * @return The projection matrix for the texture.
   */  
  Matrix4f getProjectorViewProjectionMatrix();

  /**
   * @return true, if this TextureProjector uses parallel projection.
   */  
  boolean isParallelProjection();

  /**
   * @return The texture to be projected.
   */  
  Texture2D getProjectiveTexture();

  /**
   * @return The geometry that should be affected by this TextureProjector. 
   * Return null if the whole RenderQueue shall be affected.
   */  
  GeometryList getTargetGeometryList();
  
  /**
   * @return The distance at which the projection should start to fall off.
   */  
  float getFallOffDistance();
  
  /**
   * @return Power at which the projection should fall off.
   */  
  float getFallOffPower();  

  /**
   * @param key A custom key.
   * @return A custom parameter value.
   */  
  Object getParameter(Object key);
}
