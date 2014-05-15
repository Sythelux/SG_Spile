/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_spile.gui.screens;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.sydsoft.sg_spile.appstates.ClientMainAppState;

/**
 *
 * @author sythelux
 */
public class MainScreenController implements ScreenController {

    protected Element optionsPopUp;
    protected Nifty nifty;
    protected Screen screen;
    protected ClientMainAppState state;

    public MainScreenController(ClientMainAppState state) {
        this.state = state;
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        if (optionsPopUp == null) {
            optionsPopUp = nifty.createPopup("optionsPopup");
            
        }
    }

    @Override
    public void onStartScreen() {
        System.out.println("onStartScreen");
    }

    @Override
    public void onEndScreen() {
        System.out.println("onEndScreen");
    }

    protected Element getElementAtCurrentScreen(final String id) {
        return nifty.getCurrentScreen().findElementByName(id);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	protected Controller getElementControllerAtCurrentScreen(final String id, Class requestedControlClass) {
        return nifty.getCurrentScreen().findControl(id, requestedControlClass);
    }

    protected Element getElementAt(final String id, final String screen) {
        return nifty.getCurrentScreen().findElementByName(id);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	protected Controller getElementControllerAt(final String id, Class requestedControlClass, final String screen) {
        return nifty.getCurrentScreen().findControl(id, requestedControlClass);
    }

    protected Element getPopUpElement(final String id, Element popUp) {
        return popUp.findElementByName(id);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	protected Controller getPopUpElementController(final String id, Class requestedControlClass, Element popUp) {
        return popUp.findControl(id, requestedControlClass);
    }

    public void buttonClicked(String guiElementID) {
        switch (guiElementID) {
            case "OptionsButton":
                nifty.showPopup(nifty.getCurrentScreen(), optionsPopUp.getId(), null);
                break;
        }
    }
}
