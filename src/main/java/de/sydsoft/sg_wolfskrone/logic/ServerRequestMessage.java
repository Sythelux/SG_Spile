/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.logic;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author sythelux
 */
@Serializable
public class ServerRequestMessage extends AbstractMessage {

    public static final int PLAYER = 0;
    public static final int PLAYERPOSITION = 1;
    private int requestNumber = -1;
    private String[] requestedItems = {""};

    public ServerRequestMessage() {
    }

    public ServerRequestMessage(int requestNumber) {
        this.requestNumber = requestNumber;
    }

    public ServerRequestMessage(int requestNumber, String[] requestItems) {
        this.requestNumber = requestNumber;
        requestedItems = requestItems;
//        if(Serializer.getExactSerializer(ServerRequestMessage.class)==null)
//            Serializer.registerClass(ServerRequestMessage.class);
    }

    public int getRequestNumber() {
        return requestNumber;
    }

    public String getRequestItemAt(int i) {
        return requestedItems[i];
    }
}
