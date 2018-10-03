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
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.GeometryList;
import com.jme3.texture.Texture2D;

import java.util.HashMap;

/**
 * A simple implementation of TextureProjector using a Camera.
 *
 * @author survivor
 */
public class SimpleTextureProjector implements TextureProjector {
    private static final Matrix4f biasMatrix = new Matrix4f(0.5f, 0.0f, 0.0f, 0.5f,
            0.0f, 0.5f, 0.0f, 0.5f,
            0.0f, 0.0f, 0.5f, 0.5f,
            0.0f, 0.0f, 0.0f, 1.0f);

    private Camera projectorCamera;
    private Matrix4f projectorViewProjectionMatrix;
    private Texture2D projectiveTextureMap;
    private GeometryList targetGeometryList;
    private float fallOffDistance;
    private float fallOffPower;
    private HashMap<Object, Object> parameters;

    /**
     * Crates a new instance.
     *
     * @param projectiveTextureMap The texture to be projected.
     * @author survivor
     */
    public SimpleTextureProjector(Texture2D projectiveTextureMap) {
        this.projectorViewProjectionMatrix = new Matrix4f();
        this.projectiveTextureMap = projectiveTextureMap;
        this.projectorCamera = new Camera(
                projectiveTextureMap.getImage().getWidth(),
                projectiveTextureMap.getImage().getHeight());
        this.fallOffDistance = Float.MAX_VALUE;
        this.fallOffPower = 3f;
        this.parameters = new HashMap<>();
    }

    /**
     * Sets the texture to be projected.
     *
     * @param projectiveTextureMap The texture to be projected.
     * @author survivor
     */
    public void setProjectiveTextureMap(Texture2D projectiveTextureMap) {
        this.projectiveTextureMap = projectiveTextureMap;
    }

    /**
     * @return The location of this TextureProjector.
     * @see TextureProjector
     */
    @Override
    public Vector3f getProjectorLocation() {
        return this.projectorCamera.getLocation();
    }

    /**
     * @return The projection direction of this TextureProjector.
     * @see TextureProjector
     */
    @Override
    public Vector3f getProjectorDirection() {
        return this.projectorCamera.getDirection();
    }

    /**
     * @return The projection matrix for the texture.
     * @see TextureProjector
     */
    @Override
    public Matrix4f getProjectorViewProjectionMatrix() {
        this.projectorViewProjectionMatrix.set(biasMatrix);
        this.projectorViewProjectionMatrix.multLocal(this.projectorCamera.getViewProjectionMatrix());
        return this.projectorViewProjectionMatrix;
    }

    /**
     * @return true, if this TextureProjector uses parallel projection.
     * @see TextureProjector
     */
    @Override
    public boolean isParallelProjection() {
        return this.projectorCamera.isParallelProjection();
    }

    /**
     * @return The texture to be projected.
     * @see TextureProjector
     */
    @Override
    public Texture2D getProjectiveTexture() {
        return this.projectiveTextureMap;
    }

    /**
     * @return The geometry that should be affected by this TextureProjector.
     * Return null if the whole RenderQueue shall be affected.
     * @see TextureProjector
     */
    @Override
    public GeometryList getTargetGeometryList() {
        return this.targetGeometryList;
    }

    /**
     * Sets the geometry that sould be affected by this TextureProjector.
     *
     * @param targetGeometryList The geometry that sould be affected by this TextureProjector.
     * @see TextureProjector
     */
    public void setTargetGeometryList(GeometryList targetGeometryList) {
        this.targetGeometryList = targetGeometryList;
    }

    /**
     * @return The projector camera.
     * @see TextureProjector
     */
    public Camera getProjectorCamera() {
        return this.projectorCamera;
    }

    /**
     * @param fallOffDistance The distance at which the projection should start to fall off.
     */
    public void setFallOffDistance(float fallOffDistance) {
        this.fallOffDistance = fallOffDistance;
    }

    /**
     * @return The distance at which the projection should start to fall off.
     */
    public float getFallOffDistance() {
        return this.fallOffDistance;
    }

    /**
     * @param fallOffPower The power at which the projection should fall off.
     */
    public void setFallOffPower(float fallOffPower) {
        this.fallOffPower = fallOffPower;
    }

    /**
     * @return Power at which the projection should fall off.
     */
    public float getFallOffPower() {
        return this.fallOffPower;
    }

    /**
     * @param key A custom key.
     * @return A custom parameter value.
     */
    public Object getParameter(Object key) {
        return this.parameters.get(key);
    }

    /**
     * @param key   A custom key.
     * @param value A custom parameter value.
     */
    public void setParameter(Object key, Object value) {
        this.parameters.put(key, value);
    }

    /**
     * Provides frustum points to be used by a WireFrustum for debugging.
     *
     * @param points An initialized Vector3f[8] array to receive the frustum points.
     */
    public void updateFrustumPoints(Vector3f[] points) {
        int w = projectorCamera.getWidth();
        int h = projectorCamera.getHeight();
        final float n = 0;
        final float f = 1f;

        points[0].set(projectorCamera.getWorldCoordinates(new Vector2f(0, 0), n));
        points[1].set(projectorCamera.getWorldCoordinates(new Vector2f(0, h), n));
        points[2].set(projectorCamera.getWorldCoordinates(new Vector2f(w, h), n));
        points[3].set(projectorCamera.getWorldCoordinates(new Vector2f(w, 0), n));

        points[4].set(projectorCamera.getWorldCoordinates(new Vector2f(0, 0), f));
        points[5].set(projectorCamera.getWorldCoordinates(new Vector2f(0, h), f));
        points[6].set(projectorCamera.getWorldCoordinates(new Vector2f(w, h), f));
        points[7].set(projectorCamera.getWorldCoordinates(new Vector2f(w, 0), f));
    }
}
