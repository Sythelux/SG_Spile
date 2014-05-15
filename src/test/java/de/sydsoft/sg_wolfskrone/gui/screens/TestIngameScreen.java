/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.gui.screens;

import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;

import de.lessvoid.nifty.Nifty;
import de.sydsoft.libst.util.Constants;
import de.sydsoft.sg_spile.appstates.ClientMainAppState;
import de.sydsoft.sg_spile.gui.screens.IngameScreen;

/**
 *
 * @author sythelux
 */
public class TestIngameScreen extends SimpleApplication {

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
        TestIngameScreen tis = new TestIngameScreen();
        tis.start();
    }

    @Override
    public void simpleInitApp() {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        Nifty nifty = niftyDisplay.getNifty();
        IngameScreen ingameScreen =new IngameScreen(new ClientMainAppState());
        nifty.fromXml("Interface/Nifty/NiftyScreens.xml", "ingame", ingameScreen);
        ingameScreen.bind(nifty, nifty.getScreen("ingame"));
        nifty.setDebugOptionPanelColors(true);
    }
}
