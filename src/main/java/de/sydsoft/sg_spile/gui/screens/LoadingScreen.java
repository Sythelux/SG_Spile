/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_spile.gui.screens;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.sydsoft.libst.util.SimpleProgressListener;
import de.sydsoft.sg_spile.appstates.ClientMainAppState;

/**
 *
 * @author sythelux
 */
public class LoadingScreen extends MainScreenController implements SimpleProgressListener {

    public static final String SCREENNAME = "loading";
    TextRenderer guiPercentage;
    TextRenderer guiLoadingText;

    public LoadingScreen(ClientMainAppState state) {
        super(state);
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        super.bind(nifty, screen);
        guiPercentage = getElementAt("LoadingPercentage", SCREENNAME).getRenderer(TextRenderer.class);
        guiLoadingText = getElementAt("LoadingText", SCREENNAME).getRenderer(TextRenderer.class);
    }

    public void quit() {
        nifty.gotoScreen("ingame");
        state.setCompleted(true);
    }

    @Override
    public void startProgress(String string) {
        guiLoadingText.setText(string);
        guiPercentage.setText("0%");
    }

    @Override
    public void updateProgress(String string) {
        guiLoadingText.setText(string);
    }

    @Override
    public void updatePercentage(int i) {
        guiPercentage.setText(i + "%");
    }
}
