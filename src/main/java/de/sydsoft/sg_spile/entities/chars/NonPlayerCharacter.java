/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_spile.entities.chars;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Spatial;

import de.sydsoft.sg_spile.gui.GameClient;

/**
 *
 * @author sythelux
 */
public class NonPlayerCharacter extends Char {

    @Override
    public void initialize(SimpleApplication app, String charName) {
        Spatial spatial = app.getAssetManager().loadModel("Models/Characters/" + charName + "/" + charName + ".obj");
        spatial.addControl(new RigidBodyControl(0.1f));
        app.getRootNode().attachChild(spatial);
        spatial.setLocalScale(0.05f);
        super.initialize(app, charName);
        if (app instanceof GameClient) {
            GameClient g = (GameClient) app;
            g.getBulletAppState().getPhysicsSpace().add(spatial);
        }
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }
}
