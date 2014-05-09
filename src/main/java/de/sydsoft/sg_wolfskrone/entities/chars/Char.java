/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.entities.chars;

import com.jme3.animation.AnimEventListener;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import de.sydsoft.sg_wolfskrone.entities.CharClass;
import de.sydsoft.sg_wolfskrone.entities.Inventory;
import de.sydsoft.sg_wolfskrone.entities.Race;
import de.sydsoft.sg_wolfskrone.entities.Skills;

/**
 *
 * @author sythelux
 */
public abstract class Char implements AnimEventListener { // Character bereits benutzt

    protected CharacterControl characterControl;
    protected float actHealth = 0;
    protected float maxHealth = 15;
    protected float airTime;
    protected Skills skills = new Skills();
    protected Inventory inventory = new Inventory();
    protected byte level = 1;
    protected CharClass charClass = new CharClass();
    protected Race charRace = new Race();
    protected int CharLevel = 5;//TODO was war das ????
    protected static byte MAXLEVEL = 50;
    protected String foreName = "", surName = "";

    public Char(CollisionShape shape, float stepHeight) {
        characterControl = new CharacterControl(shape, stepHeight);
    }

    public Char() {
        characterControl = new CharacterControl();
    }

    public void initialize(SimpleApplication app, String charName) {
//        Texture tex = app.getAssetManager().loadTexture("Textures/Characters/" + charName + "/" + charName + ".tga");
//        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
//        //mat.setTexture("m_ColorMap", tex);
//        spatial.setMaterial(mat);
        foreName = charName;
    }

    public void setAirTime(float airTime) {
        this.airTime = airTime;
    }

    public void addAirTime(float airTime) {
        this.airTime += airTime;
    }

    public float getAirTime() {
        return airTime;
    }

    public void setMaxHealth(float health) {
        maxHealth = health;
    }

    public void heal(float amnt) {
        if ((actHealth += amnt) >= maxHealth) {
            actHealth = maxHealth;
        }
    }

    /*
     * @return returns true if the Player is death
     */
    public boolean hurt(float amnt) {
        if ((actHealth -= amnt) <= 0) {
            actHealth = 0;
            return true;
        } else {
            return false;
        }
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public float getActHealth() {
        return actHealth;
    }

    public void setActHealth(float actHealth) {
        this.actHealth = actHealth;
    }

    public String getFullyName() {
        return foreName;
    }

    public int getCharLevel() {
        return CharLevel;
    }

    public CharacterControl getCharacterControl() {
        return characterControl;
    }
    
    

    public static Char deSerialize(SimpleApplication app, String serializedString, Char charToDeserialize) {
        String[] splittedSerString = serializedString.split(";");
        charToDeserialize.actHealth = Float.parseFloat(splittedSerString[0]);
        serializedString = serializedString.replace(splittedSerString[0] + ";", "");
        charToDeserialize.maxHealth = Float.parseFloat(splittedSerString[1]);
        serializedString = serializedString.replace(splittedSerString[1] + ";", "");
        charToDeserialize.skills = Skills.deSerialize(serializedString);
        serializedString = serializedString.replaceFirst(charToDeserialize.skills.serialize() + ";", "");
        charToDeserialize.inventory = Inventory.deSerialized(serializedString);
        serializedString = serializedString.replaceFirst(charToDeserialize.inventory.serialize() + ";", "");
        splittedSerString = serializedString.split(";");//TODO testen wenn Inventory und Skills deserilisierer implementiert sind
        charToDeserialize.level = Byte.parseByte(splittedSerString[0]);//TODO siehe zeile drueber
        serializedString = serializedString.replace(splittedSerString[0] + ";", "");
        charToDeserialize.charClass = CharClass.deSerialize(serializedString);
        serializedString = serializedString.replaceFirst(charToDeserialize.charClass.serialize() + ";", "");
        charToDeserialize.charRace = Race.deSerialize(serializedString);
        serializedString = serializedString.replaceFirst(charToDeserialize.charRace.serialize() + ";", "");
        splittedSerString = serializedString.split(";");//TODO testen wenn Klasse und Rasse deserilisierer implementiert sind
        charToDeserialize.CharLevel = Byte.parseByte(splittedSerString[0]);//TODO siehe zeile drueber        
        serializedString = serializedString.replace(splittedSerString[0] + ";", "");
//        if (serializedString.substring(0, 15).contains("UserData{}")) {
//            serializedString = serializedString.replace("UserData{}", "");
//        } else {
//            serializedString = serializedString.replace("UserData{", "");
//            splittedSerString = serializedString.split(";");
//            for (int i = 0; i < Integer.parseInt(splittedSerString[0]); i++) {
//                //charToDeserialize.initialize(app, splittedSerString[i+1].split(":")[1]);
//                charToDeserialize.initialize(app, "Sinbad");
//                charToDeserialize.spatial.setUserData(splittedSerString[i + 1].split(":")[0], splittedSerString[i + 1].split(":")[1]);
//            }
//            serializedString = serializedString.substring(serializedString.indexOf("}") + 2);
//        }
        return charToDeserialize;
    }

    public String serialize(SimpleApplication app) {
        StringBuilder sb = new StringBuilder();
        sb.append(actHealth).append(";").append(maxHealth).append(";");
        sb.append(skills.serialize()).append(";");
        sb.append(inventory.serialize()).append(";");
        sb.append(level).append(";");
        sb.append(charClass.serialize()).append(";");
        sb.append(charRace.serialize()).append(";");
        sb.append(CharLevel).append(";");
//        sb.append("UserData{");
//        if (spatial != null) {
//            sb.append(spatial.getUserDataKeys().size()).append(";");
//            for (String key : spatial.getUserDataKeys()) {
//                sb.append(key).append(":").append(spatial.getUserData(key));
//                sb.append(";");
//            }
//            sb.replace(sb.lastIndexOf(";"), sb.length(), "");
//        }
//        sb.append("};");
        return sb.toString();
    }

    public boolean levelUp() {
        if (level < MAXLEVEL) {
            level++;
            return true;
        } else {
            return false;
        }
    }
}
