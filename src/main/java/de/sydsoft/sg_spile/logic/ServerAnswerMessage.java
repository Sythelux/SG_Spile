/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_spile.logic;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author sythelux
 */
@Serializable
public class ServerAnswerMessage extends AbstractMessage {

    private String[] requestedItems = {""};
    private int requestNumber = -1;

    public ServerAnswerMessage() {
    }

    public ServerAnswerMessage(int requestNumber, String[] requestedItems) {
        this.requestNumber = requestNumber;
        this.requestedItems = requestedItems;
    }

    public int getRequestNumber() {
        return requestNumber;
    }

    public String getRequestItemAt(int i) {
        return requestedItems[i];
    }
}
