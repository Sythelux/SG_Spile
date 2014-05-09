/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.util;

import java.util.Random;

/**
 *
 * @author sythelux
 */
public class Dice {
    public static Random rnd = new Random();
    //public Random dice = new Random();
    
    
    public static int nextInt(int n){//return goes from 0 to n-1!!
        return rnd.nextInt(n);
    }
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            System.out.print("|"+Dice.nextInt(3));
        }
    }
}
