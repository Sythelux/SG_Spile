/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.entities;

import de.sydsoft.sg_wolfskrone.util.DBConnector;
import de.sydsoft.sg_wolfskrone.entities.chars.PlayerCharacter;
import de.sydsoft.sg_wolfskrone.entities.items.ItemRareNess;
import de.sydsoft.sg_wolfskrone.entities.items.ItemSuffix;
import java.util.ArrayList;
import java.util.ListResourceBundle;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 *
 * @author sythelux
 */
public abstract class Item {

    protected int id;
    protected Effect effect;
    protected Attributes attributs;
    protected ItemSuffix suffix;
    protected ItemRareNess rareNess;
    //protected Skill skill;
    protected String name;
    protected byte maxStackCount = 1;
    //protected ArrayList<Item> includedItems; TODO: anders, RezeptKlasse oder so
    protected String description = "";

    public Item(ItemBuilder builder) {
        this.attributs = builder.getAttributs();
        this.id = builder.getId();
        this.suffix = builder.getSuffix();
        this.rareNess = builder.getRareNess();
        this.name = builder.getName();
    }

    public Item() {
    }

    public Item(ItemSuffix suffix, ItemRareNess rareNess) {
        this.id = DBConnector.getNextID();
        this.suffix = suffix;
        this.rareNess = rareNess;
    }

    @Override
    public String toString() {
        try {
            return "ItemID#" + id + " " + ResourceBundle.getBundle("ItemName").getString(name) + " " + suffix.getName();
        } catch (MissingResourceException e) {
            return "ItemID#" + id + " !<" + name + ">! " + suffix.getName();
        }
    }

    public String toTooltipText() {
        StringBuilder strB = new StringBuilder();
        strB.append("<HTML>").append("<h1 style=\"color: #");
        String rgb = Integer.toHexString(rareNess.getColor().getRGB());
        strB.append(rgb.substring(2, rgb.length()));
        strB.append(";\">").append(toString().replace("ItemID#" + id + " ", "")).append("</h1>").append("<p>");
        strB.append(attributs.toTooltipText());
        strB.append("</p>").append("<p style=\"color: #008000;\">").append(description).append("</p>").append("</HTML>");
        return strB.toString();
    }

    public String getName() {
        return name;
    }

    public boolean isStackable() {
        return (maxStackCount > 1) ? true : false;
    }

    public byte getMaxStackCount() {
        return maxStackCount;
    }

    public void setAttribut(Attribute key, int value) {
        attributs.put(key, (float) value);
    }

    public Attributes getAttributs() {
        return attributs;
    }

    public void activate(PlayerCharacter pc) {
        if (hasEffect()) {
            activate(pc);
        }
    }

    public boolean hasEffect() {
        return effect == null ? false : true;
    }

    public static Item deSerialize(String serializedString) {
        ItemBuilder ib = new ItemBuilder();
        Item i = ib.build("");
        return i;
    }

    public String serialize() {
        StringBuilder sb = new StringBuilder();
//        sb.append(id).append(";");
//        sb.append(effect.getID()).append(";");
//        sb.append(attributs.serialize()).append(";");
//        sb.append(suffix.name()).append(";");
//        sb.append(rareNess.name()).append(";");
//        sb.append(name).append(";");
//        sb.append(maxStackCount).append(";");
////        for (Item item : includedItems) {
////            sb.append(item.serialize());
////            sb.append(";");
////        }
//        sb.append(description).append(";");
        return sb.toString();
    }
}
