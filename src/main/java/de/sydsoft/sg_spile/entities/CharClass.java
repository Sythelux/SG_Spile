/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_spile.entities;

import java.util.ArrayList;

import de.sydsoft.sg_spile.entities.items.Armor;
import de.sydsoft.sg_spile.entities.items.ArmorPrefix;
import de.sydsoft.sg_spile.entities.items.Weapon;

/**
 *
 * @author sythelux
 */
public class CharClass {

    private ArrayList<ArmorPrefix> equipClass = new ArrayList<>();
//    private Effect[] skills = {new Effect()};

    public CharClass(ArrayList<ArmorPrefix> equipClass) {
        this.equipClass = equipClass;
    }

    public CharClass() {
    }

    public boolean isEquipAble(Weapon item) {
        return equipClass.contains(item.getPrefix());
    }

    public boolean isEquipAble(Armor item) {
        return equipClass.contains(item.getPrefix());
    }

    public static CharClass deSerialize(String serializedString){//TODO
        CharClass cc = new CharClass();
        serializedString = serializedString.replaceFirst(";", "");
        return cc;
    }
    
    public String serialize() {//TODO
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }
}
