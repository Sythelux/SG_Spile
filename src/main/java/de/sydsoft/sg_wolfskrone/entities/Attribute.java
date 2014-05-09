
package de.sydsoft.sg_wolfskrone.entities;

import java.util.EnumSet;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * ENUM mit allen verfügbaren typen von Attributen
 * @author sythelux
 */
public enum Attribute {

    AGILITY(),//Beweglichkeit
    STRENGTH(),//Staerke
    PRESTIGE(),//Ansehen
    ENDURANCE();//AUSDAUER
    
    public String getName() {
        try{
            return ResourceBundle.getBundle("Item").getString( this.getClass().getSimpleName() + "." + this.name());
        }catch (Exception e){
            return "!" + this.getClass().getSimpleName() + "." + this.name() +"!";            
        }
    }

    public static Attribute Random() {
        return getByNumber(new Random().nextInt(EnumSet.allOf(Attribute.class).size()));
    }
    
    public static Attribute getByNumber(int num){
        switch (num) {
            case 0:
                return Attribute.AGILITY;
            case 1:
                return Attribute.STRENGTH;
            case 2:
                return Attribute.PRESTIGE;
            case 3:
                return Attribute.ENDURANCE;
        }
        return null;
    }

    public static Attribute getByName(String name){
        for (Attribute a : EnumSet.allOf(Attribute.class)) {
            if (a.name().equals(name)) {
                return a;
            }
        }
        return null;
    }

}
