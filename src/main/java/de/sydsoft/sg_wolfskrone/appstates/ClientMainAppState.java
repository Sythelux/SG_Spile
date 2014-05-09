/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.renderer.RenderManager;
import de.sydsoft.sg_wolfskrone.gui.GameClient;
import de.sydsoft.sg_wolfskrone.gui.screens.MainScreenController;

/**
 *
 * @author sythelux
 */
public class ClientMainAppState implements AppState {

    protected boolean enabled = false;
    protected boolean initialized = false;
    protected boolean completed = false;
    protected GameClient g;
    protected MainScreenController screenController;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        g = (GameClient) app;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void setEnabled(boolean active) {
        enabled = active;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
    }

    @Override
    public void update(float tpf) {
    }

    @Override
    public void render(RenderManager rm) {
    }

    @Override
    public void postRender() {
    }

    @Override
    public void cleanup() {
        enabled = false;
    }
    
    public GameClient getGame() {
        return g;
    }

    public void setScreenController(MainScreenController screenController) {
        this.screenController = screenController;
    }
}
