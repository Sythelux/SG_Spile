package de.sydsoft.sg_spile.entities.items;

import java.util.EnumSet;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import de.sydsoft.sg_spile.util.Dice;

/**
 *
 * @author sythelux
 */
public enum ArmorPrefix {
    //ruestungstyp

    LEATHER(),
    CLOTH(),
    LATHY();

    public String getName() {
        try{
            return ResourceBundle.getBundle("Item").getString("ItemPrefix." + this.name());
        } catch (MissingResourceException e) {
            return "!<ItemPrefix." + this.name() +">!";
        }
    }

    public static ArmorPrefix getByNumber(int num) {
        switch (num) {
            case 0:
                return ArmorPrefix.LEATHER;
            case 1:
                return ArmorPrefix.CLOTH;
        }
        return null;
    }

    public static ArmorPrefix getByName(String name){
        for (ArmorPrefix aP : EnumSet.allOf(ArmorPrefix.class)) {
            if (aP.name().equals(name)) {
                return aP;
            }
        }
        return null;
    }

    
    public static ArmorPrefix Random() {
        return getByNumber(Dice.nextInt(EnumSet.allOf(ArmorPrefix.class).size()));
    }
}
