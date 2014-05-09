/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.appstates;

import audio.AudioConstants;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.audio.AudioNode;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import de.sydsoft.libst.util.Console;
import de.sydsoft.sg_wolfskrone.gui.GameClient;
import de.sydsoft.sg_wolfskrone.gui.screens.LoginScreen;
import de.sydsoft.sg_wolfskrone.logic.ChatMessage;
import de.sydsoft.sg_wolfskrone.logic.ClientListener;
import de.sydsoft.sg_wolfskrone.logic.PlayerLoaderMessage;
import java.io.IOException;

/**
 *
 * @author sythelux
 */
public class LoginState extends ClientMainAppState {

    private Geometry geom;
    private Spatial sky;
    private ClientListener clientListener;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        clientListener = new ClientListener(this);
        super.initialize(stateManager, app);
        g.getInputManager().setCursorVisible(true);
        g.getFlyByCamera().setEnabled(false);
        g.getInputManager().setCursorVisible(true);
        Client client = null;
        if (g.getClient() == null) {
            try {
                client = (Client) Network.connectToServer("localhost", 0xface);
            } catch (IOException ex) {
                Console.errMsg(ex);
            }
            g.setClient(client);
            g.getClient().start();
            g.getClient().send(new ChatMessage("hi Server"));
            g.getClient().addMessageListener(clientListener, PlayerLoaderMessage.class);
        }
        Sphere sph = new Sphere(128, 128, 3f); // create cube shape at the origin
        geom = new Geometry("World", sph);  // create cube geometry from the shape
        Material mat = new Material(g.getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
        Texture tex = g.getAssetManager().loadTexture("Interface/Textures/Earth.jpg");
        mat.setTexture("ColorMap", tex);
        geom.setMaterial(mat);                   // set the cube's material
        geom.rotate(-1.5707963267f, (float) Math.PI / 2, 0);
        geom.setLocalTranslation(-4f, -3f, 0);
        geom.rotate(-FastMath.PI * 0.25f, 0, 0);
        g.getRootNode().attachChild(geom);              // make the cube appear in the scene
        sky = SkyFactory.createSky(g.getAssetManager(), "Textures/Skybox/stars.jpg", true);
        g.getRootNode().attachChild(sky);
        // <editor-fold defaultstate="collapsed" desc="Init Sound">
        AudioNode musicNode = (AudioNode) g.getAudioRootNode().getChild(AudioConstants.MUSICNODE);
        AudioNode loginMusic = new AudioNode(g.getAssetManager(), "Sounds/Login/GoodTimes.ogg", true);
        loginMusic.setLooping(false);
        loginMusic.setVolume(1f);
        musicNode.attachChild(loginMusic);
        loginMusic.play();
        // </editor-fold>
        enabled = true;
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        geom.rotate(0, 0, tpf * 0.01f);
        if (completed) {
            g.toLoadingScreen();
        }
    }

    @Override
    public void render(RenderManager rm) {
        super.render(rm);
        rm.renderGeometry(geom);
    }

    @Override
    public void cleanup() {
        super.cleanup();
        ((LoginScreen) screenController).quit();
        if (geom != null) {
            g.getRootNode().detachChild(geom);
            geom = null;
        }
        if (sky != null) {
            g.getRootNode().detachChild(sky);
            sky = null;
        }
    }

    public LoginScreen getScreen() {
        return (LoginScreen) screenController;
    }
}
