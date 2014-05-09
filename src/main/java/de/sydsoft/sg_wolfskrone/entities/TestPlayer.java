/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.entities;

import com.jme3.app.SimpleApplication;
import de.sydsoft.libst.util.Console;
import de.sydsoft.libst.util.Constants;
import de.sydsoft.sg_wolfskrone.entities.Player;
import de.sydsoft.sg_wolfskrone.gui.GameServer;

/**
 *
 * @author sythelux
 */
public class TestPlayer {

    public static void main(String[] args) {
        Constants.DEBUG = true;
        Player p = new Player();
        p.newCharacter("Dieter");
        for (int i = 0; i < 12; i++) {
            p.getActualCharacter().levelUp();
        }
        SimpleApplication app = new GameServer();
        app.start();
        p.getActualCharacter().initialize(app, "Sinbad");
        String serializeTest = p.serialize(app);
        Console.dbgMsg(serializeTest);
        Player p2 = Player.deSerialize(app, serializeTest);
        if (p2.equals(p)) {
            Console.dbgMsg("TestSpieler sind gleich");
        }
    }
}
