/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.entities;

import de.sydsoft.sg_wolfskrone.entities.skills.Skill;
import java.util.ArrayList;

/**
 *
 * @author sythelux
 */
public class Skills extends  ArrayList<Skill>{

    public boolean isEquipAble(Item item) {
        return false;
    }
    
    public static Skills deSerialize(String serializedString){//TODO
        Skills s = new Skills();
        return s;
    }
    
    public String serialize(){//TODO
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }
}
