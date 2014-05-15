/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_spile.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.util.SkyFactory;

import de.sydsoft.sg_spile.gui.screens.LoadingScreen;

/**
 *
 * @author sythelux
 */
public class LoadingState extends ClientMainAppState {

    private DirectionalLight sun;
    private int percentageClompleted = 0;
    private Material mat_terrain;
    private TerrainQuad terrain;
    private Spatial sky;
//    private FractalSum base;
//    private PerturbFilter perturb;
//    private OptimizedErode therm;
//    private SmoothFilter smooth;
//    private IterativeFilter iterate;
//    private float grassScale = 64;
//    private float dirtScale = 16;
//    private float rockScale = 128;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        //add Light
        initLight();
        percentageClompleted = 33;
        //initmats
        initMap();
        percentageClompleted = 66;
        //add Sky
        initSky();
        percentageClompleted = 100;
        enabled = true;
    }

    public void initLight() {
        sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-1, -1, -1).normalizeLocal());
        g.getRootNode().addLight(sun);

        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(1.3f));
        g.getRootNode().addLight(al);
    }

    public void initMap() {
        /** 1. Create terrain material and load four textures into it. */
        mat_terrain = new Material(g.getAssetManager(),
                "Common/MatDefs/Terrain/Terrain.j3md");

        /** 1.1) Add ALPHA map (for red-blue-green coded splat textures) */
        mat_terrain.setTexture("Alpha", g.getAssetManager().loadTexture(
                "Textures/Terrain/ib_dhmv4-alphamap.jpg"));

        /** 1.2) Add GRASS texture into the red layer (Tex1). */
        Texture grass = g.getAssetManager().loadTexture(
                "Textures/Jungle/T_J_grass-a.png");
        grass.setWrap(WrapMode.Repeat);
        mat_terrain.setTexture("Tex1", grass);
        mat_terrain.setFloat("Tex1Scale", 512f);

        /** 1.3) Add DIRT texture into the green layer (Tex2) */
        Texture dirt = g.getAssetManager().loadTexture(
                "Textures/Jungle/T_J_dirt-a.png");
        dirt.setWrap(WrapMode.Repeat);
        mat_terrain.setTexture("Tex2", dirt);
        mat_terrain.setFloat("Tex2Scale", 512f);

        /** 1.4) Add ROAD texture into the blue layer (Tex3) */
        Texture rock = g.getAssetManager().loadTexture(
                "Textures/Jungle/T_J_path-a.png");
        rock.setWrap(WrapMode.Repeat);
        mat_terrain.setTexture("Tex3", rock);
        mat_terrain.setFloat("Tex3Scale", 512f);

        /** 2. Create the height map */
        AbstractHeightMap heightmap = null;
//        Texture heightMapImage = g.getAssetManager().loadTexture("Textures/Terrain/splat/mountains512.png");
        Texture heightMapImage = g.getAssetManager().loadTexture("Textures/Terrain/ib_dhmv4.jpg");
        try {
            heightmap = new ImageBasedHeightMap(heightMapImage.getImage());
//            heightmap = new RawHeightMap("/home/sythelux/Arbeitsbank/Java/Wolfskrone/assets/Textures/Terrain/landscape_test.raw", 256);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        heightmap.load();

        /** 3. We have prepared material and heightmap. 
         * Now we create the actual terrain:
         * 3.1) Create a TerrainQuad and name it "my terrain".
         * 3.2) A good value for terrain tiles is 64x64 -- so we supply 64+1=65.
         * 3.3) We prepared a heightmap of size 512x512 -- so we supply 512+1=513.
         * 3.4) As LOD step scale we supply Vector3f(1,1,1).
         * 3.5) We supply the prepared heightmap itself.
         */
        int patchSize = 65;
        terrain = new TerrainQuad("my terrain", patchSize, 513, heightmap.getHeightMap());
        /** 4. We give the terrain its material, position & scale it, and attach it. */
        terrain.setMaterial(mat_terrain);
        terrain.setLocalTranslation(0, 0, 0);
        terrain.setLocalScale(2f, 2f, 2f);
        g.getRootNode().attachChild(terrain);

        /** 5. The LOD (level of detail) depends on were the camera is: */
    }

    public void initSky() {
        Texture skyTop = g.getAssetManager().loadTexture("Textures/Skybox/T_S_top.png");
        Texture skyBottom = g.getAssetManager().loadTexture("Textures/Skybox/T_S_bottom.png");
        Texture skyWest = g.getAssetManager().loadTexture("Textures/Skybox/T_S_west.png");
        Texture skyEast = g.getAssetManager().loadTexture("Textures/Skybox/T_S_east.png");
        Texture skySouth = g.getAssetManager().loadTexture("Textures/Skybox/T_S_south.png");
        Texture skyNorth = g.getAssetManager().loadTexture("Textures/Skybox/T_S_north.png");
        sky = SkyFactory.createSky(g.getAssetManager(), skyEast, skyWest, skySouth, skyNorth, skyTop, skyBottom);
        g.getRootNode().attachChild(sky);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (enabled) {
            ((LoadingScreen) screenController).updatePercentage(percentageClompleted);
            if (percentageClompleted == 100) {
                completed = true;
                ((LoadingScreen) screenController).quit();
                g.toIngameScreen();
            }
        }
    }

    @Override
    public void cleanup() {
        super.cleanup();
    }

    public TerrainQuad getTerrain() {
        return terrain;
    }
    
    
}
