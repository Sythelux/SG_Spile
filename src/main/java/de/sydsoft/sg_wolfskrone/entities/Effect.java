/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.entities;

import de.sydsoft.sg_wolfskrone.entities.chars.PlayerCharacter;
import java.util.Properties;

/**
 *
 * @author sythelux
 */
public class Effect extends Properties {

    private long id = -1;
    private boolean active = false;

    public Effect(Properties defaults) {
        super(defaults);
    }

    public Effect() {
    }

    public void activate(PlayerCharacter pc) {
        if (active) {
            return;
        }
        active = true;
    }

    public long getID() {
        return id;
    }
    
}
