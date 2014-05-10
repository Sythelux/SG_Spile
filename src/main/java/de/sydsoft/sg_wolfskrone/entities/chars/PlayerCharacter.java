/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.entities.chars;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
import de.sydsoft.libst.util.Console;
import de.sydsoft.libst.util.Constants;
import de.sydsoft.sg_wolfskrone.entities.Attribute;
import de.sydsoft.sg_wolfskrone.entities.Attributes;
import de.sydsoft.sg_wolfskrone.entities.Item;
import de.sydsoft.sg_wolfskrone.entities.ItemBuilder;
import de.sydsoft.sg_wolfskrone.gui.GameClient;
import de.sydsoft.sg_wolfskrone.entities.Outfit;
import de.sydsoft.sg_wolfskrone.entities.items.Armor;
import de.sydsoft.sg_wolfskrone.entities.items.Weapon;
import de.sydsoft.sg_wolfskrone.util.Dice;

/**
 *
 * @author sythelux
 */
public class PlayerCharacter extends Char {

    private CameraNode camNode;
    private Node charNode;
    private Attributes attributs = new Attributes();
    private Outfit outfit = new Outfit();
    transient AnimChannel baseChannel;
    transient AnimChannel topChannel;
    transient AnimChannel shootingChannel;
    transient AnimControl animationControl;

    @Override
    public void initialize(SimpleApplication app, String charName) {
        Spatial spatial = app.getAssetManager().loadModel("Models/Characters/" + charName + "/" + charName + ".j3o");
        spatial.setLocalScale(0.05f);
        super.initialize(app, charName);
//        MaterialList matList = (MaterialList) g.getAssetManager().loadAsset("Models/Characters/Sinbad/Sinbad.material");
//        OgreMeshKey key = new OgreMeshKey("Models/Characters/Sinbad/Sinbad.j3o", matList);
//        spatial = (Spatial) g.getAssetManager().loadAsset(key);
//        Material mat = matList.get("Sinbad/Body");
//        TextureKey tKey = new TextureKey("Textures/Characters/Sinbad/sinbad_body.jpg");
//        Texture2D texture = (Texture2D) g.getAssetManager().loadTexture(tKey);
//        mat.setTexture("NormalMap", texture);
//        spatial.setMaterial(mat);
        if (Constants.DEBUG) {
            characterControl.setJumpSpeed(20);
        } else {
            characterControl.setJumpSpeed(5);
        }
        characterControl.setFallSpeed(100);
        characterControl.setGravity(9.81f);
        if (app instanceof GameClient) {
            GameClient g = (GameClient) app;
            g.getBulletAppState().getPhysicsSpace().add(characterControl);
            //spatial.addControl(this);
            camNode = new CameraNode("CharCamNode", app.getCamera().clone());
            camNode.setControlDir(ControlDirection.SpatialToCamera);
            //spatial.
//            this.setPhysicsLocation(new Vector3f(app.getCamera().getLocation().x, 150, app.getCamera().getLocation().z));
            characterControl.setViewDirection(app.getCamera().getDirection());
            //this.getViewDirection().y = 0;
            charNode = new Node(charName);
            charNode.attachChild(camNode);
            camNode.setLocalTranslation(new Vector3f(0, 1, -5));
            camNode.lookAt(charNode.getLocalTranslation(), Vector3f.UNIT_Y);
            animationControl = spatial.getControl(AnimControl.class);
            charNode.attachChild(spatial);
            charNode.addControl(characterControl);
            app.getRootNode().attachChild(charNode);
            //app.getRootNode().attachChild(spatial);
            animationControl.addListener(this);
            baseChannel = animationControl.createChannel();
            shootingChannel = animationControl.createChannel();
            topChannel = animationControl.createChannel();
            shootingChannel.addBone(animationControl.getSkeleton().getBone("Humerus.R"));
            shootingChannel.addBone(animationControl.getSkeleton().getBone("Ulna.R"));
            shootingChannel.addBone(animationControl.getSkeleton().getBone("Hand.R"));
        }
        //testData
        if (Constants.DEBUG) {
            testAttributs();
            testOutfit();
            setMaxHealth(100f);
            setActHealth(100f);
            spatial.setUserData("SurName", "-name");
            spatial.setUserData("ForeName", "test");
        }
    }

