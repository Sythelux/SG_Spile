package de.sydsoft.sg_wolfskrone.entities;

import de.sydsoft.sg_wolfskrone.entities.chars.Char;

/**
 *
 * @author sythelux
 */
public class Group {
    Char[] groupMembers = new Char[5];
    
    public boolean isSmallGroup(){
        return (groupMembers.length<=3);
    }
}
