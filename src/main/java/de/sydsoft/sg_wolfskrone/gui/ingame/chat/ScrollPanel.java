/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.gui.ingame.chat;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.controls.ScrollPanel.AutoScroll;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.xml.xpp3.Attributes;
import java.util.Properties;

/**
 *
 * @author sythelux
 */
public class ScrollPanel implements Controller{

    @Override
    public void bind(Nifty nifty, Screen screen, Element element, Properties parameter, Attributes controlDefinitionAttributes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void init(Properties parameter, Attributes controlDefinitionAttributes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onStartScreen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onFocus(boolean getFocus) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean inputEvent(NiftyInputEvent inputEvent) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    AutoScroll getAutoScroll() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void setAutoScroll(AutoScroll autoScroll) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
