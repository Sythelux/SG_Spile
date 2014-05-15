/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_spile.logic;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;

import de.sydsoft.libst.util.Console;
import de.sydsoft.sg_spile.gui.GameServer;
import de.sydsoft.sg_spile.util.DBConnector;
import de.sydsoft.sg_spile.util.NetworkConstants;

/**
 *
 * @author sythelux
 */
public class ServerListener implements MessageListener<HostedConnection>, NetworkConstants {

    GameServer g;

    public ServerListener(GameServer g) {
        this.g = g;
    }

    public void messageReceived(HostedConnection source, Message message) {
        if (message instanceof ChatMessage) {
            // do something with the message
            ChatMessage chatMessage = (ChatMessage) message;
            Console.infMsg("Server received '" + chatMessage.getMessage() + "' from client #" + source.getId());
        } else if (message instanceof ServerRequestMessage) {
            DBConnector dbC = g.getDBConnector();
            ServerRequestMessage srm = (ServerRequestMessage) message;
            switch (srm.getRequestNumber()) {
                case ServerRequestMessage.PLAYER:
                    Console.infMsg("Server received Player request from client #" + source.getId());
                    source.send(new PlayerLoaderMessage(dbC.getPlayer(srm.getRequestItemAt(0), srm.getRequestItemAt(1)).serialize(g)));
                    break;
                case ServerRequestMessage.PLAYERPOSITION:
                    Console.infMsg("Server received Player Position request from client #" + source.getId());
                    source.send(new ServerAnswerMessage(ServerRequestMessage.PLAYERPOSITION, dbC.getCharPositionFromName(srm.getRequestItemAt(0))));
                    break;

            }
        }
    }
}
