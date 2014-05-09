/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.entities;

import java.util.ArrayList;

/**
 *
 * @author sythelux
 */
public class Inventory {

    private byte maxSize = 2 ^ 4;
    private ArrayList<Item> items = new ArrayList<>();

    public Inventory(byte maxSize) {
        this.maxSize = maxSize;
    }

    public Inventory() {
    }

    Inventory(int maxSize) {
        this.maxSize = (byte) maxSize;
    }

    public synchronized boolean add(Item item) {
        boolean added = false;
        if (this.contains(item)) {
            if (item.isStackable()) {
                int c = 0;
                for (Item i : items) {
                    if (i.getName().equals(item.getName())) {
                        c++;
                    }
                    if (c < i.getMaxStackCount()) {
                        items.add(i);
                        added = true;
                    } else {
                        if (!isFull()) {
                            items.add(i);
                            added = true;
                        }
                    }
                }
            }
        } else {
            if (!isFull()) {
                items.add(item);
                added = true;
            }
        }
        return added;
    }

    public Integer getSize() {
        ArrayList<Integer> stacks = new ArrayList<>();
        int stackc = 0;
        for (Item item : items) {
            for (Item item2 : items) {
                if (item.getName().equals(item2.getName()) && item.isStackable() && item != item2) {
                    stackc++;
                    if (stackc == item.getMaxStackCount()) {
                        stacks.add(stackc);
                        stackc = 0;
                    }
                }
            }
            stackc = 0;
        }
        return stacks.size();
    }

    public boolean isFull() {
        return (getSize() >= maxSize) ? true : false;
    }

    public synchronized void remove(Item item) {
        if (this.contains(item)) {
            items.remove(item);
        }
    }

    public boolean contains(Item item) {
        for (Item item1 : items) {
            if (item.getName().contains(item1.getName())) {
                return true;
            }
        }
        return false;
    }

    public static Inventory deSerialized(String serializedString){//TODO
        Inventory inv = new Inventory();
        return inv;
    }
    
    public String serialize() {//TODO
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }
}