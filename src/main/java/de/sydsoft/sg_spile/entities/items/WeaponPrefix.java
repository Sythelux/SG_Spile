/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_spile.entities.items;

import de.sydsoft.sg_spile.util.Dice;

import java.util.EnumSet;
import java.util.ResourceBundle;

/**
 *
 * @author sythelux
 */
public enum WeaponPrefix {
    //waffentyp
    OBSIDIAN(),
    IRON(),
    SLAUGHTER();

    public String getName() {
        try{
            return ResourceBundle.getBundle("Item").getString("ItemPrefix." + this.name());
        }catch (Exception e){
            return "!<ItemPrefix." + this.name() +">!";            
        }
    }

    public static WeaponPrefix getByNumber(int num) {
        switch (num) {
            case 0:
                return WeaponPrefix.OBSIDIAN;
            case 1:
                return WeaponPrefix.IRON;
            case 2:
                return WeaponPrefix.SLAUGHTER;
        }
        return null;        
    }
    
    public static WeaponPrefix getByName(String name){
        for (WeaponPrefix wP : EnumSet.allOf(WeaponPrefix.class)) {
            if (wP.name().equals(name)) {
                return wP;
            }
        }
        return null;
    }

    public static WeaponPrefix Random() {
        return getByNumber(Dice.nextInt(COUNT));
    }
    private static final int COUNT = 3;
}
