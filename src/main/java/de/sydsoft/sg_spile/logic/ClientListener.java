/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_spile.logic;

import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;

import de.sydsoft.libst.util.Console;
import de.sydsoft.sg_spile.appstates.LoginState;
import de.sydsoft.sg_spile.entities.Player;
import de.sydsoft.sg_spile.gui.ingame.chat.ChatAreaController;
import de.sydsoft.sg_spile.util.LocHelper;

/**
 *
 * @author sythelux
 */
public class ClientListener implements MessageListener<Client> {

    ChatAreaController chatAreaController;
    LoginState state;
    Vector3f playerPos = null;

    public ClientListener(ChatAreaController chatAreaController) {
        this.chatAreaController = chatAreaController;
    }

    public ClientListener(LoginState state) {
        this.state = state;
    }

    @Override
    public void messageReceived(Client source, Message m) {
        if (m instanceof ChatMessage) {
            ChatMessage message = (ChatMessage) m;
            Console.infMsg("Client #" + source.getId() + " received: '" + message.getMessage() + "'");
            chatAreaController.append("\r\n" + LocHelper.timeAsChannelStamp() + " " + source.getId() + ": " + message.getMessage());
        } else if (m instanceof PlayerLoaderMessage) {
            PlayerLoaderMessage pl = (PlayerLoaderMessage) m;
            state.getScreen().login(Player.deSerialize(state.getGame(), pl.getSerializedPlayer()));
        } else if (m instanceof ServerAnswerMessage) {
            ServerAnswerMessage sAM = (ServerAnswerMessage) m;
            switch (sAM.getRequestNumber()) {
                case ServerRequestMessage.PLAYERPOSITION:
                    playerPos = new Vector3f(Float.parseFloat(sAM.getRequestItemAt(0)), Float.parseFloat(sAM.getRequestItemAt(1)), Float.parseFloat(sAM.getRequestItemAt(2)));
                    Console.infMsg("Client #" + source.getId() + " received: Player Position:" + playerPos.toString());
                    break;
            }
        }
    }

    public Vector3f getPlayerPos() {
        return playerPos;
    }

    public void setPlayerPos(Vector3f playerPos) {
        this.playerPos = playerPos;
    }
}
