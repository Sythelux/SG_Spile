/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.logic;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import java.util.logging.Level;

/**
 *
 * @author sythelux
 */
@Serializable
public class ChatMessage extends AbstractMessage{
    private String message;
    private Level channel;

    public ChatMessage() {
    }
    
    public ChatMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    
}
