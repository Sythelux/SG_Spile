/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_spile.entities;

import java.util.ArrayList;

import de.sydsoft.sg_spile.entities.skills.Skill;

/**
 *
 * @author sythelux
 */
public class Skills extends  ArrayList<Skill>{
	private static final long	serialVersionUID	= 7598370260105470875L;

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
