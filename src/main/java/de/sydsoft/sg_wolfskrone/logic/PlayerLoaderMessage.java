/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.logic;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *  Laed einen Spieler und dessen Charaktere von Server //TODO: verschlüsselt uebertragen
 * @author sythelux
 */
@Serializable
public class PlayerLoaderMessage extends AbstractMessage{
    private String player;
    
    public PlayerLoaderMessage() {
    }
    
    public PlayerLoaderMessage(String serializedPlayer) {
        player = serializedPlayer;
    }

    public String getSerializedPlayer() {
        return player;
    }   
}
