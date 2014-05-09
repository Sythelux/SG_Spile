/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import de.sydsoft.sg_wolfskrone.gui.screens.IntroScreen;

/**
 *
 * @author sythelux
 */
public class IntroState extends ClientMainAppState {

    String skipS = "skip";

    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        app.getInputManager().addMapping(skipS, new KeyTrigger(KeyInput.KEY_SPACE));
        app.getInputManager().addMapping(skipS, new KeyTrigger(KeyInput.KEY_RETURN));
        app.getInputManager().addMapping(skipS, new KeyTrigger(KeyInput.KEY_ESCAPE));
        app.getInputManager().addListener(skipH, skipS);
        enabled = true;
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (completed) {
            g.toLoginScreen();
        }
    }

    @Override
    public void cleanup() {
        super.cleanup();
    }
    
    
    private ActionListener skipH = new ActionListener() {

        public void onAction(String name, boolean keyPressed, float tpf) {
            if (keyPressed) {
                switch (((IntroScreen) screenController).getCurrentScreenID()) {
                    case "startSydSoft":
//                        ((IntroScreen) screenController).setRunTime(5000);
                        ((IntroScreen) screenController).toWolfskroneIntro();
                        break;
                    case "startWolfskrone":
//                        ((IntroScreen) screenController).setRunTime(10000);
                        ((IntroScreen) screenController).toLoginScreen();
                        g.getInputManager().deleteMapping(skipS);
                        g.getInputManager().removeListener(skipH);
                        break;
                }
            }
        }
    };
}
