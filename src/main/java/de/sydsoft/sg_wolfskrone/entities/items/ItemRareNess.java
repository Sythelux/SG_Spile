/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.entities.items;

import java.awt.Color;
import java.util.EnumSet;

/**
 *
 * @author dbft111asc
 */
public enum ItemRareNess {

    GREEN(new Color(0x00FF00)),
    BLUE(new Color(0x0000FF)),
    VIOLETT(new Color(0x8500FF));

    private ItemRareNess(Color cl) {
        this.color = cl;
    }

    public Color getColor() {
        return color;
    }
    
    private Color color;
    
    
    public static ItemRareNess getByNumber(int num){
        switch (num) {
            case 0:
                return ItemRareNess.GREEN;
            case 1:
                return ItemRareNess.BLUE;
            case 2:
                return ItemRareNess.VIOLETT;
        }
        return null;
    }
    
    public static ItemRareNess getByName(String name){
        for (ItemRareNess iRN : EnumSet.allOf(ItemRareNess.class)) {
            if (iRN.name().equals(name)) {
                return iRN;
            }
        }
        return null;
    }
}
