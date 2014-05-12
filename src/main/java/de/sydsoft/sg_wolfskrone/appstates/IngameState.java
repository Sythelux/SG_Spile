package de.sydsoft.sg_wolfskrone.appstates;

import com.jme3.animation.LoopMode;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.ScreenshotAppState;
import com.jme3.bullet.collision.shapes.HeightfieldCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import de.sydsoft.libst.util.Constants;
import de.sydsoft.sg_wolfskrone.gui.GameClient;
import de.sydsoft.sg_wolfskrone.gui.screens.IngameScreen;
import de.sydsoft.sg_wolfskrone.logic.ChatMessage;
import de.sydsoft.sg_wolfskrone.logic.ClientListener;
import de.sydsoft.sg_wolfskrone.logic.ServerAnswerMessage;
import de.sydsoft.sg_wolfskrone.logic.ServerRequestMessage;
import de.sydsoft.sg_wolfskrone.util.AdvancedChaseCamera;
import de.sydsoft.sg_wolfskrone.util.LocHelper;
import org.lwjgl.Sys;

/**
 *
 * @author sythelux
 */
public class IngameState extends ClientMainAppState {

    private AdvancedChaseCamera chaseCam;
    //private Quaternion camRot;
    private BitmapText ch;
    private BitmapText charAttributs;
    private ClientListener clientListener;
    private TerrainQuad terrain;
    //private Geometry viewSphere;
    private Vector3f walkDirection = new Vector3f(0, 0, 0);
    private Vector3f viewDirection = new Vector3f(0, 0, 0);

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        //add PhysicsAppState
        stateManager.attach(g.getBulletAppState());
        if (Constants.DEBUG) {
            g.getBulletAppState().getPhysicsSpace().enableDebug(g.getAssetManager());
        }
        //add Chatability, apstateDownload
        initNetwork();
        //clamb screenshot ability
        initAppStates();
        //clamb Nifty
        initGui();
        //initPlayer
        initPlayer();
        //initNPCS
        initNPCs();
        //initInputs
        g.getInputHandler().initialize();
        stateManager.attach(this);
        enabled = true;
    }

    private void initNetwork() {
        clientListener = new ClientListener(((IngameScreen) screenController).getChatArea());
        g.getClient().addMessageListener(clientListener, ServerAnswerMessage.class);
    }

    public void initAppStates() {
        ScreenshotAppState state = new ScreenshotAppState();
        g.getStateManager().attach(state);
    }

    public void initGui() {
        g.getClient().addMessageListener(clientListener, ChatMessage.class);

//        Picture pic = new Picture("HUD Picture");
//        pic.setImage(g.getAssetManager(), "Interface/Textures/GUI-idee1.1280x1024.png", true);
//        pic.setWidth(g.getSettings().getWidth());
//        pic.setHeight(g.getSettings().getHeight());
        //pic.setPosition(g.getSettings().getWidth() / 4, g.getSettings().getHeight() / 4);
//        g.getGuiNode().attachChild(pic);
        BitmapFont guiFont = g.getAssetManager().loadFont("Interface/Fonts/NimbusSansL.fnt");
        ch = new BitmapText(guiFont, false);
        //ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("airtime: 0\r\nHealth: 0");
        ch.setLocalTranslation(0, g.getSettings().getHeight(), 0);
        g.getGuiNode().attachChild(ch);
//        g.getNifty().fromXml("Interface/Nifty/Ingame.xml", "start", ingameScreen);
//        ingameScreen.bind(g.getNifty(), g.getNifty().getScreen("start"));
        charAttributs = new BitmapText(guiFont);
        charAttributs.setLocalTranslation(0, g.getSettings().getHeight() / 2, 0);
        g.getGuiNode().attachChild(charAttributs);
    }

    public void initPlayer() {
        g.getActualCharacter().initialize((GameClient) g, g.getPlayer().getChoosenName());
        g.getClient().send(new ServerRequestMessage(ServerRequestMessage.PLAYERPOSITION, new String[]{g.getPlayer().getChoosenName()}));
        chaseCam = new AdvancedChaseCamera(g.getCamera(), (Node) g.getActualCharacter().getNode(), g.getInputManager());
        chaseCam.setInvertHorizontalAxis(false);
        chaseCam.setInvertVerticalAxis(true);
        chaseCam.setDefaultDistance(3f);
        chaseCam.setMinDistance(3f);
        chaseCam.setMaxDistance(10f);
        StringBuilder sb = new StringBuilder(g.getActualCharacter().getFullyName());
        sb.append(" (").append(g.getActualCharacter().getCharLevel()).append(")\r\n").append(g.getActualCharacter().getAttributs().toString());
        charAttributs.setText(sb);
        terrain = ((TerrainQuad) g.getRootNode().getChild("my terrain"));
        terrain.addControl(new RigidBodyControl(new HeightfieldCollisionShape(terrain.getHeightMap(), terrain.getLocalScale()), 0));
        g.getBulletAppState().getPhysicsSpace().add(terrain);
        clientListener.getPlayerPos().setY(terrain.getHeight(new Vector2f(clientListener.getPlayerPos().x, clientListener.getPlayerPos().z)));
        g.getActualCharacter().getCharacterControl().setPhysicsLocation(clientListener.getPlayerPos().add(0,1,0));
        TerrainLodControl control = new TerrainLodControl(terrain, g.getActualCharacter().getCamNode().getCamera());
        terrain.addControl(control);
    }

    public void initNPCs() {
        //TODO: load NPCs from server
//        NonPlayerCharacter ele = new NonPlayerCharacter();
//        ele.initialize(g, "Elephant");
//        ele.setLocalTranslation(g.getActualCharacter().getPhysicsLocation().add(-50, -20, -20));
//        NonPlayerCharacter pika = new NonPlayerCharacter();
//        pika.initialize(g, "Pikachu");
//        pika.setLocalTranslation(g.getActualCharacter().getPhysicsLocation().add(-50, -20, -20));
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        // <editor-fold defaultstate="collapsed" desc="Autoheal example">
        float maxHealth = g.getActualCharacter().getMaxHealth();
        float actHealth = g.getActualCharacter().getActHealth();
        if ((actHealth + 0.01f) > maxHealth) {
            g.getActualCharacter().setActHealth(maxHealth);
        } else {
            g.getActualCharacter().heal(0.01f);//TODO: NOT RIGHT !!!            
        }
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Anti-Lag screen">
        if (tpf >= 0.15f) {
            ((IngameScreen) screenController).startWaitScreen();
        } else if (((IngameScreen) screenController).isWaitscreenactive()) {
            ((IngameScreen) screenController).stopWaitScreen();
        }
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="walking">
        Vector3f camDir = g.getCamera().getDirection().mult(tpf);
        camDir.y = 0;
        Vector3f camLeft = g.getCamera().getLeft().mult(tpf);
        camLeft.y = 0;
        viewDirection.set(camDir);
        walkDirection.set(0, 0, 0);
        if (g.getInputHandler().left) {
            if (!g.getInputHandler().rightC) {
                viewDirection.addLocal(camLeft.mult(tpf));
            } else {
                walkDirection.addLocal(camLeft);
            }
        }
        if (g.getInputHandler().right) {
            if (!g.getInputHandler().rightC) {
                viewDirection.addLocal(camLeft.mult(tpf).negate());
            } else {
                walkDirection.addLocal(camLeft.negate());
            }
        }
        if (g.getInputHandler().ahead || g.getInputHandler().aheadLock) {
            walkDirection.addLocal(camDir);
        }
        if (g.getInputHandler().back) {
            walkDirection.addLocal(camDir.negate());
        }
        // </editor-fold>
        if (g.getInputHandler().jump) {
            g.getActualCharacter().setAnimation("JumpStart", 1f);
            g.getActualCharacter().getCharacterControl().jump();
        }
        if (g.getActualCharacter().getCharacterControl().onGround()) {
            if (g.getActualCharacter().getAirTime() > 3) {
                g.getActualCharacter().hurt(g.getActualCharacter().getAirTime());
            }
            g.getActualCharacter().setAirTime(0);
        } else {
            g.getActualCharacter().addAirTime(tpf);
        }
        if (walkDirection.length() == 0) {
            if (g.getActualCharacter().getAirTime() > .3f) {
                if (!"JumpLoop".equals(g.getActualCharacter().getAnimation())) {
                    g.getActualCharacter().setAnimation("JumpLoop");
                }
            } else if ("JumpLoop".equals(g.getActualCharacter().getAnimation())) {
                g.getActualCharacter().setAnimation("JumpEnd", 1f);
            } else if (!"IdleBase".equals(g.getActualCharacter().getAnimation())) {
                g.getActualCharacter().setAnimation("IdleTop", 1f);
                g.getActualCharacter().setAnimation("IdleBase", 1f);
                g.getActualCharacter().setLoopMode(LoopMode.Loop);
            }
        } else {
            g.getActualCharacter().getCharacterControl().setViewDirection(walkDirection);
            if (g.getActualCharacter().getAirTime() > .3f) {
                if (!"JumpLoop".equals(g.getActualCharacter().getAnimation())) {
                    g.getActualCharacter().setAnimation("JumpLoop");
                }
            } else if ("JumpLoop".equals(g.getActualCharacter().getAnimation())) {
                g.getActualCharacter().setAnimation("JumpEnd", 1f);
            } else if (!"RunBase".equals(g.getActualCharacter().getAnimation())) {
                g.getActualCharacter().setAnimation("RunTop", 1f);
                g.getActualCharacter().setAnimation("RunBase", 1f);
                g.getActualCharacter().setLoopMode(LoopMode.Loop);
            }
        }
        ch.setText("CharPos:" + g.getActualCharacter().getCharacterControl().getPhysicsLocation().toString() + "\r\nTime: " + Sys.getTime() + "\r\ntpf: " + tpf + "\r\nairtime: " + g.getActualCharacter().getAirTime() + "\r\nHealth: " + LocHelper.roundTwoDecimals(g.getActualCharacter().getActHealth()) + "/" + g.getActualCharacter().getMaxHealth());
        //System.out.println(g.getActualCharacter().getWalkDirection().toString()+"|"+g.getActualCharacter().getWalkDirection().length());
        g.getActualCharacter().getCharacterControl().setWalkDirection(walkDirection.normalize().multLocal(0.1f));
        g.getActualCharacter().getCharacterControl().setViewDirection(viewDirection.normalize().multLocal(0.1f));
    }

    @Override
    public void cleanup() {
        //super.cleanup();
    }
}
