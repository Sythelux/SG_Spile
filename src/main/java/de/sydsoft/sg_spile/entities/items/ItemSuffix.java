/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_spile.entities.items;

import de.sydsoft.sg_spile.entities.Attribute;
import de.sydsoft.sg_spile.util.Dice;

import java.util.EnumSet;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author sythelux
 */
public enum ItemSuffix {
    //zusatzwert
    BEAR(new Attribute[]{Attribute.STRENGTH}),
    MONKEY(new Attribute[]{Attribute.STRENGTH,Attribute.AGILITY}),
    MITHRIL(new Attribute[]{Attribute.AGILITY,Attribute.PRESTIGE});

    private ItemSuffix(Attribute[] attributKeys) {
        this.attributKeys = attributKeys;
    }

    public String getName() {
        try{
            return ResourceBundle.getBundle("Item").getString("ItemSuffix." + this.name());
        } catch (MissingResourceException e) {
            return "!<ItemSuffix." + this.name() +">!";
        }
    }

    public static ItemSuffix getByNumber(int num) {
        switch (num) {
            case 0:
                return ItemSuffix.BEAR;
            case 1:
                return ItemSuffix.MONKEY;
            case 2:
                return ItemSuffix.MITHRIL;
        }
        return null;
    }
    public static ItemSuffix getByName(String name){
        for (ItemSuffix iS : EnumSet.allOf(ItemSuffix.class)) {
            if (iS.name().equals(name)) {
                return iS;
            }
        }
        return null;
    }

    public static ItemSuffix Random() {
        return getByNumber(Dice.nextInt(EnumSet.allOf(ItemSuffix.class).size()));
    }

    public Attribute[] getAttributKeys() {
        return attributKeys;
    }
    
    private Attribute[] attributKeys;
}
