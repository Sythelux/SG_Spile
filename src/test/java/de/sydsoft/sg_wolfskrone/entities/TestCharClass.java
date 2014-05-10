/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.entities;

import de.sydsoft.sg_wolfskrone.entities.items.armors.Chestplate;
import de.sydsoft.sg_wolfskrone.entities.items.ArmorPrefix;
import java.util.ArrayList;

/**
 *
 * @author sythelux
 */
public class TestCharClass {

    public static void main(String[] args) {
        testEquipable();
    }
    
    public static void testEquipable(){
        ArrayList<ArmorPrefix> druidItems = new ArrayList<>();
        druidItems.add(ArmorPrefix.LEATHER);
        druidItems.add(ArmorPrefix.CLOTH);
        CharClass druid = new CharClass(druidItems);
        Chestplate i = (Chestplate) new ItemBuilder().buildRandomBlue("Chestplate", 5);
        if (druid.isEquipAble(i)) {
            System.out.println("yeah");
        } else{
            System.out.println("nope");
        }        
    }
}
