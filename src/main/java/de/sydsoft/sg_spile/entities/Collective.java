/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_spile.entities;

import de.sydsoft.sg_spile.entities.chars.PlayerCharacter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author sythelux
 */
public class Collective {
    Inventory bank = new Inventory(10*5);
    /* Ganzzahl zeigt den Rang an. Gildenmeister ist immer in einer Arraylist mit rang 0. neue spieler werden standartm‰ﬂig in die letzte gruppe gehangen*/
    HashMap<Integer, ArrayList<PlayerCharacter>> members;

    @SuppressWarnings("unused")
	@Deprecated
    private Collective() {
        members = new HashMap<>();
    }

    public Collective(PlayerCharacter leader) {
        members = new HashMap<>();
        members.put(0, new ArrayList<PlayerCharacter>());
        members.get(0).add(leader);
        members.put(1, new ArrayList<PlayerCharacter>());
    }
    
    public PlayerCharacter getLeader() {
        return members.get(0).get(0);
    }

    public PlayerCharacter getMember(int group, int member) {
        return members.get(group).get(member);
    }

    public ArrayList<PlayerCharacter> getGroup(int group) {
        return members.get(group);
    }

    public int getGroupNumber(PlayerCharacter member) {
        for (Integer integer : members.keySet()) {
            if (members.get(integer).contains(member)) {
                return integer;
            }
        }
        return -1;
    }
    
    public void join(PlayerCharacter newMember) {
        members.get(members.size()-1).add(newMember);
    }
    
    public void leave(PlayerCharacter member) {
        members.get(getGroupNumber(member)).remove(member);
    }
}
