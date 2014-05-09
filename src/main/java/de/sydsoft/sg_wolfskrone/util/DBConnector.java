/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.util;

import com.jme3.math.Vector3f;
import de.sydsoft.sg_wolfskrone.entities.Player;

/**
 *
 * @author sythelux
 */
public class DBConnector {

    private static int nextID = 0;

    public static int getNextID() {
        return nextID++;
    }

    ;
    
    public Player getPlayer(String AccountName, String PaswordHash) {
        //TODO: openDatabaseConnection
        //request Player
        //if player not exists return null
        //else load all character and the lastPlayed
        Player p = new Player();
        p.newCharacter("Sinbad");
        return p;
    }

    public String[] getCharPositionFromName(String fullCharName) {
        //TODO: request position with name
        Vector3f v = new Vector3f(300f, 0f, -200f);
        return new String[]{v.getX() + "", v.getY() + "", v.getZ() + ""};
    }
}
