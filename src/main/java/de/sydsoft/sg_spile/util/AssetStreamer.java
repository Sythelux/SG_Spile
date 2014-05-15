/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_spile.util;

import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.HttpZipLocator;
import com.jme3.asset.plugins.ZipLocator;

public class AssetStreamer {

    AssetManager assetManager;

    @Deprecated
    public AssetStreamer() throws Exception {
        throw new Exception("Need an AssetManager to Check");
    }

    public AssetStreamer(AssetManager assetmanager) throws Exception {
        this.assetManager = assetmanager;
        if (assetmanager == null) {
            throw new NullPointerException("Need an AssetManager to Check");
        }
    }

    public void loadHTTPZipStream(String url) {
        if (url.contains(".zip")) {
            String path = url.substring(url.lastIndexOf("/"));
            assetManager.registerLocator(path, ZipLocator.class);
            if (assetManager.loadAsset(path.replace(".zip", ".scene")) == null) {
                assetManager.registerLocator(url, HttpZipLocator.class);
            }
        }
    }
}
