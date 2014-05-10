/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.entities;

/**
 *
 * @author sythelux
 */
public class TestAttributs {

    public static void main(String[] args) {
        Attributes attributs2 = new Attributes();
        attributs2.add(Attribute.AGILITY, 75f);
        attributs2.add(Attribute.STRENGTH, 80f);
        
        Attributes attributs = new Attributes();
        attributs.add(Attribute.STRENGTH, 65f);
        attributs.add(Attribute.AGILITY, 100.5f);
        System.out.println("a1:");
        System.out.println(attributs.toString());
        System.out.println("a2:");
        System.out.println(attributs2.toString());
        attributs.put(Attribute.AGILITY, 20f);
        System.out.println("a1 put 20 Agi");
        System.out.println(attributs.toString());
        attributs.add(Attribute.AGILITY, 70f);
        System.out.println("a1 add 70 Agi");
        System.out.println(attributs.toString());
        Attributes attributs3 = new Attributes();
        attributs3.add(Attribute.AGILITY, 60f);
        attributs.putAll(attributs2);
        System.out.println("a1 put a2");
        System.out.println(attributs.toString());
        attributs3.addAll(attributs);
        System.out.println("a3 add a1");
        System.out.println(attributs3.toString());
    }
}