    public void testAttributs() {
        attributs.add(Attribute.AGILITY, 10f);
        attributs.add(Attribute.STRENGTH, 15f);
    }

    public void testOutfit() {
        Item[] someRnd = new Item[5];
        for (Item item : someRnd) {
            switch (Dice.nextInt(4)) {
                case 1:
                    item = new ItemBuilder().buildRandomGreen("armors.Chestplate", CharLevel);
                    outfit.add(this, (Armor) item);
                    break;
                case 2:
                    item = new ItemBuilder().buildRandomGreen("armors.Cape", CharLevel);
                    outfit.add(this, (Armor) item);
                    break;
                case 3:
                    item = new ItemBuilder().buildRandomGreen("armors.Helmet", CharLevel);
                    outfit.add(this, (Armor) item);
                    break;
                case 0:
                    item = new ItemBuilder().buildRandomGreen("weapons.Axe", CharLevel);
                    outfit.add(this, (Weapon) item);
                    break;
            }
            System.out.println(item.toTooltipText());
        }
    }

    public PlayerCharacter(CollisionShape shape, float stepHeight) {
        super(shape, stepHeight);
    }

    public PlayerCharacter() {
        super();
    }

    public boolean isEquipAble(Armor item) {
        return (charClass.isEquipAble(item) || skills.isEquipAble(item)) ? true : false;
    }

    public boolean isEquipAble(Weapon item) {
        return (charClass.isEquipAble(item) || skills.isEquipAble(item)) ? true : false;
    }

    public Attributes getAttributs() {
        return attributs;
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        if (channel == shootingChannel) {
            channel.setAnim("IdleTop");
        }
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }

    public String getAnimation() {
        return baseChannel.getAnimationName();
    }

    public void setAnimation(String name, float blendtime) {
        if (name.contains("Top")) {
            this.topChannel.setAnim(name, blendtime);
        } else {
            this.baseChannel.setAnim(name, blendtime);
        }
    }

    public void setAnimation(String name) {
        if (name.contains("Top")) {
            this.topChannel.setAnim(name);
        } else {
            this.baseChannel.setAnim(name);
        }
    }

    public void setLoopMode(LoopMode loopm) {
        this.baseChannel.setLoopMode(loopm);
        this.topChannel.setLoopMode(loopm);
    }

    public String getSurName() {
        return surName;
    }

    public String getForeName() {
        return foreName;
    }

    @Override
    public String getFullyName() {
        return foreName + " " + surName;
    }

    public String toLoginScreenText() {
        //return getFullyName() + "&#10;" + charRace + "&#10;Level: " + level;
        return getFullyName() + "\r\n" + charRace.toString() + "\r\nLevel: " + level;
    }

    public static PlayerCharacter deSerialize(SimpleApplication app, String serializedString, PlayerCharacter playerChar) {
        serializedString = serializedString.replace(serializedString.substring(0, serializedString.indexOf("{") + 1), "");
        PlayerCharacter pc = (PlayerCharacter) Char.deSerialize(app, serializedString, playerChar);
        return pc;
    }

    @Override
    public String serialize(SimpleApplication app) {
        StringBuilder sb = new StringBuilder(super.serialize(app));
        sb.append("Attributes{");
        sb.append(attributs.serialize()).append("};");
        sb.append("Outfit{");
        sb.append(outfit.serialize()).append("};");
        return sb.toString();
    }

    public Node getNode() {
        return charNode;
    }

    public CameraNode getCamNode() {
        return camNode;
    }
    
}
