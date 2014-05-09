/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.logic;

import com.jme3.asset.AssetManager;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.terrain.geomipmap.TerrainGridTileLoader;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.NbtIo;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.world.level.chunk.storage.OldChunkStorage;
import net.minecraft.world.level.chunk.storage.OldChunkStorage.OldLevelChunk;
import net.minecraft.world.level.chunk.storage.RegionFile;

/**
 *
 * @author sythelux
 */
public class MCTileLoader implements TerrainGridTileLoader {

    private AssetManager manager;
    private String assetPath;
    private String name;
    private int size;
    private int patchSize;
    private int quadSize;
    
    public MCTileLoader() {
    }

    public MCTileLoader(AssetManager manager, String name, String assetPath) {
        this.manager = manager;
        this.name = name;
        this.assetPath = assetPath;
    }

    public TerrainQuad getTerrainQuadAt(Vector3f location) {
        TerrainQuad quad = null;
        RegionFile regionSource = new RegionFile(new File(assetPath + name + "/r.0.0.mcr"));
        int x = Math.round(location.x + 8f), z = Math.round(location.z + 8f);
        if (regionSource.hasChunk(x, z)) {
            try {
                CompoundTag chunkData;
                DataInputStream regionChunkInputStream = regionSource.getChunkDataInputStream(x, z);
                if (regionChunkInputStream == null) {
                    System.out.println("Failed to fetch input stream");
                }
                chunkData = NbtIo.read(regionChunkInputStream);

                CompoundTag compound = chunkData.getCompound("Level");
                {
                    OldLevelChunk oldChunk = OldChunkStorage.load(compound);

                    float[] heightMap = new float[oldChunk.heightmap.length];
                    for (int k = 0; k < oldChunk.heightmap.length; k++) {
                        heightMap[k] = oldChunk.heightmap[k];
                    }

                    quad = new TerrainQuad("Quad;" + x + ";" + z, patchSize, quadSize, heightMap);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return quad;
    }

    public String getAssetPath() {
        return assetPath;
    }

    public String getName() {
        return name;
    }

    public void setPatchSize(int patchSize) {
        this.patchSize = patchSize;
    }

    public void setQuadSize(int quadSize) {
        this.quadSize = quadSize;
    }

    private TerrainQuad createNewQuad(Vector3f location) {
        TerrainQuad q = new TerrainQuad("Quad" + location, patchSize, quadSize, null);
        return q;
    }

    public void write(JmeExporter ex) throws IOException {
        OutputCapsule c = ex.getCapsule(this);
        c.write(assetPath, "assetPath", null);
        c.write(name, "name", null);
    }

    public void read(JmeImporter im) throws IOException {
        InputCapsule c = im.getCapsule(this);
        manager = im.getAssetManager();
        assetPath = c.readString("assetPath", null);
        name = c.readString("name", null);
    }
}