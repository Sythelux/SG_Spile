/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.entities;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import de.sydsoft.sg_wolfskrone.entities.chars.PlayerCharacter;
import java.util.HashMap;

/**
 *
 * @author sythelux
 */
public class Player {

    private HashMap<String, PlayerCharacter> characters;
    private String choosenName = "";

    public Player() {
        characters = new HashMap<>();
    }

    /*
     * fuegt einen Neuen Character zur Liste aller Charaktere des Spielers hinzu
     * @return gibt true zurueck wenn kein alter character ueberschrieben wurde (koennte nuetzlich sein wenn man charactere loeschen will)
     */
    public boolean newCharacter(String fullName) {
        CapsuleCollisionShape capsule = new CapsuleCollisionShape(0.2f, 0.1f);
        if (characters.put(fullName, new PlayerCharacter(capsule, 0.01f)) == null) {
            if ("".equals(choosenName)) {
                choosenName = fullName;
            }
            return true;
        } else {
            return false;
        }
    }

    public PlayerCharacter getActualCharacter() {
        return characters.get(choosenName);
    }

    public void setChoosenName(String choosenName) {
        this.choosenName = choosenName;
    }

    public HashMap<String, PlayerCharacter> getCharacters() {
        return characters;
    }

    public String getChoosenName() {
        return choosenName;
    }

    public String serialize(SimpleApplication app) {//TODO braucht spaeter serverGame um spatial zu bauen
        StringBuilder sb = new StringBuilder();
        sb.append(characters.keySet().size()).append(";");
        sb.append("characters{");
        for (String key : characters.keySet()) {
            sb.append("char_").append(key).append("{").append(characters.get(key).serialize(app)).append("}_char");
            sb.append(";");
        }
        sb.replace(sb.lastIndexOf(";"), sb.length(), "");
        sb.append("}");
        sb.append(choosenName);
        return sb.toString();
    }

    public static Player deSerialize(SimpleApplication app, String serializedString) {
        Player p = new Player();
        p.choosenName = serializedString.substring(serializedString.lastIndexOf("}") + 1);
        serializedString = serializedString.replace("characters{", "");
        serializedString = serializedString.replace("}" + p.choosenName, "");
        String currentCharName = "";
        int numPlayers = Integer.parseInt(serializedString.substring(0, serializedString.indexOf(";")));
        serializedString = serializedString.replaceFirst(numPlayers+";", "");
        for (int i = 0; i < numPlayers; i++) {
            currentCharName= serializedString.substring(0, serializedString.indexOf("{")).replace("char_", "");
            p.newCharacter(currentCharName);
            p.characters.put(currentCharName, PlayerCharacter.deSerialize(app, serializedString, p.characters.get(currentCharName)));
            serializedString = serializedString.replace(serializedString.substring(serializedString.indexOf("char_"),serializedString.indexOf("_char") ), "");
        }
        return p;
    }
}
