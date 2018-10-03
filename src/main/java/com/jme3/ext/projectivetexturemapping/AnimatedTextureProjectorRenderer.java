/*
 * Copyright (c) 2009-2010 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *     may be used to endorse or promote products derived from this software
 *     without specific prior written permission.
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

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.post.SceneProcessor;
import com.jme3.profile.AppProfiler;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.GeometryList;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.system.Timer;
import com.jme3.texture.FrameBuffer;

import java.util.ArrayList;
import java.util.List;

/**
 * A SceneProcessor that renders TextureProjectors, which means it projects
 * textures on scene geometry. The textures can have a tile animation.
 *
 * @author survivor, H
 */
public class AnimatedTextureProjectorRenderer implements SceneProcessor {
    private RenderManager renderManager;
    private ViewPort viewPort;
    private final Material textureMat;
    private final ArrayList<TextureProjector> textureProjectors;
    private final Timer timer;
    private int numTilesU;

    private AppProfiler profiler;

    @Override
    public void setProfiler(AppProfiler profiler) {
        this.profiler = profiler;
    }

    private int numTilesV;
    private int speed;

    public AnimatedTextureProjectorRenderer(AssetManager assetManager, Timer timer, int numTilesU, int numTilesV, int speed) {
        this(assetManager, timer, numTilesU, numTilesV, speed, null);
    }

    public AnimatedTextureProjectorRenderer(final AssetManager assetManager, Timer timer, int numTilesU, int numTilesV, int speed, ColorRGBA color) {
        this.numTilesU = numTilesU;
        this.numTilesV = numTilesV;
        this.speed = speed;
        this.timer = timer;
        this.textureMat = new Material(assetManager, "MatDefs/ProjectiveAnimatedTextureMapping.j3md");
        this.textureMat.setInt("NumTilesU", numTilesU);
        this.textureMat.setInt("NumTilesV", numTilesV);
        this.textureMat.setInt("SelectedTileU", 0);
        this.textureMat.setInt("SelectedTileV", 0);
        this.textureProjectors = new ArrayList<>();
        this.renderManager = null;
        this.viewPort = null;
        setPolyOffset(-0.1f, -0.1f);
    }

    /**
     * @return A list of TextureProjectors rendered by this instance.
     */
    public List<TextureProjector> getTextureProjectors() {
        return textureProjectors;
    }

    /**
     * Offsets the on-screen z-order of the texture material's polygons,
     * to combat visual artefacts like stitching, bleeding and z-fighting
     * for overlapping polygons.
     * Factor and units are summed to produce the depth offset.
     * This offset is applied in screen space,
     * typically with positive Z pointing into the screen.
     * Typical values are (1.0f, 1.0f) or (-1.0f, -1.0f).
     * The default values are (-0.1f, -0.1f).
     *
     * @param factor scales the maximum Z slope, with respect to X or Y of the polygon
     * @param units  scales the minimum resolvable depth buffer value
     * @see com.jme3.material.RenderState
     * @see <a href="http://www.opengl.org/resources/faq/technical/polygonoffset.htm" rel="nofollow">http://www.opengl.org/resources/faq/technical/polygonoffset.htm</a>
     */
    public final void setPolyOffset(float factor, float units) {
        textureMat.getAdditionalRenderState().setPolyOffset(factor, units);
    }

    /**
     * Initializes this instance.
     *
     * @see SceneProcessor
     */
    @Override
    public void initialize(RenderManager rm, ViewPort vp) {
        renderManager = rm;
        viewPort = vp;
    }

    /**
     * @return true, if this instance is initialized, false otherwise.
     * @see SceneProcessor
     */
    @Override
    public boolean isInitialized() {
        return viewPort != null;
    }

    /**
     * Called before the a frame is rendered.
     *
     * @see SceneProcessor
     */
    @Override
    public void preFrame(float tpf) {
    }

    /**
     * Called before the render queue is flushed.
     *
     * @see SceneProcessor
     */
    @Override
    public void postQueue(RenderQueue rq) {
    }

    /**
     * Renders each TextureProjector with its corresponding material parameters.
     * Called after a frame has been rendered.
     *
     * @see SceneProcessor
     */
    @Override
    public void postFrame(final FrameBuffer out) {
        this.renderManager.setForcedMaterial(this.textureMat);
        this.renderManager.getRenderer().setFrameBuffer(out); // ToDo: check if needed

        for (final TextureProjector textureProjector : this.textureProjectors) {
            final float fallOffDistance = textureProjector.getFallOffDistance();
            this.textureMat.setTexture("ProjectiveMap", textureProjector.getProjectiveTexture());
            this.textureMat.setMatrix4("ProjectorViewProjectionMatrix",
                    textureProjector.getProjectorViewProjectionMatrix());

            //this.textureMat.setFloat("Time", this.timer.getTimeInSeconds());
            float now = this.timer.getTimeInSeconds() * this.speed;
            int selectedTileU = (int) (now % numTilesU);
            int selectedTileV = (int) (now % numTilesV);
            this.textureMat.setInt("SelectedTileU", selectedTileU);
            this.textureMat.setInt("SelectedTileV", selectedTileV);

            if (textureProjector.isParallelProjection()) {
                this.textureMat.clearParam("ProjectorLocation");
                this.textureMat.setVector3("ProjectorDirection", textureProjector.getProjectorDirection());
            } else {
                this.textureMat.clearParam("ProjectorDirection");
                this.textureMat.setVector3("ProjectorLocation", textureProjector.getProjectorLocation());
            }

            if (fallOffDistance != Float.MAX_VALUE) {
                this.textureMat.setFloat("FallOffDistance", textureProjector.getFallOffDistance());
                this.textureMat.setFloat("FallOffPower", textureProjector.getFallOffPower());
            } else {
                this.textureMat.clearParam("FallOffDistance");
                this.textureMat.clearParam("FallOffPower");
            }

            final GeometryList targetGeometryList = textureProjector.getTargetGeometryList();
            if (targetGeometryList != null) {
                this.renderManager.renderGeometryList(targetGeometryList);
            } else {
                this.renderManager.renderViewPortRaw(this.viewPort);
            }
        }

        this.renderManager.setForcedMaterial(null);
    }

    /**
     * Cleans up this instance.
     *
     * @see SceneProcessor
     */
    @Override
    public void cleanup() {
    }

    /**
     * Called if the shape of the ViewPort changed.
     *
     * @see SceneProcessor
     */
    @Override
    public void reshape(ViewPort vp, int w, int h) {
    }
}
