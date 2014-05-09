/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.gui;

import de.sydsoft.sg_wolfskrone.logic.ChatMessage;
import de.sydsoft.sg_wolfskrone.logic.ServerListener;
import com.jme3.app.SimpleApplication;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import com.jme3.system.JmeContext;
import de.sydsoft.libst.util.Console;
import de.sydsoft.libst.util.Constants;
import de.sydsoft.sg_wolfskrone.entities.Player;
import de.sydsoft.sg_wolfskrone.entities.chars.PlayerCharacter;
import de.sydsoft.sg_wolfskrone.logic.PlayerLoaderMessage;
import de.sydsoft.sg_wolfskrone.logic.ServerAnswerMessage;
import de.sydsoft.sg_wolfskrone.logic.ServerRequestMessage;
import de.sydsoft.sg_wolfskrone.util.DBConnector;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author sythelux
 */
public class GameServer extends SimpleApplication {

    Server server;
    ServerListener serverListener;
    DBConnector dBConnector;

    public static void main(String[] args) {
        Constants.DEBUG = false;
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "--debug":
                    case "-D":
                        Constants.DEBUG = true;
                        break;
                }
            }
        }
        GameServer app = new GameServer();
        app.start(JmeContext.Type.Headless);
    }

    @Override
    public void simpleInitApp() {
        Serializer.registerClass(ChatMessage.class);
        Serializer.registerClass(ServerRequestMessage.class);
        Serializer.registerClass(PlayerLoaderMessage.class);
        Serializer.registerClass(ServerAnswerMessage.class);
        try {
            server = (Server) Network.createServer(0xface);
            server.start();
        } catch (IOException ex) {
            Console.errMsg(ex);
        }
        dBConnector = new DBConnector();
        serverListener = new ServerListener(this);
        server.addMessageListener(serverListener, ChatMessage.class);
        server.addMessageListener(serverListener, ServerRequestMessage.class);
        server.broadcast(new ChatMessage("Welcome to the server"));//TODO: spaeter mit Filters.dings()
//        for (Enumeration<String> e = LogManager.getLogManager().getLoggerNames(); e.hasMoreElements();) {
//            Logger.getLogger(e.nextElement()).setLevel(Level.OFF);
//        }
//        Logger.getLogger(Console.LOG).setLevel(Level.ALL);
    }
    private Scanner sc;

    @Override
    public void update() {
        super.update();
        sc = new Scanner(System.in);
        System.out.print(">");
        server.broadcast(new ChatMessage(sc.next()));
    }

    public DBConnector getDBConnector() {
        return dBConnector;
    }

    public Server getServer() {
        return server;
    }
}
